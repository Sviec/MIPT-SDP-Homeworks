@echo off
echo 1. Launch Zookeeper...
start "Zookeeper" cmd /c "D:\Utils\Zookeeper\apache-zookeeper\bin\zkServer.cmd"
timeout /t 3

echo 2. Launch Pact Broker...
cd /d D:\Projects\Java\MIPT-SDP-Homeworks\docker\pact-broker
docker-compose up -d
timeout /t 3

echo 3. Launch Prometheus + Grafana...
cd /d D:\Projects\Java\MIPT-SDP-Homeworks\docker\monitoring
docker-compose up -d
timeout /t 3

echo 4. Launch Currency Provider...
cd /d D:\Projects\Java\MIPT-SDP-Homeworks\currency-provider
start "Currency Provider" cmd /c "mvnw spring-boot:run"
timeout /t 8

echo 5. Launch Rate Printer...
cd /d D:\Projects\Java\MIPT-SDP-Homeworks\rate-printer
start "Rate Printer" cmd /c "mvnw spring-boot:run"

echo All services started
pause