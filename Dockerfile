# Use the official OpenJDK base image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file to the container
COPY target/lyami-business-service-1.0-SNAPSHOT.jar lyami-business-service-1.0-SNAPSHOT.jar

# Expose the port on which the application will listen
EXPOSE 8081

# Set the entry point command
CMD ["java", "-jar", "lyami-business-service-1.0-SNAPSHOT.jar"]