<h1 align="center">📱 Mobile Backend Management</h1>

<p align="center">
  A collection of Spring Boot microservices for a mobile application backend, covering users, products and orders.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white" alt="Maven" />
  <img src="https://img.shields.io/badge/Spring%20Data-JPA-6DB33F?logo=spring&logoColor=white" alt="JPA" />
  <img src="https://img.shields.io/badge/REST-API-blue" alt="REST API" />
</p>

---

## 📑 Table of Contents

- [About the Project](#-about-the-project)
- [Features](#-features)
- [Project Structure](#-project-structure)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Running Tests](#-running-tests)
- [Author](#-author)

---

## 📖 About the Project

**Mobile Backend Management** is a set of backend services built with **Spring Boot** to power a mobile application. Each service is independent and follows a clean layered architecture (Controller → Service → Repository → Entity).

The repository also includes practice projects on **JPA entity relationships**, **REST API design** and **request validation**.

## ✨ Features

- 👤 **User Service** – create, read, update and delete users
- 🛍️ **Product Service** – manage products, with a correlation ID filter for request tracing
- 📦 **Order Service** – handle order processing
- 🔗 JPA entity relationships (one-to-one, one-to-many, many-to-many)
- ✅ Request validation for REST APIs
- 🧱 Layered architecture in every service

## 📂 Project Structure

```text
Mobile_Backend_Management/
├── userservice/                  # User management service
├── productservice/               # Product management service
├── orderservice/                 # Order management service
├── UserManagementRestApi/        # Practice: REST API for users
├── UserManagementApiValidation/  # Practice: request validation
├── JPAEntityRealationships/      # Practice: JPA relationships
└── mybackend/                    # Starter backend project
```

Each service uses the same layout:

```text
src/main/java/org/example/<service>/
├── controller/     # REST endpoints
├── service/        # Business logic
├── repository/     # Spring Data JPA repositories
├── entity/         # JPA entities
└── filter/         # Request filters (e.g. CorrelationIdFilter)
```

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java |
| Framework | Spring Boot |
| Persistence | Spring Data JPA, Hibernate |
| Build Tool | Maven (Maven Wrapper included) |
| API Style | REST |

## 🚀 Getting Started

### Prerequisites

- JDK 17 or later
- Maven 3.8+ (optional, `mvnw` is included)
- A running database (MySQL / H2, as configured in `application.properties`)

### Installation

**1. Clone the repository**

```bash
git clone https://github.com/nagendrababu66633-jpg/Mobile_Backend_Management.git
cd Mobile_Backend_Management
```

**2. Configure the database**

Edit `src/main/resources/application.properties` in each service:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
server.port=8081
```

**3. Run a service** (example: `userservice`)

```bash
cd userservice
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
cd userservice
.\mvnw.cmd spring-boot:run
```

> 💡 Give each service a different `server.port` so they can run at the same time.

## 🔌 API Endpoints

> Update these to match your controllers.

| Service | Method | Endpoint | Description |
|---------|--------|----------|-------------|
| User | `GET` | `/users` | Get all users |
| User | `GET` | `/users/{id}` | Get user by ID |
| User | `POST` | `/users` | Create a user |
| User | `PUT` | `/users/{id}` | Update a user |
| User | `DELETE` | `/users/{id}` | Delete a user |
| Product | `GET` | `/products` | Get all products |
| Order | `POST` | `/orders` | Place an order |

## 🧪 Running Tests

```bash
./mvnw test
```

## 👨‍💻 Author

**Nagendra Babu**

- GitHub: [@nagendrababu66633-jpg](https://github.com/nagendrababu66633-jpg)

---

<p align="center">⭐ If you found this project useful, give it a star!</p>
