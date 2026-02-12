# Sample stater Project - Assignment

[![Java CI](https://github.com/likhithgp/assignment/actions/workflows/servicebuild.yml/badge.svg)](https://github.com/likhithgp/assignment/actions/workflows/servicebuild.yml)
![Static Badge](https://img.shields.io/badge/3.4.2-likhith?style=plastic&logo=springboot&logoColor=green&label=Springboot)
![Static Badge](https://img.shields.io/badge/21-likhith?style=plastic&label=Java&labelColor=orange&color=black)

This Project Contains simple API POST and GET HTTP endpoints with OpenAPI doc(Swagger).

---

## 🚀 Features
* **Java 21 & Spring Boot 3**: Leveraging the latest LTS features.
* **Deterministic ID Generation**: Generates a 10-character alphanumeric `uuId` based on `userId` and `value`.
* **Custom Logging**: Integrated with Log4j2 (Synchronous configuration).
* **Docker image**: Added Docker file to generate DOcker image.
* **Automated CI/CD**: Uses GitHub Actions for building, testing, and Docker publishing.

---

## 🛠️ Tech Stack
* **Language**: Java 21
* **Framework**: Spring Boot 3
* **Build Tool**: Maven
* **Logging**: Log4j2
* **DevOps**: Docker & GitHub Actions

---

Instruction Run the service locally
------------------------
## Prerequisite to run in local:
+ Install latest Maven 
+ Install Java 21
+ Have IDE
+ Docker(Good to have)
+ Add required Software to Global Access(Environment Variable).

## Maven Run commands
* **To build**: mvn clean install
* **To start service**:mvn spring-boot:run

## Docker Run
+ Verify Docker is running in your system.
+ Open Terminal at root folder of this Project where dockerfile is present.
* **To build**: docker build -t assignment .
* **To run Docker image**: docker run -p 8092:8092 assignment

# REST Endpoint Details

* **Swagger Endpoint for local**: http://localhost:8092/swagger-ui/index.html

* **Note: Rate limiter is implemented for POST api/v1/example and GET GET api/v1/health only 5 request allowed in 10 secs**

## 1️⃣ Health Check

**Endpoint**

```
GET api/v1/health
```

**Description**

Returns the current status of the application.  
Used for uptime monitoring, load balancer checks, and container orchestration liveness/readiness probes.

**Response Example**

```json
{
  "status": "UP"
}
```

**HTTP Status Codes**

- `200 OK` – Application is running successfully.

**Production Note**

In production, this endpoint can be extended using:

```
/actuator/health
```

For deeper health diagnostics (database, disk space, external dependencies, etc.).

---

## 2️⃣ Data Processor

**Endpoint**

```
POST api/v1/example
```

**Description**

Processes incoming user data and returns a success response with a generated request identifier.

This endpoint demonstrates:
- Input validation
- Business layer delegation
- Unique request tracking
- Standardized response structure
- Rate limiting for production protection

---

## 🚦 Rate Limiting

To protect the service in high-traffic environments:

- A **rate limiter is implemented**
- Maximum **5 requests**
- Within a **10-second window**
- Per client (IP/user depending on implementation)

If the rate limit is exceeded, the API returns:

```
HTTP 429 – Too Many Requests
```

### Rate Limit Exceeded Response Example

```json
{
  "timestamp": "2026-02-11T10:15:30Z",
  "status": 429,
  "error": "Too Many Requests",
  "message": "Rate limit exceeded. Please try again later.",
  "path": "/example"
}
```

This ensures:
- Protection against abuse
- Stability under traffic spikes
- Fair usage enforcement

---

### Request Body

```json
{
  "userId": "123",
  "value": 42
}
```

### Request Field Details

- **userId** (String, required)  
  Unique identifier of the user.

- **value** (Integer, required)  
  Numeric value to be processed by the service.

Validation is enforced using Jakarta Validation annotations.

---

### Response Body

```json
{
  "status": "SUCCESS",
  "requestId": "uuid-string"
}
```

### Response Field Details

- **status**  
  Indicates processing result (e.g., `SUCCESS`).

- **requestId**  
  A generated UUID used for request tracing and observability.

---

### HTTP Status Codes

- `200 OK` – Request processed successfully.
- `400 Bad Request` – Invalid input payload.
- `429 Too Many Requests` – Rate limit exceeded.
- `500 Internal Server Error` – Unexpected system error.

---

## Error Response Format

All errors follow a standardized structure:

```json
{
  "timestamp": "2026-02-11T10:15:30Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/example"
}
```

This ensures:

- Consistent error contracts
- Clean client-side handling
- No exposure of internal stack traces
- Production-grade API standards

---

## Design Principles Applied to Endpoints

- Thin controllers with delegated business logic.
- Explicit request/response DTOs.
- Centralized exception handling.
- Boundary validation using Jakarta Validation.
- Rate limiting for traffic protection.
- Traceable responses with request identifiers.
- Production-ready HTTP semantics.

---

These endpoints are intentionally minimal but structured to reflect how real-world production microservices should be implemented and operated.

# Design Decisions
------------------------
## Package Structure & Separation of Concerns

To ensure maintainability, scalability, and clear ownership across multiple engineers, the project follows a clean layered architecture:

- **controller**
    - Contains all REST endpoint classes.
    - Responsible only for handling HTTP requests/responses.
    - Delegates business logic to the service layer.

- **service**
    - Contains business logic and core application rules.
    - Keeps controllers thin and focused on transport concerns.
    - Improves testability and separation of concerns.

- **model**
    - Contains POJO classes (DTOs) for request and response payloads.
    - Separates API contracts from internal implementation.
    - Makes the system easier to extend and refactor.

- **exception**
    - Centralized exception handling using `@ControllerAdvice`.
    - Maps exceptions to appropriate HTTP status codes.
    - Returns only required error information.
    - Prevents internal stack traces from leaking to clients.

- **configuration**
    - Contains configuration-related classes (e.g., OpenAPI config, filters, custom beans).
    - Keeps setup logic isolated and organized.

- **constants**
    - Stores reusable constant values in a single place.
    - Avoids duplication of hard-coded values.
    - Ensures easier updates and better maintainability.

---

## API Documentation

- Integrated **Swagger / OpenAPI** documentation.
- Provides clear request and response models.
- Improves developer onboarding.
- Enables easy testing through Swagger UI.
- Encourages consistent API standards across services.

---

## Validation

- Implemented input validation using **Jakarta Validation** (`@Valid`, `@NotNull`, etc.).
- Ensures invalid data is rejected at the boundary.
- Returns meaningful validation error messages.
- Improves reliability and protects business logic from bad inputs.

---

## Exception Handling Strategy

- Global exception handler ensures:
    - Proper HTTP status codes.
    - Clean, standardized error responses.
    - No exposure of internal implementation details.
    - Easy extensibility for domain-specific exceptions.

---

## Containerization

- Docker image provided for:
    - Environment consistency across dev/test/prod.
    - Simplified CI/CD integration.
    - Easy deployment to cloud/Kubernetes environments.
    - Horizontal scalability in high-traffic production systems.

- The service is designed to be **stateless**, making it suitable for scaling behind a load balancer.

---

## Rate Limiting

To make the service production-ready for high-traffic environments:

- Implemented **rate limiting** on critical endpoints.
- Allows **5 requests per 10 seconds** per client.
- Returns `HTTP 429 – Too Many Requests` when the limit is exceeded.
- Protects against abuse and traffic spikes.
- Improves system stability and fairness.

This ensures the service can survive real-world usage patterns and prevents cascading failures under sudden load.

---

## Production-Oriented Considerations

Even though the functional scope is minimal, the structure reflects production-readiness:

- Clear separation of concerns.
- Centralized error handling.
- Input validation.
- API documentation.
- Docker support.
- Clean and extensible package structure.

This ensures the service can serve as a reliable **base template** for future microservices within the organization.


## 🏗️ CI/CD Pipeline Architecture
The project uses a sophisticated two-stage pipeline defined in `.github/workflows/`:

1.  **Build Stage**:
    * Compiles the code using Maven.
    * Runs unit tests.
    * Uploads the generated `.jar` file as a GitHub Artifact.
2.  **Publish Stage**:
    * Downloads the `.jar` from the build stage.
    * Builds a Docker image using a lightweight JRE.
    * Pushes the image to Docker Hub with tags: `latest` and `build-${{ github.run_number }}`.


# Future Roadmap

If this service were to evolve into a long-term production standard, the following improvements would be added:

## Infrastructure & Deployment

- **Helm charts for Kubernetes deployment**
    - Standardized deployment templates.
    - Configurable values per environment (dev, staging, prod).
    - Easier rollout and rollback strategies.

- **Externalized configuration using a Config Server**
    - Centralized configuration management.
    - Environment-specific properties.
    - Dynamic refresh capability (if required).
    - Better separation of code and configuration.

- **CI/CD Enhancements**
    - Add Bash/Shell scripts as Docker entrypoints where necessary.
    - Standardize container startup configuration.
    - Automate versioning and image tagging.
    - Add environment-based deployment pipelines.

---

## Resilience & Fault Tolerance

If the application interacts with external APIs or downstream services:

- **Circuit Breaker (Resilience4j)**
    - Prevent cascading failures.
    - Improve system stability under partial outages.

- **Bulkhead Pattern**
    - Isolate failures.
    - Prevent resource exhaustion.
    - Improve high-traffic resilience.

- **Timeouts & Retry Mechanisms**
    - Controlled retry strategies.
    - Fail-fast behavior where appropriate.

---

## Observability & Monitoring

- **Micrometer Integration**
    - Expose application metrics.
    - Integrate with Prometheus/Grafana.
    - Improve visibility into performance and throughput.

- **Structured Logging**
    - JSON logs for centralized log aggregation.
    - Correlation IDs for distributed tracing.

- **Distributed Tracing**
    - OpenTelemetry integration.
    - End-to-end request tracing across services.

---

## Security Improvements

- Add OAuth2 / JWT-based authentication.
- Role-based authorization.
- Secure service-to-service communication.
- HTTPS enforcement.
- Rate limiting.

---

## Testing & Quality

- Increase unit and integration test coverage.
- Add contract testing for external integrations.
- Introduce static code analysis (SonarQube).
- Add performance/load testing.

---

These enhancements would ensure the service is fully production-grade, cloud-native, and aligned with enterprise microservice standards.
