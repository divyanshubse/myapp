FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY ../../pom.xml ./
COPY ../../services/schedule-service/pom.xml services/schedule-service/pom.xml
RUN mvn -q -DskipTests dependency:go-offline
COPY ../../ .
RUN mvn -q -DskipTests -pl services/schedule-service -am package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/services/schedule-service/target/schedule-service-*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","/app/app.jar"]