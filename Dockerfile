FROM amazoncorretto:17

WORKDIR /app

COPY . /app

RUN ./mvnw package -DskipTests

WORKDIR /app

COPY --from=build /app/target/your-spring-boot-app.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
