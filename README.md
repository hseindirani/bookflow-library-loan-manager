# BookFlow – Library Loan Manager

A reactive REST API built with Spring Boot 3 and Spring WebFlux for managing a small library system.

The application allows members to borrow and return books, rate books they have borrowed, and view a personal dashboard showing their current loans and ratings.

---

## Technologies

- Java 21
- Spring Boot 3
- Spring WebFlux
- Spring Data R2DBC
- H2 Database
- Maven
- OpenAPI / Swagger

---

## Features

### Book Loans
- Borrow a book
- Return a borrowed book
- Prevent borrowing when no copies are available
- Prevent duplicate active loans for the same member and book

### Ratings
- Members can rate a book only if they have borrowed it at least once
- One rating per member per book
- Existing ratings are updated instead of creating duplicates

### Dashboard
Displays:
- Current borrowed books
- Member's own rating
- Average rating for each borrowed book

---

## Project Structure

The project follows a feature-based package structure.

```
book
member
loan
rating
dashboard
common
```

Each feature contains its own domain classes, repository, service, controller and DTOs.

---

## Architecture

The application follows a layered architecture.

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

- Controllers handle HTTP requests and responses.
- Services contain the business logic.
- Repositories are responsible for database access.
- Global exception handling provides consistent API error responses.

---

## Database

The application uses an in-memory H2 database.

The database schema is created automatically from `schema.sql`, and sample data is loaded from `data.sql` when the application starts.

The seed data includes different scenarios for testing, such as:
- available books
- books with no available copies
- active and returned loans
- existing ratings

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/books/{bookId}/loans` | Borrow a book |
| POST | `/api/loans/{loanId}/return` | Return a book |
| PUT | `/api/books/{bookId}/ratings` | Create or update a rating |
| GET | `/api/members/{memberId}/dashboard` | View member dashboard |

---

## Running the Project

Clone the repository.

Run:

```bash
mvn spring-boot:run
```

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Testing

The project includes both unit and integration tests.

### Unit Tests
- Borrowing fails when no copies are available.
- Rating fails when the member has never borrowed the book.

### Integration Tests
- Borrowing successfully creates a loan.
- Borrowing returns HTTP 409 when no copies are available.

---

## Angular Frontend

The project also includes a simple Angular frontend that consumes the REST API and demonstrates the complete flow between frontend and backend.

### Technologies

- Angular
- TypeScript

### Features

- Load dashboard by Member ID
- Display current borrowed books
- Display member rating and average rating
- Handle members with no active loans
- Display user-friendly error messages

### Frontend Architecture

The frontend follows Angular's separation of concerns.

```
DashboardComponent
        │
        ▼
DashboardService
        │
        ▼
Spring Boot REST API
```

- **DashboardComponent** manages the UI state and user interactions.
- **DashboardService** is responsible only for HTTP communication with the backend.
- Business logic remains entirely in Spring Boot.

### Running the Frontend

```bash
cd bookflow-dashboard
npm install
ng serve
```

The frontend runs on:

```
http://localhost:4200
```

Ensure the Spring Boot backend is also running 
