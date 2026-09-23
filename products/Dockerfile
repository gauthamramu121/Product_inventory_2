#FROM eclipse-temurin:21-jdk
#
#ARG JAR_FILE=target/*.jar
#
#COPY ${JAR_FILE} app.jar
#
#EXPOSE 8332
#
#ENV SPRING_PROFILES_ACTIVE=docker
#
#ENTRYPOINT ["java","-jar","/app.jar"]
# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]