# Stage 1: Building the application with Gradle
FROM gradle:jdk21 as builder

# Set the working directory in the Docker image
WORKDIR /app

# Copy the Gradle configuration files and the Gradle wrapper
COPY build.gradle settings.gradle gradle.properties gradlew /app/
COPY gradle /app/gradle

# Make the Gradle wrapper executable
RUN chmod +x ./gradlew

# Copy the source code
COPY src /app/src

# Run Gradle build, skip tests for faster build
# Change to `./gradlew build` if tests are needed during build
RUN ./gradlew build -x test

# Stage 2: Create the runtime image using UBI OpenJDK 21
FROM registry.access.redhat.com/ubi8/openjdk-21:1.18

# Set environment variables
ENV LANGUAGE='en_US:en'
ENV POSTGRES_USER=username
ENV POSTGRES_PASSWORD=password
ENV POSTGRES_URL=jdbc:postgresql://postgres/microservice-example

# Set the working directory in the Docker image
WORKDIR /deployments

# Copy the built artifacts from the builder stage to the runtime image
COPY --from=builder --chown=185 /app/build/quarkus-app/lib/ /deployments/lib/
COPY --from=builder --chown=185 /app/build/quarkus-app/*.jar /deployments/
COPY --from=builder --chown=185 /app/build/quarkus-app/app/ /deployments/app/
COPY --from=builder --chown=185 /app/build/quarkus-app/quarkus/ /deployments/quarkus/

# Expose the application port
EXPOSE 8080

# Use the non-root user to run our application
USER 185

# Set additional JVM options
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

# Set the path to the application jar file
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

# Use the provided run script from the base image to start the application
ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]
