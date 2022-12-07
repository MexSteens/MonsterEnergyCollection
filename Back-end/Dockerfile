FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/MonsterEnergyCollection-0.0.1-SNAPSHOT.jar
COPY build/libs/MonsterEnergyCollection-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
