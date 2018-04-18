This is a proof of concept to demonstrate spring cloud streams and kinesis integration.
The goal is to build few stream services and have at least one of each spring stream application types in a end to end data pipeline, source, and processor and sink.

# About the end to end flow

There is no end to end flow yet decided. Actual code has only one source service but other services are going to be added to the stream pipeline shortly.

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

# To do list
- [x] Build a processor service
- [x] Build a sink service
- [x] Build an end to end data pipeline with source, processor and sink apps 
