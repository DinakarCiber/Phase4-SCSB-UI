FROM openjdk:11.0.7-jre-slim as builder
WORKDIR application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} Phase4-SCSB-UI.jar
RUN java -Djarmode=layertools -jar Phase4-SCSB-UI.jar extract

FROM openjdk:11.0.7-jre-slim
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/Phase4-SCSB-UI.jar/ ./
ENTRYPOINT java -jar -Denvironment=$ENV Phase4-SCSB-UI.jar && bash