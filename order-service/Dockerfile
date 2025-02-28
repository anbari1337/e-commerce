FROM openjdk:17

WORKDIR /app

COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar

ENTRYPOINT ["java", "-jar", "/app/order-service.jar"]
