FROM openjdk:11.0.7-jre-slim as builder

WORKDIR application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} Phase4-SCSB-UI.jar
RUN java -Djarmode=layertools -jar Phase4-SCSB-UI.jar extract

FROM openjdk:11.0.7-jre-slim
MAINTAINER HTC Recap Support "recap-support@htcindia.com"

ENV TERM=xterm

#Set Locale
RUN apt-get clean && apt-get update && apt-get install -y locales

RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

#Set EST Timezone
ENV TZ=America/New_York
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#Set Terminal
ENV TERM=xterm

WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/Phase4-SCSB-UI.jar/ ./
ENTRYPOINT java -jar -Denvironment=$ENV Phase4-SCSB-UI.jar && bash
