FROM openjdk:18
ARG JAR_FILE=target/pos.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
