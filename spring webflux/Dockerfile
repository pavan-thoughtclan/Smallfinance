# Use the official OpenJDK base image
FROM eclipse-temurin:17.0.5_8-jre


# Copy the JAR file into the container
ADD target/small-finance.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8085

# Define the command to run your Spring Boot application
CMD ["java", "-jar", "/app.jar"]
