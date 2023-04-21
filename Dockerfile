FROM openjdk:11

COPY build/libs/timedeal-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["nohup", "java", "-jar", \
"javaagent:/home/dongmin.na/pinpoint-agent/pinpoint-bootstrap-1.8.1.jar",\
"Dpinpoint.applicationName=timedeal",\
"Dpinpoint.agentId=timedeal01",\
"/app.jar"]
