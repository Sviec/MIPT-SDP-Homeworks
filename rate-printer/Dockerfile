FROM openjdk:21-jdk-slim
LABEL authors="Sviec"
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]