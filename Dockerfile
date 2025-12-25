# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application (skipping tests to speed up build and avoid environment issues)
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create uploads directory
RUN mkdir -p /app/uploads

# Copy the JAR file (using specific name to avoid ambiguity)
COPY --from=build /app/target/ideasync-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Run with memory limit to prevent OOM on Render free tier
ENTRYPOINT ["java", "-Xmx350m", "-jar", "app.jar"]
