# access-recorder
Application which represents a store for access records.
The store holds all access records using in-memory data structures and serve them via RESTful API with JSON.


## Getting Started

### Prerequisites:
- JDK 8
- Maven 3.5.2
- Spring boot 2.1.6
- Lombok


### Installing

* Build with tests:
```
$ mvn clean install
```

* To run just the tests:
```
$ mvn clean test
```



### Running

* Run the application:
```
Run the class ChargingSessionStoreApplication.java from IDE
```
or
```
$ mvn spring-boot:run
```



### The rest API will be available at:
```
http://localhost:8080
```



### Examples of requests using [httpie](https://httpie.org/) (a command line HTTP client):
```
$ http POST :8080/chargingSessions startedAt='2019-07-10T15:53:02.888'

$ http PUT :8080/chargingSessions/ef385c6f-3e8e-4bee-9491-1d8930424f38 suspendedAt='2019-07-10T15:54:16.254'

$ http :8080/chargingSessions/ef385c6f-3e8e-4bee-9491-1d8930424f38

$ http :8080/chargingSummary
```
