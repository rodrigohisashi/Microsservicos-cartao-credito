# Use a imagem base com Gradle e JDK 17
FROM gradle:jdk17 as build
WORKDIR /app
COPY . .
# Execute o comando Gradle para construir o projeto
RUN gradle build --no-daemon

# Use a imagem base do OpenJDK para a execução do aplicativo
FROM openjdk:17
WORKDIR /app
# Copie o arquivo JAR construído durante a fase de construção
COPY --from=build /app/build/libs/*.jar ./app.jar

ARG RABBITMQ_SERVER=rabbitmq
ARG EUREKA_SERVER=localhost
ENTRYPOINT java -jar app.jar



