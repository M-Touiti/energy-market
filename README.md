# Agregio Solutions - Energy Market Application

This is a Spring Boot application designed to manage energy offers and production parks for different market types (e.g., Primary Reserve, Secondary Reserve, Fast Reserve). The application allows users to create offers, list offers by market type, and manage production parks.

---

## Table of Contents

1. [Features](#features)
2. [Technologies](#technologies)
3. [Setup](#setup)
4. [API Documentation](#api-documentation)
4. [To do](#to-do)

---

## Features

- **Create Offers**: Create energy offers with hourly blocks and associated production parks.
- **List Offers by Market Type**: Retrieve offers for a specific market type.
- **Manage Production Parks**: Create and manage production parks (e.g., Solar, Wind, Hydraulic).
- **Validation**: Ensure data integrity with input validation and error handling.

---

## Technologies

- **Spring Boot 3**: Backend framework for building the application.
- **Spring Data JPA**: For database interactions.
- **Mockito**: For unit testing and mocking dependencies.
- **JUnit 5**: For writing and running tests.
- **H2 Database**: In-memory database for development and testing.
- **Maven**: Build and dependency management tool.

---

## Setup

### Prerequisites

- Java 21
- Maven 3.9.9

### Steps to Run the Application

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/M-Touiti/energy-market.git
   cd agregio-energy-market
   ``` 
2. **Build the Project**:
   ```bash
   mvn clean install
   ```
3. **Run the Application**:
   - use this command:
   ```shell
   mvn spring-boot:run -pl application
   ```
   - Or run the jar
   ```shell
   java -jar ./application/target/application-1.0.0-SNAPSHOT.jar
   ```

4. **Access the Application**:
   ```
   The application will be available at http://localhost:8080
   ```
   
## API Documentation

#### Parks
1. **Post /api/v1/parks**:
   ```json
   {
      "name": "park Solar 1", 
      "type": "SOLAR", 
      "capacity": 5.0 
   }
   ```

1. **GET /api/v1/parks/{marketType}**:
   ```
   Example: GET /api/v1/parks/RESERVE_PRIMAIRE
   ```

#### Offers

1. **Post /api/v1/offers**:
   ```json
   {
    "marketType": "RESERVE_PRIMAIRE",
    "blocks": [
        {
            "startTime": "2025-03-05T12:30:45",
            "durationHours": 3,
            "quantity": 50,
            "floorPrice": 100.0,
            "parks": [
                { "id": 1 },
                { "id": 2 }
            ]
        },
        {
            "startTime": "2025-03-05T12:30:45",
            "durationHours": 3,
            "quantity": 40,
            "floorPrice": 90.0,
            "parks": [
                { "id": 1 },
                { "id": 2 }
            ]
        }
    ]
   }
   ```

2. **GET /api/v1/offers/{marketType}**:
   ```
   Example: GET /api/v1/offers/RESERVE_PRIMAIRE  
   ```
#### Database H2  
   ```
   GET localhost/h2  
   ```

## To do
- add unit and integration tests.
- add pagination
- add logs