FROM gcr.io/distroless/java:8

COPY target/spring-app-0.0.1-SNAPSHOT.jar app.jar


CMD ["-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Europe/Istanbul", "-Dfile.encoding=UTF-8", "app.jar"]
