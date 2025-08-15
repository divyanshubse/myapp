FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY ../../pom.xml ./
COPY ../../services/auth-service/pom.xml services/auth-service/pom.xml
RUN mvn -q -DskipTests dependency:go-offline
COPY ../../ .
RUN mvn -q -DskipTests -pl services/auth-service -am package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/services/auth-service/target/auth-service-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]