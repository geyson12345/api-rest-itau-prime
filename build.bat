aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 861276095731.dkr.ecr.us-east-1.amazonaws.com
docker build -t ecr-aws-itau -f Dockerfile .
docker tag ecr-aws-itau:latest 861276095731.dkr.ecr.us-east-1.amazonaws.com/ecr-aws-itau:latest
docker push 861276095731.dkr.ecr.us-east-1.amazonaws.com/ecr-aws-itau:latest