FROM eclipse-temurin:21-jre-alpine

WORKDIR /vita-backend
COPY /build/libs/vita-backend-0.0.1-SNAPSHOT.jar /vita-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/vita-backend-0.0.1-SNAPSHOT.jar"]