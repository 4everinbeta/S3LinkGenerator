#S3LinkGenerator

A simple REST service to generate a short-lived link to your s3 document

##Usage

The s3LinkGenerator takes a bucket and objectid from s3 as part of the path in a GET request and uses those values to generate a short-lived link to a given s3 resource.

For example, the following curl command:
~~~ bash
$ curl http://localhost:8080/s3linkgenerator/samples/s3linkgeneratorsample.txt
~~~

Would result in the following jason response:
~~~json
{
  "bucket": "s3linkgenerator",
  "objectId": "samples/s3linkgeneratorsample.txt",
  "link": "https://s3linkgenerator.s3-us-west-2.amazonaws.com/samples/s3linkgeneratorsample.txt?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20160930T194907Z&X-Amz-SignedHeaders=host&X-Amz-Expires=86399&X-Amz-Credential=AKIAJQOZ3ROVOZCXA2BA%2F20160930%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Signature=743113d7f0c9efe2b79718632672c23791339f8bade8fc32e5d3605a5a0b77d9"
}
~~~

However, that key is just the deafult on the server that is running the service. If I need to provide my own key I can do that as well (Not EVER recommended unless you have a significant SSL/TLS connection) via query parameters.

That would look something like the following:

~~~bash
$ curl "http://localhost:8080/s3linkgenerator/samples/s3linkgeneratorsample.txt?key=AKIAJU4VW24R5AOKX2KQ&secret=by/Abd/EoNd8r2afa6X4TwLyoS2OYdLIaXlw%2Bwuk"
~~~

Would result in the following jason response:
~~~json
{
	"bucket": "s3linkgenerator",
	"objectId": "samples/s3linkgeneratorsample.txt",
	"link": "https://s3linkgenerator.s3-us-west-2.amazonaws.com/samples/s3linkgeneratorsample.txt?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20161004T022732Z&X-Amz-SignedHeaders=host&X-Amz-Expires=86399&X-Amz-Credential=AKIAJU4VW24R5AOKX2KQ%2F20161004%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Signature=03be768e9dc446654a69b19fdcaa1ebf2ea81c718b5e1fab8de28b6a19768292"
}
~~~

##Configuration

The S3 Link generator has a very simple configuration. It is a very simple DropWizard service. Therefore, it will expect a configuration file to be passed to it on execution.

Besides logging config, there is only one other value that must be specified and that is the "expiration" root-level value. By default, it is set to one hour.

~~~YAML
expiration: 3600000
~~~

I have included an example configuration that includes this default as well as a simple slf4j configuration.

~~~YAML
expiration: 3600000

# Logging settings.
logging:

  # The default level of all loggers. Can be OFF, ERROR, WARN, INFO, DEBUG, TRACE, or ALL.
  level: INFO

  # Logger-specific levels.
  loggers:

    # Sets the level for 'com.foureverinbeta.s3LinkGenerator' to DEBUG.
    com.foureverinbeta.s3LinkGenerator: DEBUG

# Logback's Time Based Rolling Policy - archivedLogFilenamePattern: ./logs/s3LinkGenerator-%d{yyyy-MM-dd}.log.gz
# Logback's Size and Time Based Rolling Policy -  archivedLogFilenamePattern: ./logs/s3LinkGenerator-%d{yyyy-MM-dd}-%i.log.gz
# Logback's Fixed Window Rolling Policy -  archivedLogFilenamePattern: ./logs/s3LinkGenerator-%i.log.gz

  appenders:
    - type: console
    - type: file
      threshold: INFO
      logFormat: "%-6level [%d{HH:mm:ss.SSS}] [%t] %logger{5} - %X{code} %msg %n"
      currentLogFilename: ./logs/s3LinkGenerator.log
      archivedLogFilenamePattern: ./logs/s3LinkGenerator-%d{yyyy-MM-dd}-%i.log.gz
      archivedFileCount: 7
      timeZone: UTC
      maxFileSize: 10MB
~~~
