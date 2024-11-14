# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application's JAR file into the container
# Make sure to use the correct path to your JAR file
COPY target/fetch-reward-reciept-processor-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your Spring Boot application uses
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]