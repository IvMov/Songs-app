# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine as build

WORKDIR /app/discovery

COPY pom.xml /app/discovery/pom.xml
RUN mvn dependency:go-offline

COPY src/ /app/discovery/src/
RUN mvn clean package -DskipTests

# Run stage
FROM alpine/java:21-jdk as run
WORKDIR /app/discovery

COPY --from=build /app/discovery/target/*.jar app.jar

EXPOSE 8761

CMD ["java", "-jar", "app.jar"]