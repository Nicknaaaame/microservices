mvn clean install -DskipTests

docker rmi -f lapotkod/config-service:0.0.1
cd .\config-service\
docker build . -t lapotkod/config-service:0.0.1
cd ..

docker rmi -f lapotkod/eureka-service:0.0.1
cd .\discovery-server\
docker build . -t lapotkod/eureka-service:0.0.1
cd ..

docker compose up
