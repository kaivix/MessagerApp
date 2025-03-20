FROM openjdk:17
LABEL authors="kaivix"

WORKDIR /app

# Копируем собранный .jar файл
COPY target/AuthService-0.0.1-SNAPSHOT.jar authservice.jar

# Экспонируем порт для приложения
EXPOSE 8080

# Добавляем переменные окружения для конфигурации базы данных (по желанию)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://auth-postgres:5432/authpostgres
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=root

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "authservice.jar"]
