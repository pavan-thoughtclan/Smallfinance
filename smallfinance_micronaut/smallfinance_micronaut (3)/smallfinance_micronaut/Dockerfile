# STAGE 1 - Create docker image
FROM openjdk:17-oracle

# copy only the artifacts we need from the first stage and discard the rest
COPY /target/smallfinance-0.1.jar /app.jar

# set the startup command to execute the jar
EXPOSE 8085
CMD ["java", "-jar",  "/app.jar"]