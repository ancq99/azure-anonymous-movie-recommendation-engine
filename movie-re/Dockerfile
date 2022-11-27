FROM openjdk:17-jdk-alpine3.14

ENV DB_HOST="wewe"

COPY ./build/libs/movie-re-0.0.1-SNAPSHOT.jar /opt/movie-re.jar

CMD ["java", "-jar", "/opt/movie-re.jar"]