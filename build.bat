aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 861276095731.dkr.ecr.us-east-1.amazonaws.com
docker build -t rpaws-itau -f Dockerfile .
docker tag rpaws-itau:latest 861276095731.dkr.ecr.us-east-1.amazonaws.com/rpaws-itau:latest
docker push 861276095731.dkr.ecr.us-east-1.amazonaws.com/rpaws-itau:latest