FROM openjdk:8-alpine

COPY ./build/libs/identity-api-0.1-SNAPSHOT.jar /app/identity-api.jar
WORKDIR /app

EXPOSE 80 5100
ENTRYPOINT [ "java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "./identity-api.jar", "--server.port=80"]