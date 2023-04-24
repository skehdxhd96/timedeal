FROM openjdk:11

COPY build/libs/timedeal-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","-javaagent:/home/dongmin.na/pinpoint-agent-2.2.2/pinpoint-bootstrap-2.2.2.jar", "-Dpinpoint.agentId=timedeal-01","-Dpinpoint.applicationName=timedeal","-Dpinpoint.config=/home/dongmin.na/pinpoint-agent-2.2.2/pinpoint-root.config","-Duser.timezone=Asia/Seoul", "/app.jar"]
