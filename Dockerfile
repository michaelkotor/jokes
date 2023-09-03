FROM amazoncorretto:17 AS build

WORKDIR /app

COPY . /app

RUN ./gradlew clean build -x test

FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /app/build/libs/jokes-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]