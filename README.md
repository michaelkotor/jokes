# Joke Service

The service contains three endpoints for receiving jokes.

## Getting Started

There are different ways to run the app:

- `docker-compose up --build` - via docker-compose, the dockerfile will be built and run locally on port 8080.
- Via IntelliJ Idea, run `JokerApplication`.
- `./gradlew bootRun` - via CLI, the app will start on port 8080.

## Actions

When the app is running, you can trigger `jokes.http` via your IDE to interact with the endpoints.

There is a dedicated endpoint to view the current cache contents.

## Assumptions

- Different APIs provide different results and structures, which is why I had to simplify their responses. For example, only one joke will be returned, simplifying the need for pagination.
- I chose the simplest rate limiter implementation.
- I didn't extensively test failing external APIs or handle HTTP 429 (Too Many Requests) codes, as it involved a considerable number of requests.
- I didn't introduce a database, even though it would increase the number of users the service can handle.

## What Could Be Done Differently?

There are numerous potential improvements:

- There are two implementations of the Joker client, with the Chuck Norris API being unstable during development and not thoroughly tested; it's now deprecated.
- Some values could be extracted into properties for easier configuration.
- The cache can be configured as external (currently it is in-memory).
- Handling API exceptions and HTTP 429 codes is not implemented, but it should be handled as a general exception.
- Swagger/Swagger UI could be added for API documentation and testing.
- The rate limiter is added only to one endpoint, but it could be more easily added to other endpoints.
- Cucumber tests are written using JUnit 4; consider updating them to a more recent version.
- Additional unit tests could be added to improve test coverage.