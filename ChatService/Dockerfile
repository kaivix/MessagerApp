FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/ChatService-0.0.1-SNAPSHOT.jar app.jar
LABEL authors="kaivix"
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]