@echo off
cd docker\compose
docker-compose -f docker-compose.dev.yml up -d
echo Dev environment started
echo - Zookeeper: localhost:2181
echo - Currency Provider: http://localhost:8081
echo - Rate Printer: http://localhost:8082
echo - Prometheus: http://localhost:9090
echo - Grafana: http://localhost:3000 (admin/admin)
pause