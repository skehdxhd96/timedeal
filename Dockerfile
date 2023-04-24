FROM openjdk:11

COPY build/libs/timedeal-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["nohup","java","-jar","-javaagent:./pinpoint/pinpoint-bootstrap-1.8.1.jar", "-Dpinpoint.agentId=timedeal01","-Dpinpoint.applicationName=timedeal","-Dpinpoint.config=./pinpoint/pinpoint.config", "/app.jar"]
