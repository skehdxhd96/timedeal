FROM openjdk:11

COPY build/libs/timedeal-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["nohup", "java", "-jar", \
"-javaagent:./pinpoint-agent/pinpoint-agent-2.4.1/pinpoint-bootstrap-2.4.1.jar",\
"-Dpinpoint.agentId=timedeal01","-Dpinpoint.applicationName=timedeal",\
"-Dpinpoint.config=./pinpoint-agent/pinpoint-agent-2.4.1/pinpoint-root.config","app.jar","2>&1","&"]
