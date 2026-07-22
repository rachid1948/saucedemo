FROM --platform=linux/amd64 selenium/standalone-chrome:latest

USER root

RUN apt-get update && apt-get install -y \
    maven \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY . .

CMD ["mvn", "clean", "test"]
