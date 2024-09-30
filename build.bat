aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 861276095731.dkr.ecr.us-east-1.amazonaws.com
docker build -t app-itau-rest -f Dockerfile .
docker tag app-itau-rest:latest 861276095731.dkr.ecr.us-east-1.amazonaws.com/app-itau-rest:latest
docker push 861276095731.dkr.ecr.us-east-1.amazonaws.com/app-itau-rest:latest