#!/bin/bash
set -e

if [ -d "/usr/lib/jvm/java-21-openjdk-amd64" ]; then
    export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
    export PATH="$JAVA_HOME/bin:$PATH"
fi

echo "Building SmartMenu Spring Boot application with Java 21 LTS..."
mvn clean package -DskipTests

echo "Build completed successfully! Artifact: target/smartmenu-backend-1.0.0.jar"
