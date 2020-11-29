FROM tumakha/spring-base
ENV SERVER_PORT=8888
COPY build/libs/*.jar /app.jar
