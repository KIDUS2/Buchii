FROM openjdk:8


ADD  target/bunchi-0.0.1-SNAPSHOT.war  bunchi.war
ENTRYPOINT["java","-war","bunchi.war"]

#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]