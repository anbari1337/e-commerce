FROM openjdk:17

WORKDIR /app

COPY target/user-service-0.0.1-SNAPSHOT.jar user-service-1.0.0.jar


ENTRYPOINT ["java", "-jar", "/app/user-service-1.0.0.jar"]