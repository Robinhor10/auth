# Auth API - Sistema de Autenticação

## 📌 Sobre a Aplicação

A **Auth API** é um sistema de autenticação desenvolvido em **Java 21** com **Spring Boot**, permitindo o cadastro e autenticação de usuários. A API armazena os usuários em um banco de dados **PostgreSQL** e implementa boas práticas de desenvolvimento, incluindo **hash de senhas** e **injeção de dependências**.

### 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.2**
- **Spring Data JPA**
- **PostgreSQL**
- **Docker e Docker Compose**
- **Maven**

---

## 🛠️ Configuração e Execução Local

### 🔧 Pré-requisitos

Antes de rodar a aplicação, você precisa ter instalado:

- [Docker](https://www.docker.com/get-started) e [Docker Compose](https://docs.docker.com/compose/)
- [Java 21](https://adoptium.net/) ou superior
- [Maven](https://maven.apache.org/install.html)

### 📦 Configuração do Banco de Dados

A API usa um banco **PostgreSQL** dentro de um container Docker. O `docker-compose.yml` já configura tudo automaticamente.

Caso queira rodar o banco localmente sem Docker, configure um **PostgreSQL** e crie um banco de dados:

```sql
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    userName TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL

);
```

### 🔥 Executando com Docker

A forma mais fácil de rodar a API é via **Docker Compose**:

**ATENÇÃO: Se optar por não instalar e configurar nada local, e for usar tudo via docker, certifique-se de fazer
checkout da branch feature/docker_full**

```bash
docker-compose up --build
```

Isso irá subir:

- O container do banco de dados **PostgreSQL**
- O container da aplicação **Auth API**

### 🏃 Executando Localmente (sem Docker)

Caso prefira rodar a API sem Docker:

1. Configure as variáveis de ambiente no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

2. Compile e execute o projeto com Maven:

```bash
mvn clean install
java -jar target/auth-api-0.0.1-SNAPSHOT.jar
```

A API estará disponível em `http://localhost:8080`.

---

## 📌 Endpoints da API

### 🔹 Cadastro de Usuário

**Endpoint:** `/auth/cadastro`

- **Método:** `POST`
- **Body (JSON):**

```json
{
  "userName": "teste2",
  "email": "teste2@email.com",
  "password": "123456",
  "role": "01"
}
```

- **Resposta Sucesso:**

```json
{
  "success": true,
  "message": "Usuário registrado com sucesso",
  "data": {
    "id": 2,
    "email": "teste2@email.com",
    "userName": "teste2",
    "role": "01"
  }
}
```

### 🔹 Autenticação de Usuário

**Endpoint:** `/auth/login`

- **Método:** `POST`
- **Body (JSON):**

```json
{
  "email": "teste2@email.com",
  "password": "123456"
}
```

- **Resposta Sucesso:**

```json
{
  "success": true,
  "message": "Login bem-sucedido",
  "data": {
    "id": 2,
    "email": "teste2@email.com",
    "userName": "teste2",
    "role": "01"
  }
}
```

- **Resposta Erro:**

```json
{
  "success": false,
  "message": "Email ou senha inválidos",
  "data": null
}
```

---

## 📝 Melhorias Futuras

- Implementação de autenticação via **JWT**
- Reset de senha
- Middleware para controle de acessos

---
