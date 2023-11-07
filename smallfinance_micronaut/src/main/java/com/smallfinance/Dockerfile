# STAGE 1 - Create docker image
FROM eclipse-temurin:17.0.5_8-jre

# copy only the artifacts we need from the first stage and discard the rest
COPY /target/smallfinance-0.1.jar /app.jar

# set the startup command to execute the jar
EXPOSE 8085
CMD ["java", "-jar",  "/app.jar"]