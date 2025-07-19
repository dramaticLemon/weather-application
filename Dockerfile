FROM tomcat:10.1-jdk17
COPY target/weather-application.war /usr/local/tomcat/webapps/ROOT.war