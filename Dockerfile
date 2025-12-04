FROM tomcat:9.0-jdk17

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file as ROOT application
COPY dist/BTChuong14_sendEmail.war /usr/local/tomcat/webapps/ROOT.war

# Copy setenv.sh to set environment variables for Tomcat
COPY setenv.sh /usr/local/tomcat/bin/setenv.sh
RUN chmod +x /usr/local/tomcat/bin/setenv.sh

# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]