FROM eclipse-temurin:21-jre-alpine

WORKDIR /vita-backend
COPY /build/libs/vpp-latest.jar /vpp.jar
ENTRYPOINT ["java","-jar","/vpp.jar"]