This is a proof of concept to demonstrate spring cloud streams and kinesis integration.
The goal is to build few stream services and have at least one of each spring stream application types in a end to end data pipeline, source, and processor and sink.

# About the end to end flow

There is no end to end flow yet decided. Actual code has only a source and a sink service but other services are going to be built after the data pipeline flow has been defined.

# About Stream Source Service

The current version produces a hello message every 500 milliseconds.
 
# How to run aws kinesis locally

```bash
echo "after localstack start command, kinesis should be listening to port 4568"
pip install localstack
localstack start
```

# How to run source service

```bash
echo "ensure kinesis is running locally before moving ahead"
echo "the next command will start the source service which will produce a hello message every 500ms. the application will log 'Producing Hello Message' every time a message is producer."
mvn spring-boot:run -f source-app
```

# How to run sink service

```bash
echo "ensure kinesis is running locally before moving ahead"
echo "the next command will start the sink service which starts consuming messages and logs it out to standard output"
mvn spring-boot:run -f sink-app
```

# How to read Log messages

As spring boot sleuth is being used, correlation of logs messages happens automatically. Below is a log message example and next shows how it has to be interpreted:

```
2018-04-18 22:26:31.575  INFO [sink-app,cd0303c506dbad3b,2d915de0c1380122,false] 9323 --- [   hello.sink-1] com.spring.stream.poc.sink.DataSink      : Reading Message: hello
```

|                        Value                       | Semantics                                          |
|:--------------------------------------------------:|----------------------------------------------------|
| 2018-04-18 22:26:31.575                            | current timestamp                                  |
| INFO                                               | log level                                          |
| [sink-app,cd0303c506dbad3b,2d915de0c1380122,false] | [applicationName,traceId,currentSpanId,exportable] |


# To do list
- [x] Build a processor service
- [x] Build a sink service
- [ ] Build an end to end data pipeline with source, processor and sink apps
- [ ] Run multiple sink services concurrently as a group
- [x] Enable distributed tracing with spring sleuth
