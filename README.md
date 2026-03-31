# Interview Preparation Platform

A full-stack interview preparation platform built with **React.js** and **Spring Boot**. Practice topic-wise interview questions, get instant scored feedback, and track your performance over time — no AI API required.

---

## Features

- **JWT Authentication** — Register, login, and secure protected routes with token-based auth
- **Topic-wise Practice** — 150+ curated questions across 10 CS topics with Easy / Medium / Hard difficulty
- **Answer Evaluation** — Keyword-based scoring engine rates your answers 0–10 with detailed feedback
- **Performance Dashboard** — View total attempts, average score, strongest/weakest topics
- **Answer History** — Browse all past attempts filtered by topic
- **Responsive UI** — Clean dark-themed interface built with React.js

---

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | React.js, React Router, Axios |
| Backend | Java 17, Spring Boot 3.2 |
| Security | Spring Security, JWT (jjwt 0.11.5) |
| Database | MySQL, Spring Data JPA, Hibernate |
| API Docs | Swagger / SpringDoc OpenAPI |
| Build Tool | Maven |

---

## Topics Covered

Java, Spring Boot, Data Structures, Algorithms, DBMS, Operating Systems, Computer Networks, System Design, OOP Concepts, SQL

---

## Project Structure

```
interview-prep-platform/
├── src/main/java/com/interviewprep/
│   ├── config/
│   │   ├── DataInitializer.java       # Seeds topics & questions on startup
│   │   ├── SecurityConfig.java
│   │   ├── GlobalExceptionHandler.java
│   │   └── SwaggerConfig.java
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── QuestionController.java
│   │   ├── AnswerController.java
│   │   └── DashboardController.java
│   ├── model/
│   │   ├── User.java
│   │   ├── Topic.java
│   │   ├── Question.java
│   │   └── UserAnswer.java
│   ├── repository/
│   ├── service/
│   │   ├── QuestionBankService.java   # Built-in question bank + evaluation engine
│   │   ├── QuestionService.java
│   │   ├── EvaluationService.java
│   │   ├── UserService.java
│   │   └── DashboardService.java
│   └── security/
│       ├── JwtUtil.java
│       ├── JwtAuthFilter.java
│       └── CustomUserDetailsService.java
├── src/main/resources/
│   └── application.properties
└── pom.xml

frontend/
├── src/
│   ├── pages/
│   │   ├── HomePage.js
│   │   ├── LoginPage.js
│   │   ├── RegisterPage.js
│   │   ├── DashboardPage.js
│   │   ├── PracticePage.js
│   │   └── HistoryPage.js
│   ├── components/
│   │   ├── Navbar.js
│   │   └── ProtectedRoute.js
│   ├── context/
│   │   └── AuthContext.js
│   └── services/
│       └── api.js
└── package.json
```

---

## Getting Started

### Prerequisites

- Java 17+
- Node.js 18+
- MySQL 8+
- Maven 3.8+

---

### 1. Database Setup

Open MySQL and run:

```sql
CREATE DATABASE interviewprep_db;
```

Hibernate will create all tables automatically on first run.

---

### 2. Backend Setup

Clone the repo and navigate to the backend folder.

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/interviewprep_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password_here

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

jwt.secret=your-secret-key-minimum-32-characters-long
jwt.expiration=86400000
```

Run the backend:

```bash
mvn clean spring-boot:run
```

The server starts at `http://localhost:8080`.
On startup, **10 topics and 150 questions are seeded automatically**.

---

### 3. Frontend Setup

```bash
cd frontend
npm install
npm start
```

The app opens at `http://localhost:3000`.

---

## API Endpoints

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |

### Questions
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/questions/generate` | Get questions for a topic |
| GET | `/api/questions/topics` | Get all available topics |

### Answers
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/answers/submit` | Submit an answer and get score |
| GET | `/api/answers/history` | Get answer history for logged-in user |

### Dashboard
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/dashboard/stats` | Get performance stats for logged-in user |

> Full API docs available at `http://localhost:8080/swagger-ui/index.html`

---

## How Answer Scoring Works

No AI API is used. The evaluation engine works as follows:

1. Detects the topic from the question text
2. Checks how many domain-specific keywords appear in your answer
3. Adds bonus points for answer length (more detail = higher score)
4. Adds bonus for using examples, comparisons, or explanations
5. Returns a score from **1–10** with specific feedback on what was missing

---

## Screenshots

> Add screenshots of your HomePage, Practice session, and Dashboard here.

---

## License

This project is built for learning and portfolio purposes.
