FROM openjdk:8-jdk-alpine
LABEL maintainer="jocricardo.ids@gmail.com"
WORKDIR /workspace
ADD target/login*.jar app.jar

#ENV userPoolId="us-east-1_vraKu8Ycp"
#ENV clientId="6p5f03cdksugff57ouc39053uf"
#ENV awsregion="us-east-1"

ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -jar /workspace/app.jar
EXPOSE 8080