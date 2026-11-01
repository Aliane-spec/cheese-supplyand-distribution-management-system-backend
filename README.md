# Cheese Supply and Distribution Management System Backend

## Student information

- **Student name:** Umutoniwase Aliane
- **Student ID:** 27771
- **Project name:** Cheese Supply and Distribution Management System

## Project overview

A Spring Boot REST API for managing cheese types, depots, distributors, retailers, supply orders, and delivery batches. It uses Spring Data JPA and Hibernate to persist data in PostgreSQL.

## Requirements

- Java 17
- Maven 3.6.3 or later
- PostgreSQL
- Postman Web and the Postman Desktop Agent for local API testing

## Database setup

1. Start PostgreSQL.
2. Create the database if it does not already exist:

   ```sql
   CREATE DATABASE "Cheese supply";
   ```

3. Create `src/main/resources/application.properties` in the project. This local file is excluded from Git because it contains database credentials. Add the following settings and replace `YOUR_POSTGRES_PASSWORD` with the password for your local PostgreSQL `postgres` user:

   ```properties
   spring.application.name=cheese-supplyand-distribution-management-system
   spring.datasource.url=jdbc:postgresql://localhost:5432/Cheese%20supply
   spring.datasource.username=postgres
   spring.datasource.password=YOUR_POSTGRES_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   server.port=8085
   ```

Hibernate creates or updates the entity tables after the application connects. It does not create the PostgreSQL database or populate the assignment records. Add the required records through the API.

## Run the API

Open PowerShell in the project root and run:

```powershell
mvn spring-boot:run
```

Wait until the console reports that the application started. The API base URL is `http://localhost:8085`. Keep the terminal open while testing.

To compile the project and run its Maven tests:

```powershell
mvn clean test
```

## Test with Postman

Import `cheese-supplyand-distribution-management-system-midterm.postman_collection.json` from the project root. In Postman Web, select the **Desktop Agent** so requests can reach the API running on `localhost`.

Run requests individually, not with **Run collection**, because some requests create, update, or delete database records. Requests that require UUIDs need IDs from this database; retrieve them from the corresponding `/api/{resource}/all` endpoint and update the collection variables. For delivery-batch creation (E1), use a supply order with status `DELIVERED`. E2 uses that order retailer's license number. Be ready for the live demonstration to show a successful response, an error response, and briefly explain the code for the selected requirements.

## Assignment features and API

All endpoints use `http://localhost:8085`.

- **Part A — Domain model and CRUD:** cheese types, depots, distributors, retailers, and supply orders.
- **Part B — Derived queries:** retailer owner search, supply-order status and date-range searches.
- **Part C — JPQL queries:** distributors by cheese type, free depots, unused cheese types, and retailers of a distributor.
- **Part D — Business rules:** safe order creation and status transitions, distributor cheese-type assignment, and depot changes.
- **Part E — Delivery batches:** save batches for delivered orders, retailer batch history, deletion, and protection from deleting orders with batches.
- **Part F — Reports and bulk updates:** supply-order counts by status and cancel-day updates.
- **Bonus:** dashboard summary and paginated supply orders.

Main route groups:

| Resource | Route |
|---|---|
| Cheese types | `/api/cheese-types` |
| Depots | `/api/depots` |
| Distributors | `/api/distributors` |
| Retailers | `/api/retailers` |
| Supply orders | `/api/supply-orders` |
| Delivery batches | `/api/delivery-batches` |
| Dashboard | `GET /api/dashboard/summary` |

## GitHub submission

The public repository for this assignment is named `cheese-supplyand-distribution-management-system-backend`. The repository includes this README, the Postman collection JSON, Maven configuration, and application source code. Do not commit the local `application.properties` file or publish its database password.
