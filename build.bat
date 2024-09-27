(Get-ECRLoginCommand).Password | docker login --username AWS --password-stdin 861276095731.dkr.ecr.us-east-1.amazonaws.com
docker build -t app-itau-01 -f Dockerfile .
docker tag app-itau-01:latest 861276095731.dkr.ecr.us-east-1.amazonaws.com/app-itau-01:latest
docker push 861276095731.dkr.ecr.us-east-1.amazonaws.com/app-itau-01:latest