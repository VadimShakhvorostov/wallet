FROM maven:3.9.4-eclipse-temurin-21 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar ./app.jar

CMD ["java", "-XX:+UseG1GC", "-jar", "./app.jar"]