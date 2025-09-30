# Multi-stage build for optimal image size and security

# Build stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# Set working directory
WORKDIR /app

# Copy dependency files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Download dependencies (cached layer)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

# Install security updates and create non-root user
RUN apk update && apk upgrade && \
    addgroup -g 1001 -S appuser && \
    adduser -u 1001 -S appuser -G appuser

# Set working directory
WORKDIR /app

# Copy jar from build stage
COPY --from=build --chown=appuser:appuser /app/target/*.jar app.jar

# Switch to non-root user
USER appuser

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Expose port
EXPOSE 8080

# JVM optimization flags
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport"

# Run application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
