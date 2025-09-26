# Parking Lot Reservation System

A backend system built with **Java 17 + Spring Boot 3**, providing REST APIs for managing parking floors, slots, and reservations.

Features validation, conflict prevention, fee calculation.

---

## Features

* **Floors Management**

  * `POST /floors` → Create new parking floors
* **Slots Management**

  * `POST /slots` → Create parking slots for a floor
* **Reservations**

  * `POST /reserve` → Reserve a slot for a time range

    * Prevents overlapping bookings
    * Calculates cost based on vehicle type (rounded up to full hours)
    * Enforces max duration = 24h
    * Enforces vehicle number format (`XX00XX0000`, e.g., `KA05MH1234`)
  * `GET /availability` → List available slots for a given time range

    * Supports **pagination & sorting**
  * `GET /reservations/{id}` → Fetch reservation details
  * `DELETE /reservations/{id}` → Cancel a reservation
* **Optimistic Locking**: Protects against concurrent booking conflicts
* **Validation & Global Exception Handling**
* **Interactive API docs** via Swagger UI

---

## Tech Stack

* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA + Hibernate**
* **H2 Database** (in-memory, dev)
* **Jakarta Validation** (`@Valid`, constraints)
* **Lombok** (for boilerplate reduction)
* **Springdoc OpenAPI** (Swagger UI) // Still being implemented.

---

## Setup & Run

### 1. Clone repository after forking

```bash
git clone https://github.com/your-username/parking-lot-reservation.git
cd parking-lot-reservation
```

### 2. Build project

```bash
./mvnw clean install
```

### 3. Run application

```bash
./mvnw spring-boot:run
```

By default, the app runs at:
👉 [http://localhost:8080](http://localhost:8080)

---

## Database

* Uses **H2 in-memory DB**
* Access H2 console at:
  👉 [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* Default JDBC URL: `jdbc:h2:mem:parkingdb`
* Username: `sa`
* Password: *(empty)*

---

## API Documentation (Swagger UI) Yet to be implemented...

Once running, visit:
👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
or
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Here you can explore, test endpoints, and see example payloads.

---

## 🚦 Endpoints Overview

### 1. Floors

#### Create Floor

```http
POST /floors
```

Request:

```json
{
  "floorName": "Ground Floor"
}
```

Response:

```json
{
  "id": 1,
  "floorName": "Ground Floor",
  "slots": []
}
```

---

### 2. Slots

#### Create Slot

```http
POST /slots
```

Request:

```json
{
  "slotNumber": "A1",
  "vehicleType": "4W",
  "floorId": 1
}
```

Response:

```json
{
  "id": 1,
  "slotNumber": "A1",
  "vehicleType": "4W",
  "floor": {
    "id": 1,
    "floorName": "Ground Floor"
  },
  "reservations": []
}
```

---

### 3. Reservations

#### Create Reservation

```http
POST /reserve
```

Request:

```json
{
  "slotId": 1,
  "vehicleNumber": "KA05MH1234",
  "vehicleType": "4W",
  "startTime": "2025-09-27T10:00:00",
  "endTime": "2025-09-27T12:30:00"
}
```

Response:

```json
{
  "id": 1,
  "vehicleNumber": "KA05MH1234",
  "vehicleType": "4W",
  "slotId": 1,
  "startTime": "2025-09-27T10:00:00",
  "endTime": "2025-09-27T12:30:00",
  "cost": 90.0
}
```

#### Check Availability (with pagination & sorting)

```http
GET /availability?start=2025-09-27T10:00:00&end=2025-09-27T12:00:00&page=0&size=5&sort=slotNumber,asc
```

Response (Page object):

```json
{
  "content": [
    {
      "id": 2,
      "slotNumber": "A2",
      "vehicleType": "2W"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5
  },
  "totalElements": 10,
  "totalPages": 2,
  "last": false
}
```

#### Get Reservation by ID

```http
GET /reservations/1
```

#### Cancel Reservation

```http
DELETE /reservations/1
```

---

## Error Handling

Examples of possible error responses:

* **400 Bad Request** (validation error)

```json
{
  "errors": {
    "vehicleNumber": "vehicle number must match XX00XX0000 (e.g., KA05MH1234)"
  }
}
```

* **409 Conflict** (overlap or concurrent booking)

```json
{
  "error": "Slot already reserved for the given time range"
}
```

* **404 Not Found**

```json
{
  "error": "Reservation not found: 10"
}
```

## Note to Evaluators

I would like to clarify that I am not a primary Spring Boot developer. Due to time constraints, I had only around 3 hours to complete this assignment. While the core logic and approach are my own, I relied on ChatGPT for assistance with several aspects of the implementation, given my limited experience with Spring Boot.

My primary expertise lies in **Node.js** development, and because of my limited familiarity with Spring Boot, I had to depend on AI support to bridge the gaps. I request that this context be considered during the evaluation process to ensure a fair and transparent assessment of my work.