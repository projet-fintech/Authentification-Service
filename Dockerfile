# Étape de build
FROM maven:3.9.6-eclipse-temurin-21 AS builder_service
WORKDIR /app

# Créer la structure du repo local
RUN mkdir -p repo/com/banque/events-lib/1.0-SNAPSHOT

# Copier events-lib dans le repo local
COPY events-lib-1.0-SNAPSHOT.jar repo/com/banque/events-lib/1.0-SNAPSHOT/

# Copier les fichiers du projet
COPY pom.xml ./Authentication_service
COPY src ./Authentication_service/src



# Build du projet
RUN mvn clean install -DskipTests

# Étape finale
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder_service /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
