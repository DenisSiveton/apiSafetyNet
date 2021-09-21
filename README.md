# API SafetyNet
A API that will provide information about victims regarding accident such as fire and floods.
This app uses Java to run and stores the data in a JSON FIle.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

- Java 1.8
- Maven 3.6.2

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html


### Running App

Post installation of Java and Maven, you will be ready to import the code into an IDE of your choice and run the ApiSafetyNetApplication.java to launch the application.

### Testing

The app has unit tests and integration tests written. To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.
`mvn test`

### Using App with Postman

For each request, please respect the different parameters and body that are needed for the request to work. In any case, if a data is missing or wrong, a message will lead you to understand what you did wrong so that you can correct the input data.

Once the API is running go to the following URL for documentation :
http://localhost:8080/swagger-ui.html#/
