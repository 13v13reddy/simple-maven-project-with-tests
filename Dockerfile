# Use an official OpenJDK 17 runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the generated artifact (JAR file) into the container
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8000

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]