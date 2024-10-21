FROM alpine/java:21-jre
MAINTAINER iv.movchanets@gmail.com
COPY target/resource-0.0.1-SNAPSHOT.jar resource-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev", "-jar","/resource-service-0.0.1-SNAPSHOT.jar"]
