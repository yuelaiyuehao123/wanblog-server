FROM java:8

COPY *.jar /app.jar

CMD ["--server.port=8080"]

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=pro"]

# docker build -t wanblog:1.0 .
# docker run -d -p 80:8080 wanblog:1.0