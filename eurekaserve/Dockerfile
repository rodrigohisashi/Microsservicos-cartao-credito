FROM maven:3-eclipse-temurin-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar ./eureka.jar
EXPOSE 8761
ENTRYPOINT java -jar eureka.jar