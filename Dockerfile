# ---------- Build Stage ----------
FROM docker.io/library/maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ---------- Runtime Stage ----------
FROM docker.io/library/eclipse-temurin:21-jdk
WORKDIR /app

# Copy only the built JAR from the build stage
COPY --from=build /app/target/Cars-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

