## Requirements
- Docker

## Main flow

![Main flow](/main-flow.png)

## Components
### Elasticsearch
Store log data, listen on `9200` port

### Kibana
View log data on web app, listen on `5601` port. Browse to http://localhost:5601.

### Logstash
Handle logging from 3 sources:
- The spring log files on `/logs` directory
- Via `9999` port with Json format
- Via `8888/udp` port with Syslog format

See pipeline config at `/logstash/pipeline`
### Spring boot app
Open `logback.xml` file to see more detail.

Request to http://localhost:8080/exception to app logging exception.

1. Config logback logging into file
```xml
<appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <encoder>
        <pattern>${FILE_LOG_TO_READ_PATTERN}</pattern>
    </encoder>
    <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
        <fileNamePattern>${log.directory}/${log.fileName}.%d{yyyy-MM-dd}.%i.${log.fileExtension}</fileNamePattern>
        <maxFileSize>5MB</maxFileSize>
        <maxHistory>2</maxHistory>
    </rollingPolicy>
</appender>
``` 

2. Logtash library

Insall maven on `pom.xml`
```xml
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>6.3</version>
</dependency>
```
Config logback file
```xml
<appender name="SOCKET" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
    <destination>localhost:9999</destination>
    <encoder class="net.logstash.logback.encoder.LogstashEncoder">
    </encoder>
    <includeCallerData>true</includeCallerData>
    <keepAliveDuration>5 minutes</keepAliveDuration>
</appender>
```

3. Config logback logging into syslog
```xml
<appender name="SYSLOG" class="ch.qos.logback.classic.net.SyslogAppender">
    <syslogHost>localhost</syslogHost>
    <port>8888</port>
    <facility>LOCAL0</facility>
    <throwableExcluded>true</throwableExcluded>
    <suffixPattern>${SYSLOG_PATTERN}</suffixPattern>
</appender>
```

## Run
1. To start ELK container

```cmd
cd /elk
docker-compose up
```

2. To run Spring app
```cmd
cd /elk/spring-app
./mvnw spring-boot-run
``` 

3. Login http://localhost:5601 with `elasticsearch/changeme` credential

4. See elasticsearch indices at http://localhost:5601/app/management/data/index_management/indices
![Elasticsearch indices](/Screenshot-2021-12-04-123441.png)

5. Setting source config for stream log at http://localhost:5601/app/logs/settings