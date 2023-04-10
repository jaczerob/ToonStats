FROM openjdk:17-jdk-slim

COPY target/toonstats-runner.jar toonstats.jar

EXPOSE 8080
USER 1001

CMD ["java", "-jar", "./toonstats.jar", "-Dquarkus.http.host=0.0.0.0"]

