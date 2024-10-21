FROM alpine/java:21-jre
MAINTAINER iv.movchanets@gmail.com
COPY target/song-0.0.1-SNAPSHOT.jar song-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar","/song-service-0.0.1-SNAPSHOT.jar"]
