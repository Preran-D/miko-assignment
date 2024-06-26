FROM openjdk:21
ARG JAR_FILE=target/*jar
COPY ./target/simple_assembly_language-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]