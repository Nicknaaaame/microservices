cd ..

docker rmi -f lapotkod/product-service:0.0.1
cd .\product-service\
CALL mvn clean install -DskipTests
docker build . -t lapotkod/product-service:0.0.1
cd ..


cd .\.docker\

docker compose -f docker-compose-tmp.yml up
