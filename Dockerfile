# Stage 1: Build the application
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Usando a imagem oficial do OpenJDK 21
FROM openjdk:21-jdk-slim

# Definindo o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR da aplicação para dentro do container
COPY --from=build /app/target/auth-0.0.1-SNAPSHOT.jar app.jar

# Expondo a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
