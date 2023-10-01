FROM openjdk:17-alpine

COPY /target/inventory-service-*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app.jar"]
