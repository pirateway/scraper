FROM java:8
ADD ./target/scraper.war .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "scraper.war"]