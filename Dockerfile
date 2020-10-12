FROM openjdk:14-jdk-alpine

EXPOSE 8080
LABEL maintainer="nikodem.peter@gmail.com"
ARG JAR_FILE=target/hacker-news-top-words-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]