# Start with a base image containing Java runtime (e.g., OpenJDK 17)
FROM openjdk:17


# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/book-managment-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} book-managment-service.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/book-managment-service.jar"]
