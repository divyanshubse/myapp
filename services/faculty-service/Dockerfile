FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY ../../pom.xml ./
COPY ../../services/faculty-service/pom.xml services/faculty-service/pom.xml
RUN mvn -q -DskipTests dependency:go-offline
COPY ../../ .
RUN mvn -q -DskipTests -pl services/faculty-service -am package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/services/faculty-service/target/faculty-service-*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/app/app.jar"]