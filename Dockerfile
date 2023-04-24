FROM openjdk:11

COPY build/libs/timedeal-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["nohup","java","-jar","-javaagent:./pinpoint/pinpoint-bootstrap-2.2.2.jar", "-Dpinpoint.agentId=timedeal-01","-Dpinpoint.applicationName=timedeal","-Dpinpoint.config=./pinpoint/pinpoint-root.config","-Duser.timezone=Asia/Seoul", "/app.jar"]
