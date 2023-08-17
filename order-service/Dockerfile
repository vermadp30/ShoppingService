FROM openjdk:17-oracle
COPY build/libs/order-service-0.0.1-SNAPSHOT.jar order-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","order-service.jar"]