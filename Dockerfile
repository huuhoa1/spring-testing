FROM openjdk:8-jre-alpine
VOLUME /tmp
RUN mkdir /app
RUN cd /app
WORKDIR /app
ADD target/spring-testing*.jar app.jar
ADD opentracing-specialagent-1.7.4.jar opentracing-specialagent-1.7.4.jar
ADD .env .env
ADD run.sh run.sh
ENTRYPOINT sh run.sh
