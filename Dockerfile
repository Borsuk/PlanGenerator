FROM openjdk:11-jre-stretch

COPY target/PlanGenerator*.jar /app.jar
ENTRYPOINT ["java"]
CMD ["-jar", "/app.jar"]
EXPOSE 8080