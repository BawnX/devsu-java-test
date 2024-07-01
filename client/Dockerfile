FROM openjdk:21-bullseye

WORKDIR /app

COPY . /app

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "/app/target/client-0.0.1-SNAPSHOT.jar"]