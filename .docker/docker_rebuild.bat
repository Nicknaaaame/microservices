cd ..

CALL mvn clean install -DskipTests

docker rmi -f lapotkod/config-service:0.0.1
cd .\config-service\
docker build . -t lapotkod/config-service:0.0.1
cd ..

docker rmi -f lapotkod/discovery-server:0.0.1
cd .\discovery-server\
docker build . -t lapotkod/discovery-server:0.0.1
cd ..

docker rmi -f lapotkod/api-gateway:0.0.1
cd .\api-gateway\
docker build . -t lapotkod/api-gateway:0.0.1
cd ..

docker rmi -f lapotkod/inventory-service:0.0.1
cd .\inventory-service\
docker build . -t lapotkod/inventory-service:0.0.1
cd ..

docker rmi -f lapotkod/order-service:0.0.1
cd .\order-service\
docker build . -t lapotkod/order-service:0.0.1
cd ..

cd .\.docker\

docker compose up
