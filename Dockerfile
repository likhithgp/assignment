FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/*.jar assignment.jar
EXPOSE 8092
ENTRYPOINT ["java","-jar","assignment.jar"]
