FROM openjdk:11
RUN groupadd -r spring && useradd -r -g spring spring
USER spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]