FROM eclipse-temurin:21 AS build

WORKDIR /app

COPY src/ ./src/
COPY build.gradle.kts .
COPY gradle.properties .
COPY gradlew .
COPY settings.gradle.kts .
COPY gradle/ ./gradle

RUN ./gradlew clean build -x test

FROM eclipse-temurin:21

WORKDIR /app

COPY --from=build /app/build/libs/eav-platform.jar .
CMD ["java", "-jar", "./eav-platform.jar"]