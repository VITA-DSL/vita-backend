FROM eclipse-temurin:21-jre-alpine

WORKDIR /vita-backend
COPY /build/libs/vita-backend-latest.jar /vita-backend.jar
ENTRYPOINT ["java","-jar","/vpp-dashboard.jar"]