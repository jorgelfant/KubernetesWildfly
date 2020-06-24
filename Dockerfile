FROM maven:3-jdk-8 AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM jboss/wildfly:latest
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/Kubernetes*.war /opt/jboss/wildfly/standalone/deployments/

ADD https://downloads.mariadb.com/Connectors/java/connector-java-1.5.9/mariadb-java-client-1.5.9.jar /opt/jboss/wildfly/modules/system/layers/base/org/mariadb/jdbc/main/
COPY librairies/module.xml /opt/jboss/wildfly/modules/system/layers/base/org/mariadb/jdbc/main/

RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#123 --silent

EXPOSE 9990

VOLUME /opt/jboss/wildfly/standalone/deployments/

USER jboss

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
