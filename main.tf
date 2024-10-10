terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"  # Certifique-se de usar uma versão que suporte cpu_architecture
    }
  }

  backend "s3" {
    bucket="bktaws-geyson"
    key="terraform.tfstate"
    region="us-east-1"

  }
}




provider "aws" {
  region = "us-east-1"  # Altere para sua região preferida
}


resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
  tags = {
    Name = "aws-vpc"
  }
}

# zonas dispiniblidades
data "aws_availability_zones" "available" {}

resource "aws_subnet" "subnets" {
  count = 2
  availability_zone = data.aws_availability_zones.available.names[count.index]
  vpc_id = aws_vpc.main.id
  cidr_block = cidrsubnet(aws_vpc.main.cidr_block, 8, count.index)
  map_public_ip_on_launch = true

  tags = {
    Name = "subnets-${count.index}"
  }
}

resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.main.id

  tags = {
    Name = "main-igw"
  }
}

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.main.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.gw.id
  }

  tags = {
    Name = "public-route-table"
  }
}

resource "aws_route_table_association" "public" {
  count = 2
  subnet_id = aws_subnet.subnets[count.index].id
  route_table_id = aws_route_table.public.id
}

resource "aws_security_group" "fargate-sg" {
  vpc_id = aws_vpc.main.id

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }


  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "fargate-sg"
  }
}



resource "aws_ecs_cluster" "ecs_cluster" {
  name = "fargate-cluster"
}

resource "aws_iam_role" "ecs_task_exec_role" {
  name = "ecsTaskExecutionRoleV2"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action    = "sts:AssumeRole"
        Effect    = "Allow"
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_policy_attachment" "ecs_task_exec_role_policy" {
  name       = "ecs_task_exec_role_policy_attach"
  roles      = [aws_iam_role.ecs_task_exec_role.name]
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}
#
resource "aws_cloudwatch_log_group" "ecs_log_group" {
  name              = "/ecs/task-unique"
  retention_in_days = 7
}


resource "aws_ecs_task_definition" "task" {
  family                   = "task001"
  network_mode             = "awsvpc"  # Defina o modo de rede
  requires_compatibilities = ["FARGATE"]  # ou "EC2" para instâncias EC2
  cpu                      = "256"  # CPU para a tarefa
  memory                   = "512"  # Memória para a tarefa
  execution_role_arn       = aws_iam_role.ecs_task_exec_role.arn  # Role de execução


  container_definitions = jsonencode([{
    name      = "consorcio-app"
    image     = "861276095731.dkr.ecr.us-east-1.amazonaws.com/rpaws-itau:latest"
    cpu       = 128
    memory    = 256
    essential = true

    portMappings = [{
      containerPort = 8080
      hostPort      = 8080
      protocol      = "tcp"
    }]

    logConfiguration = {
      logDriver = "awslogs"
      options = {
        awslogs-group         = "/ecs/task-unique"
        awslogs-region        = "us-east-1"
        awslogs-stream-prefix = "ecs"
      }
    }
  }])
}



resource "aws_ecs_service" "service" {
  name            = "ecs-service"
  cluster         = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.task.id
  desired_count   = 1
  launch_type     = "FARGATE"
  network_configuration {
    subnets          = aws_subnet.subnets[*].id
    security_groups  = [aws_security_group.fargate-sg.id]
    assign_public_ip = true
  }
}





