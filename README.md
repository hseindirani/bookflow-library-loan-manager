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
- JUnit 5
- Mockito

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

This keeps related code together and makes the project easier to maintain as it grows.

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

## Design Decisions

Some notable design decisions include:

- Feature-based package structure instead of layer-based packaging.
- Reactive programming using Spring WebFlux.
- Transactions for operations that update multiple entities.
- Dashboard implemented as a dedicated endpoint to simplify frontend integration.
- Average book rating is calculated dynamically rather than stored in the database.

---
