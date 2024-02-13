# Используем OpenJDK 11 в качестве базового образа
# FROM adoptopenjdk:11-jre-hotspot
FROM openjdk:11-slim-bullseye

# Устанавливаем рабочую директорию в /app
WORKDIR /app

# Копируем JAR-файл в образ
COPY target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar

# Команда, которая будет выполнена при запуске контейнера
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
