# Simple Kafka Consumer

I've created this project to train how to test kafka applications with SpringBoot. 

## Prerequisites

* Java 11
* Maven 3.6+

## Running manually

I prefer to test automaticaly, but if you want to do some tests mannualy using Postman, for example, you need to download Kafka and run commands below:

```shell
bin/zookeeper-server-start.sh config/zookeeper.properties
```

Open another terminal and type:

```shell
bin/kafka-server-start.sh config/server.properties
```

Now, you just need to start the application:

```shell
mvn spring-boot:run
```

To send a message to this application, see [simple-kafka-producer](https://github.com/rapesil/simple-kafka-producer) project.

When a new message is received, a new file is created at `test resources` folder containing the content message.

## Running automatically

To run the automatic test, just run:

```shell
mvn test
```

## TO DO

I plan to create more tests using Testcontainers.


