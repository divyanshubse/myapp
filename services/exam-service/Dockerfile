FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY ../../pom.xml ./
COPY ../../services/exam-service/pom.xml services/exam-service/pom.xml
RUN mvn -q -DskipTests dependency:go-offline
COPY ../../ .
RUN mvn -q -DskipTests -pl services/exam-service -am package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/services/exam-service/target/exam-service-*.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar","/app/app.jar"]