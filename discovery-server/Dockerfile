FROM openjdk:17-alpine

COPY /target/discovery-server-*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app.jar"]
