# Cheese Supply and Distribution Management System Backend

| | |
|---|---|
| **Student** | Umutoniwase Aliane |
| **Student ID** | 27771 |
| **Project** | Cheese Supply and Distribution Management System |

A REST API built with Spring Boot, Spring Data JPA, and PostgreSQL for managing cheese types, depots, distributors, retailers, supply orders, and delivery batches.

## Technologies

- Java 17
- Spring Boot 4.1.2
- Spring Web
- Spring Data JPA and Hibernate
- PostgreSQL
- Maven
- Postman

## Domain model

| Entity | Table | Main relationships |
|---|---|---|
| `CheeseType` | `cheese_type` | Many-to-many with `Distributor` |
| `Depot` | `depot` | One depot can be assigned to distributors, subject to the application's rule that a depot is not taken by another distributor |
| `Distributor` | `distributor` | Many-to-one with `Depot`; many-to-many with `CheeseType` |
| `Retailer` | `retailer` | One retailer can have many `SupplyOrder` records |
| `SupplyOrder` | `supply_order` | Many-to-one with `Retailer` and `Distributor` |
| `DeliveryBatch` | `delivery_batch` | Many-to-one with `SupplyOrder` |

`OrderStatus` has four values: `PLACED`, `DISPATCHED`, `DELIVERED`, and `REJECTED`. Allowed transitions are `PLACED` to `DISPATCHED` or `REJECTED`, and `DISPATCHED` to `DELIVERED` or `REJECTED`. `DELIVERED` and `REJECTED` are final statuses.

## How to run the API

### Prerequisites

- Java 17 or later
- Maven 3.6.3 or later
- PostgreSQL running on `localhost:5432`

### 1. Clone the repository

Open PowerShell and run:

```powershell
git clone https://github.com/Aliane-spec/cheese-supplyand-distribution-management-system-backend.git
cd cheese-supplyand-distribution-management-system-backend
```

### 2. Create the database

In PostgreSQL or pgAdmin, create a database named `Cheese supply` if it does not already exist:

```sql
CREATE DATABASE "Cheese supply";
```

Hibernate creates or updates the entity tables after a successful connection. It does not create the database itself.

### 3. Configure the local database password

Create `src/main/resources/application.properties` with the following settings:

```properties
spring.application.name=cheese-supplyand-distribution-management-system
spring.datasource.url=jdbc:postgresql://localhost:5432/Cheese%20supply
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8085
```

The local `application.properties` file is excluded from Git. In the same PowerShell window you will use to run the app, set your own PostgreSQL password:

```powershell
$env:DB_PASSWORD = "your PostgreSQL password"
```

Replace the example text with the password configured for your local PostgreSQL `postgres` user. Do not commit or share the password.

### 4. Start the application

From the project folder, run:

```powershell
mvn spring-boot:run
```

Keep the terminal open while using the API. When Spring Boot reports that the application has started, the API is available at `http://localhost:8085`.

To compile the project and run its Maven tests, use:

```powershell
mvn clean test
```

## Seed data

Seed records are added through the API save endpoints; they are not included in the source code. Add records in this order because later records refer to earlier records:

1. **Cheese types:** Gouda, Mozzarella, Cheddar, and Feta (leave Feta unassigned if demonstrating the unused-cheese query).
2. **Depots:** numbers 1, 2, 3, and 4 (leave depot 4 unassigned if demonstrating the free-depots query).
3. **Distributors:** create three distributors and assign depots and cheese types.
4. **Retailers:** create five retailers, including two with owner name `Umutoni`.
5. **Supply orders:** create orders across the required dates and statuses, including at least two with status `DELIVERED`.

Use the relevant `GET /api/{resource}/all` endpoint to retrieve generated IDs.

## API endpoints

All endpoints use `http://localhost:8085`.

### Part A — CRUD

The main CRUD resources are `cheese-types`, `depots`, `distributors`, `retailers`, and `supply-orders`.

