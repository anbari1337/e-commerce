FROM openjdk:17

COPY target/user-service-0.0.1-SNAPSHOT.jar user-service-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/user-service-1.0.0.jar"]