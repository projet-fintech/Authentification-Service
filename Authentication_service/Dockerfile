# Première étape : Construction du projet events-lib (build)
FROM maven:3.9.6-eclipse-temurin-21 AS builder_lib

WORKDIR /app

COPY events-lib/pom.xml events-lib/
COPY events-lib/src events-lib/src

RUN cd events-lib && mvn clean install -DskipTests

# Deuxième étape : Construction du projet Authentication_service (ou User_service) (build)
FROM maven:3.9.6-eclipse-temurin-21 AS builder_service

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY --from=builder_lib  /app/events-lib/target/*.jar ./lib/

RUN  mvn clean install -DskipTests

 # Troisième étape : Construction de l'image finale
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder_service /app/target/*.jar app.jar
COPY --from=builder_service /app/lib/*.jar lib/

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]