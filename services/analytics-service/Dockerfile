FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY ../../pom.xml ./
COPY ../../services/analytics-service/pom.xml services/analytics-service/pom.xml
RUN mvn -q -DskipTests dependency:go-offline
COPY ../../ .
RUN mvn -q -DskipTests -pl services/analytics-service -am package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/services/analytics-service/target/analytics-service-*.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","/app/app.jar"]