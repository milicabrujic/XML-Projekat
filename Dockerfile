FROM gradle:6.5.1-jdk8 AS build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle build --no-daemon -x test

FROM openjdk:8-jdk-alpine

USER root
RUN apk --update add fontconfig ttf-dejavu

MAINTAINER Nikola Basic <ftnra84@gmail.com>

COPY --from=build /app/build/libs/app-0.0.1-SNAPSHOT.jar backend-app.jar 
ENTRYPOINT ["java", "-jar", "/backend-app.jar"]
EXPOSE 8080
