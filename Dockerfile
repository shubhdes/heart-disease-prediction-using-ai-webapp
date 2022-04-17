# Base image
FROM tomcat:8.0.51-jre8-alpine

# Delete WAR file to tomcat directory
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file to tomcat directory
ADD ./target/*.war /usr/local/tomcat/webapps/

# Run tomcat server
CMD ["catalina.sh", "run"]