# Build stage - Maven with Eclipse Temurin JDK 21 LTS
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B || true

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage - Eclipse Temurin JRE 21 LTS Alpine
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Run as non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /app/target/smartmenu-backend-1.0.0.jar app.jar

USER appuser

ENV PORT=3020
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70.0 -XX:+ExitOnOutOfMemoryError"

EXPOSE 3020

ENTRYPOINT ["java", "-jar", "app.jar"]
