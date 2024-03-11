FROM openjdk:17

WORKDIR /app

COPY ./target/email-ms-0.0.1-SNAPSHOT.jar /app/email-ms.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "email-ms.jar"]