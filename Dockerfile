# Stage 1: Build the application com Maven + JDK 21
FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Define o JAVA_HOME para o JDK 21
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Realiza o build da aplicação
RUN mvn clean package -DskipTests

# Stage 2: Rodar a aplicação com OpenJDK 21
FROM openjdk:21-jdk-slim

WORKDIR /app

# Define o JAVA_HOME também na execução
ENV JAVA_HOME=/usr/local/openjdk-21
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Copia o arquivo JAR da aplicação para dentro do container
COPY --from=build /app/target/auth-0.0.1-SNAPSHOT.jar app.jar

# Expondo a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
