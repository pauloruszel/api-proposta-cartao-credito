FROM openjdk:17

WORKDIR /app

COPY ./target/cartoes-ms-0.0.1-SNAPSHOT.jar /app/cartoes-ms.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "cartoes-ms.jar"]