FROM openjdk:17-alpine

COPY /target/config-service-*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "/app.jar"]
