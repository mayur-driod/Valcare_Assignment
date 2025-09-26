# Stage 1: Build the JAR
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and Maven wrapper
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build JAR
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the JAR
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy JAR from build stage and rename it to app.jar
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the JAR
CMD ["java", "-jar", "app.jar"]
