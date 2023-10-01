FROM openjdk:17-alpine

COPY /target/order-service-*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/app.jar"]
