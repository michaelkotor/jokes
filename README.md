# Joke Service

The service contains three endpoints for receiving jokes.

### Getting starting

There are different ways how to run the app:

 - `docker-compose up -build` - via docker-compose, the dockerfile will
be build and run locally on port 8080.
 - Via IntelliJ Idea, run `JokerApplication`
 - `./gradlew bootRun` - via CLI, the app will start on port 8080

### Actions

When the app will run, you can trigger `jokes.http` via IDE, it will 
trigger endpoints.

There are dedicated endpoint to see what is inside current cache.

### Assumptions

 - Different APIs provide different results and structures, that is why I had
to simplify their responses. For example, only one joke will be returned.
Because of that, I could omit pagination.
 - I chose the simplest rate limiter implementation
 - I didn't test failing external API or 429 code as it was a bit too much requests
 - I didn't introduce database, even it would increase number of users to handle

### What could be done else?

There are a lot of things :)

 - There are two implementations of Joker client, Chuck Norris was
unstable during the development, so it is not 100% tested and deprecated.
 - Some values could be extracted to properties
 - Cache can be configured as external (now it is in-memory)
 - API exception to 429 code is not handled, but it as a general exception
 - Swagger/Swagger UI could be added
 - Rate limiter is added only to one endpoint, but could be easier added
to other endpoints.
 - Cucumber tests are written via JUnit 4
 - Unit tests could be added