FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY ../../pom.xml ./
COPY ../../services/student-service/pom.xml services/student-service/pom.xml
RUN mvn -q -DskipTests dependency:go-offline
COPY ../../ .
RUN mvn -q -DskipTests -pl services/student-service -am package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/services/student-service/target/student-service-*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]