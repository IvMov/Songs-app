# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine as build

WORKDIR /app/resource-app

COPY pom.xml /app/resource-app/pom.xml
RUN mvn dependency:go-offline

COPY src/ /app/resource-app/src/
RUN mvn clean package -DskipTests

# Run stage
FROM alpine/java:21-jdk as run
WORKDIR /app/resource-app

COPY --from=build /app/resource-app/target/*.jar app.jar

EXPOSE 8001

CMD ["java", "-jar", "app.jar"]