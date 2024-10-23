# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app/song-app

COPY pom.xml /app/song-app/pom.xml
RUN mvn dependency:go-offline

COPY src/ /app/song-app/src/
RUN mvn clean package -DskipTests

# Run stage
FROM alpine/java:21-jdk AS run
WORKDIR /app/song-app

COPY --from=build /app/song-app/target/*.jar app.jar

EXPOSE 8002

CMD ["java", "-jar", "app.jar"]