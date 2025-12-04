FROM tomcat:9.0-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*
COPY dist/BTChuong14_sendEmail.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]