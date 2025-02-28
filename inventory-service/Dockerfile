FROM openjdk:17

WORKDIR /app

COPY target/inventory-service-0.0.1-SNAPSHOT.jar inventory-service-1.0.0.jar


ENTRYPOINT ["java", "-jar", "/app/inventory-service-1.0.0.jar"]
