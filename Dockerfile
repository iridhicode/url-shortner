# Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project file to the container
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline

# Copy the application source code to the container
COPY src ./src

# Build the application
RUN mvn package

# Expose the port on which your application runs
EXPOSE 9000

# Run the application when the container starts
CMD ["java", "-jar", "target/shortenurl-0.0.1-SNAPSHOT.jar"]