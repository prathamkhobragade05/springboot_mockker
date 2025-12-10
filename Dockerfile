FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/spring-boot-docker.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
