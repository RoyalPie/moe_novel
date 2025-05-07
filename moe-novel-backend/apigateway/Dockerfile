# Use a lightweight Java image
FROM eclipse-temurin:21-jre-ubi9-minimal

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar into the container
COPY target/*.jar app.jar

# Expose the service port (adjust if needed)
EXPOSE 8888

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]