FROM openjdk:17

WORKDIR /app

COPY target/product-service-0.0.1-SNAPSHOT.jar product-service-1.0.0.jar
COPY src/main/resources/sql/data.sql /docker-entrypoint-initdb.d/data.sql

ENTRYPOINT ["java", "-jar", "/app/product-service.jar"]