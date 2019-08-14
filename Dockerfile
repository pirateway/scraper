FROM java:8
ADD ./target/tm-frontend-primefaces.war .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tm-frontend-primefaces.war"]