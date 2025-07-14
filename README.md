# ☕ Café da Manhã Colaborativo

Sistema Full Stack para organizar um café da manhã entre os colaboradores. A aplicação permite cadastrar colaboradores, itens do café, marcar se foram entregues ou não, e listar os itens por data.

## 🛠 Tecnologias Utilizadas

### Backend:
- Java 17
- Spring Boot
- JPA + Hibernate
- MySQL
- Maven
- Swagger

### Frontend:
- Angular 17 (standalone components)
- HTML / CSS

---

## 📦 Como executar com o Docker

### Pré-requisitos:
- Docker instalado

---

### 🔧 1. Gerar o JAR na pasta raiz do Backend

Execute na raiz do projeto Java:

```bash
mvn clean package -DskipTests
```
### 🔧 2. Executar Build

```bash
docker-compose build
```
### 🔧 3. Executar o projeto
```bash
docker-compose up
```

---

## Funcionalidades
✅ Cadastro de colaborador com validação de CPF

✅ Cadastro de item com data, cpf e comida escolhida

✅ Marcação de entrega de item (checkbox) com atualização no banco de dados

✅ Listagem de itens por data

✅ Validações com feedback visual no frontend

✅ Comunicação entre Angular e Spring via REST API

---

## 🔧 Endpoints do backend com swagger

- Com o projeto em execução, acesse a rota:
http://localhost:8080/swagger-ui/index.html
