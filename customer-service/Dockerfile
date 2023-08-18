FROM openjdk:17-oracle
COPY build/libs/customer-service-0.0.1-SNAPSHOT.jar customer-service.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar","customer-service.jar"]