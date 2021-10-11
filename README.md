![Actions Status](https://github.com/lensesio/stream-reactor/actions/workflows/build.yml/badge.svg)
[<img src="https://img.shields.io/badge/docs--orange.svg?"/>](https://docs.lenses.io/connectors/)

Join us on slack [![Alt text](images/slack.jpeg)](https://launchpass.com/lensesio)

# Lenses for Apache Kafka

Lenses offers SQL (for data browsing and Kafka Streams), Kafka Connect connector management, cluster monitoring and more.

You can find more on [lenses.io](http://www.lenses.io)

# Stream Reactor


![Alt text](images/streamreactor-logo.png)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Flensesio%2Fstream-reactor.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Flensesio%2Fstream-reactor?ref=badge_shield)


A collection of components to build a real time ingestion pipeline.

## Kafka Compatibility

*    Kafka 2.5+ (Confluent 5.5) - Stream reactor 2.0.0+
*    Kafka 2.0 -> 2.4 (Confluent 5.4) - Stream reactor 1.2.7



### Connectors

**Please take a moment and read the documentation and make sure the software prerequisites are met!!**

|Connector       | Type   | Description                                                                                   | Docs |
|----------------|--------|-----------------------------------------------------------------------------------------------|------| 
| FTP/HTTP       | Source | Copy data from FTP/HTTP to Kafka.                       | [Docs](https://docs.lenses.io/connectors/source/ftp.html)                  |

## Release Notes

**2.1.4**

Support for SFTP connector 

### Building

***Requires gradle 6.0 to build.***

To build

```bash
gradle compile
```

To test

```bash
gradle test
```

To create a fat jar

```bash
gradle shadowJar
```

You can also use the gradle wrapper

```
./gradlew shadowJar
```

To view dependency trees

```
gradle dependencies # or
gradle :kafka-connect-cassandra:dependencies
```

To build a particular project

```
gradle :kafka-connect-elastic5:build
```
To create a jar of a particular project:

```
gradle :kafka-connect-elastic5:shadowJar
```

## Contributing

We'd love to accept your contributions! Please use GitHub pull requests: fork the repo, develop and test your code, 
[semantically commit](http://karma-runner.github.io/1.0/dev/git-commit-msg.html) and submit a pull request. Thanks!


## License
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Flensesio%2Fstream-reactor.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2Flensesio%2Fstream-reactor?ref=badge_large)
