# build stage
FROM maven:3.8.6-openjdk-18-slim AS build

COPY src /tmp/src
COPY pom.xml /tmp
RUN mvn -f /tmp/pom.xml clean test package

# deployment stage
FROM openjdk:18-jdk-alpine
COPY --from=build /tmp/target/*.jar wiss_quiz_rest.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","wiss_quiz_rest.jar"] 

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

