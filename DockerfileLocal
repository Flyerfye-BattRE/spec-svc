FROM eclipse-temurin:21-jre-alpine

COPY target/spec-svc*.jar /app/spec-svc.jar
WORKDIR /app
#Expose Server Port
EXPOSE 50000
#Expose gRPC port
EXPOSE 50005
CMD ["java", "-jar", "spec-svc.jar"]