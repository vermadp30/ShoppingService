FROM openjdk:17-oracle
COPY build/libs/courier-service-0.0.1-SNAPSHOT.jar courier-service.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","courier-service.jar"]