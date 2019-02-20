FROM openjdk:8-jre-slim

VOLUME /tmp
ARG JAR_FILE=actionsvc*.jar
RUN apt-get update
RUN apt-get -yq clean

RUN groupadd -g 990 actionsvc && \
    useradd -r -u 990 -g actionsvc actionsvc
USER actionsvc

COPY target/$JAR_FILE /opt/actionsvc.jar

ENTRYPOINT [ "java", "-jar", "/opt/actionsvc.jar" ]
