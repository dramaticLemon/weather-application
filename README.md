# Weather Application

A web application for viewing the current weather. Users can register, add one or more locations to their collection, and the main page will display a list of these locations with their current weather conditions.

## ✨ Features

-   User registration and session-based authentication.
-   Ability to add and manage a list of locations.
-   Dashboard view of current weather for all saved locations.
-   Secure password hashing.
-   Session management using Redis.

## 🛠️ Tech Stack

-   **Backend:** Java, Spring MVC, Spring ORM, Hibernate
-   **Frontend:** HTML, CSS, Thymeleaf
-   **Database:** PostgreSQL 16
-   **Database Migrations:** Flyway
-   **Caching:** Redis 7
-   **Build Tool:** Apache Maven
-   **Testing:** JUnit 5, Mockito
-   **Containerization:** Docker, Docker Compose

## 🚀 Getting Started

### Prerequisites

-   Java (JDK)
-   Apache Maven
-   Docker & Docker Compose

### Running the Application

1.  **Clone the repository:**
    ```bash
    git clone git@github.com:dramaticLemon/weather-application.git
    cd weather-application
    ```

2.  **Set up environment variables:**
    Create a `.env` file in the root of the project. The application uses the following variables, which are defined in the `docker-compose.yml` file:
    - `POSTGRES_DB`
    - `POSTGRES_USER`
    - `POSTGRES_PASSWORD`
    - `POSTGRES_PORT`
    - `PGADMIN_DEFAULT_EMAIL`
    - `PGADMIN_DEFAULT_PASSWORD`
    - `PGADMIN_PORT`
    - `REDIS_PORT`
    - `APP_PORT`

3.  **Build and run the application:**
    The easiest way to get started is by using the provided `Makefile` and Docker Compose. This command will build the Java application and then start all the necessary services (application, database, Redis).

    ```bash
    make run
    ```

    Alternatively, you can run the steps manually:
    ```bash
    # Build the application using Maven
    make build

    # Start all services using Docker Compose
    make up
    ```

4.  **Access the application:**
    -   **Web Application:** `http://localhost:8080` (or the `APP_PORT` you have configured)
    -   **pgAdmin (Database GUI):** `http://localhost:5050` (or the `PGADMIN_PORT` you have configured)

### Stopping the Application

To stop all running containers, use:
```bash
make down
```

## 🗃️ Database Migrations

Database schema changes are managed by Flyway. The migration scripts are located in the `/db/migrations` directory.

You can manually control the migrations using the `Makefile`:
-   `make flyway-migrate`: Applies pending migrations.
-   `make flyway-info`: Shows the status of all migrations.
-   `make flyway-clean`: **(Warning!)** Cleans the database, removing all objects.
-   `make flyway-validate`: Validates applied migrations against available ones.

## ✅ Testing

To run the integration tests with a specific Spring profile (e.g., TEST):
```bash
make test-profile
```
Or run the tests in a dedicated Docker container:
```bash
make test
```
