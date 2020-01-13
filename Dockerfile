FROM openjdk:8
ENV APP_HOME=/Apps/MoneyChange
WORKDIR $APP_HOME
COPY bcp-moneychange-demo-1.0.jar $APP_HOME/bcp-moneychange-demo-1.0.jar
ENTRYPOINT ["java","-jar","bcp-moneychange-demo-1.0.jar"]
EXPOSE 8080