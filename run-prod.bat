@echo off
cd docker\compose
docker-compose -f docker-compose.prod.yml --env-file .env up -d
echo Production environment started
pause