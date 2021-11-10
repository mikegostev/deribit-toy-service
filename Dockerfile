FROM openjdk:16-alpine

ENV db xxx
ENV dbuser xxx
ENV dbpass xxx

EXPOSE 8080

RUN mkdir /app

COPY build/libs/deribit-demo-0.0.1-SNAPSHOT.jar /app/deribit-demo-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/app/deribit-demo-0.0.1-SNAPSHOT.jar"]