| Method | Endpoint pattern | Purpose |
|---|---|---|
| `POST` | `/api/{resource}/save` | Create a record |
| `GET` | `/api/{resource}/all` | List records |
| `GET` | `/api/{resource}/{id}` | Get a record by ID |
| `PUT` | `/api/{resource}/update/{id}` | Update a record |
| `DELETE` | `/api/{resource}/delete/{id}` | Delete a record |

### Part B — Derived queries

| ID | Method | Endpoint |
|---|---|---|
| B1 | `GET` | `/api/retailers/by-owner-name?ownerName=umutoni` |
| B2 | `GET` | `/api/supply-orders/by-status?status=PLACED` |
| B3 | `GET` | `/api/supply-orders/between?start=2026-10-01&end=2026-10-31` |

### Part C — JPQL queries

| ID | Method | Endpoint |
|---|---|---|
| C1 | `GET` | `/api/distributors/by-cheese-type?name=gouda` |
| C2 | `GET` | `/api/depots/free` |
| C3 | `GET` | `/api/cheese-types/unused` |
| C4 | `GET` | `/api/retailers/of-distributor/{distributorId}` |

### Part D — Business rules

| ID | Method | Endpoint | Expected behavior |
|---|---|---|---|
| D1 | `POST` | `/api/supply-orders/save` | Reject past dates with `400`; reject duplicate non-rejected distributor orders on the same date with `409`; new orders start as `PLACED` |
| D2 | `PATCH` | `/api/supply-orders/{id}/status?status=DISPATCHED` | Allow only valid status transitions |
| D3 | `PUT` | `/api/distributors/{distributorId}/cheese-types/{cheeseTypeId}` | Add a cheese type; reject duplicates |
| D4 | `PATCH` | `/api/distributors/{distributorId}/depot?depotNumber=4` | Move a distributor to a free depot |

### Part E — Delivery batches

| ID | Method | Endpoint | Expected behavior |
|---|---|---|---|
| E1 | `POST` | `/api/delivery-batches/save?supplyOrderId={id}` | Save a batch only for a `DELIVERED` supply order |
| E2 | `GET` | `/api/delivery-batches/retailer/{licenseNumber}` | Return retailer batches, newest first |
| E3 | `DELETE` | `/api/delivery-batches/delete/{id}` | Delete a delivery batch |

A supply order with delivery batches cannot be deleted until its batches are removed; the supply-order delete endpoint returns `409` in that case.

### Part F — Reports and bulk updates

| ID | Method | Endpoint |
|---|---|---|
| F1 | `GET` | `/api/supply-orders/stats/by-status` |
| F2 | `PATCH` | `/api/supply-orders/cancel-day?distributorId={id}&date=2026-10-20` |

### Bonus

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/dashboard/summary` | Counts retailers, distributors, free depots, today's orders, placed orders, and delivery batches |
| `GET` | `/api/supply-orders/page?page=0&size=5&sort=orderDate,desc` | Returns a page of supply orders with page totals |

## Test with Postman

1. Import `cheese-supplyand-distribution-management-system-midterm.postman_collection.json` from the repository root.
2. In Postman Web, select the Postman Desktop Agent so requests can reach `localhost`.
3. The collection's base URL is `http://localhost:8085`.
4. Replace collection UUID variables with IDs returned by the corresponding `/api/{resource}/all` endpoints when testing against a different database.
5. Use a `DELIVERED` order for E1 and its retailer's license number for E2.
6. Send requests individually. Some requests create, update, or delete database records.
7. For the live demonstration, show successful and failing responses and explain the related repository, service, and controller logic.

## Project structure

```text
src/main/java/com/example/cheesesupply/
├── CheeseSupplyApplication.java
├── controller/   REST endpoints
├── service/      business rules
├── repository/   Spring Data JPA queries
└── domain/       entities and order status
```
