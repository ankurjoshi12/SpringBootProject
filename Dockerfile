# Use a base image with Java pre-installed
FROM openjdk:17-oracle
#openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/AutomationUtils-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your Spring Boot application listens on
EXPOSE 8081

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "app.jar"]