# Electronic Taxi Service

An electronic taxi service application built using Java Spring Boot, Postgres (with PostGIS extension), Redis, and RabbitMQ. This application handles geospatial data to match passengers with the closest available drivers and incorporates a fully-featured JWT authentication system with a two-stage login process.

## Features

- **Geographical Matching**: Efficiently finds the closest driver for a passenger request using PostGIS and JTS (Java Topology Suite).
- **Caching and Locks**: Implements Redis for placing locks on the payment process, ensuring consistency.
- **JWT Security**: Full JWT-based authentication with role-based access control (Passenger, Driver, and a two-stage login process using a `null` role).
- **Support for Different Transportation Types**: Calculates the estimated time and cost based on transportation type properties and geographical distance.
- **Microservices Architecture**: The application is composed of three microservices that communicate via RabbitMQ:
  - **Core Service**: Manages orders, drivers, passengers, and payment.
  - **SMS Service**: Sends notifications about payment status and order creation (simulated with system output).
  - **Logging Service**: Logs core service activities into a separate PostgreSQL database.
- **Asynchronous Communication**: Utilizes RabbitMQ for event-driven communication between services.
- **PostgreSQL & PostGIS**: Handles all geographical data in PostgreSQL with the PostGIS extension for spatial queries.

## Technologies Used

- **Java Spring Boot**: Backend framework for developing the core service and microservices.
- **PostgreSQL + PostGIS**: Database and spatial extension for managing and querying geographical data.
- **JTS (Java Topology Suite)**: For handling geospatial operations.
- **Redis**: Used for placing locks on payment processing.
- **RabbitMQ**: Message broker used for asynchronous communication between services.
- **JWT (JSON Web Token)**: Authentication and role-based authorization mechanism.
- **Docker**: Each microservice is containerized for easy deployment and scalability.

## System Architecture

### 1. **Core Service** 
   - **Functionality**: Manages passenger requests, finds drivers, processes payments, and coordinates rides.
   - **Geo-Spatial Matching**: Uses PostGIS and JTS tools to locate the closest driver to a passenger's request.
   - **Locking Mechanism**: Redis is used to implement locks during the payment process to ensure data consistency.

### 2. **SMS Service**
   - **Functionality**: Simulates sending SMS notifications to users regarding payment status and order creation.
   - **Data Persistence**: Stores contact information of users in its own PostgreSQL database and updates this information whenever changes are made in the core service.

### 3. **Logging Service**
   - **Functionality**: Logs every action taken in the core service into a separate PostgreSQL database for auditing and monitoring purposes.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/electronic-taxi-service.git
   ```
2. Navigate into the project directory:
   ```bash
   cd electronic-taxi-service
   ```
3. Set up the databases (PostgreSQL, PostGIS, Redis).
4. Build and run the microservices:
   ```bash
   mvn clean install
   docker-compose up
   ```
5. The application will now be available on the following ports:
   - Core Service: `http://localhost:8080`
   - SMS Service: `http://localhost:8181`
   - Logging Service: `http://localhost:8282`

## Usage

1. **Authentication**: 
   - Sign up as a user and perform a two-stage login process to become either a Passenger or Driver.
   - Use JWT for secure access to the endpoints.

2. **Creating Orders**: 
   - Passengers can request rides by specifying their location. The system finds the nearest driver and calculates the cost and time estimates based on the transportation type and distance.

3. **Notifications**: 
   - Simulated SMS notifications will be printed in the logs whenever payments are made or orders are created.

4. **Logs**: 
   - The Logging Service stores all activities for tracking and auditing purposes.

## Future Enhancements

- Implement real SMS notifications using third-party services like Twilio.
- Add real-time location tracking for drivers and passengers.
- Introduce more sophisticated fare calculations based on traffic, time of day, and dynamic pricing models.
- Expand the two-stage login system to support additional roles, like administrators.
