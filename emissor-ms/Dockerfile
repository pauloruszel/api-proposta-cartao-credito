FROM openjdk:17

WORKDIR /app

COPY ./target/emissor-ms-0.0.1-SNAPSHOT.jar /app/emissor-ms.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "emissor-ms.jar"]