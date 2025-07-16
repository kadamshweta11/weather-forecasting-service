# Use the official OpenJDK image
FROM openjdk:17-jdk-slim

# Create and set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
COPY target/forecasting-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Command to run the JAR
CMD ["java", "-jar", "app.jar"]