CALL mvn clean install -DskipTests

docker rmi -f lapotkod/config-service:0.0.1

cd ..

cd .\config-service\
docker build . -t lapotkod/config-service:0.0.1
cd ..

docker rmi -f lapotkod/discovery-server:0.0.1
cd .\discovery-server\
docker build . -t lapotkod/discovery-server:0.0.1
cd ..

cd .\.docker\

docker compose up
