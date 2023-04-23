FROM openjdk:17-jdk-slim

COPY target/toonstats.jar toonstats.jar

EXPOSE 8080
USER 1001

CMD ["java", "-Dvaadin.productionMode=true", "-jar", "./toonstats.jar"]

