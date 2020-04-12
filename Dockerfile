FROM adoptopenjdk/openjdk13:alpine-slim

COPY application/target/application-*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]