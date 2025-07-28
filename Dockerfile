# Etapa de build
FROM ubuntu:22.04 AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven gettext-base # gettext-base para envsubst

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

# Etapa final (imagem menor para execução)
FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y gettext-base && rm -rf /var/lib/apt/lists/*

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/deliberation-api-0.0.1-SNAPSHOT.jar app.jar

COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

COPY config/prometheus/prometheus.yml.template /app/config/prometheus/prometheus.yml.template

ENTRYPOINT ["/app/entrypoint.sh"]
