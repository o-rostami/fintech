# Build stage
FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -B dependency:resolve dependency:resolve-plugins
RUN mvn -f /home/app/pom.xml clean -DskipTests=true package

# Package stage
FROM openjdk:17-jdk-alpine
COPY --from=build /home/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]