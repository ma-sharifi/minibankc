**## Mini BankC Assessment**
This is an assessment about a mini bank.

# Introduction
Welcome to Mini BankC.  

1.  A Spring Boot server that is deployed as Docker container and can handle a mini bank.
2.  There are tow main endpoints for manipulating data.
3.  A H2 database used to hold the data in RAM.
3.  A H2 database used to hold the data in RAM.

Documentation is: http://localhost:8080/swagger-ui/index.html

0- Ping service
GET http://localhost:8080/v1/customers/ping

1- Add Account to current customer:
POST http://localhost:8080/v1/customers/1/accounts
After account opened, return 201 and its URL location will put on headers with key Location.

2- Get customer details:
GET http://localhost:8080/v1/customers/1

3- Get account details:
GET http://localhost:8080/v1/accounts/2
This file will be completed...


## Initial Configuration
1.	Apache Maven (http://maven.apache.org)  This code have been compiled with Java version 11.
2.	Git Client (http://git-scm.com)
3.  Docker(https://www.docker.com/products/docker-desktop)

## How To Use
To clone and run this application, you'll need [Git](https://git-scm.com), [Maven](https://maven.apache.org/), [Java 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html). From your command line:

```bash
# Clone this repository
$ git clone https://github.com/ma-sharifi/minibankc

# To build the code as a docker image, open a command-line 
# window and execute the following command for each of module:
$ mvn clean package dockerfile:build

# Now we are going to use docker-compose to start the actual image.  To start the docker image, stay in the directory containing src and run the following command: 
$ docker-compose -f docker/docker-compose.yml up
$ docker-compose -f docker/docker-compose.yml down

# To consider code with sonarQube after running sonarQube with docker-compose, run the following command:
$ mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=8fab5c5ec0a5c69a96695722824deb9d2d41c786
# sonarQube default login  and assword is "admin" 
# Notes : you can get token from: in right top of sonarQube pannel -> User(Administrator) > My Account > Security > Generate Tokens
```

## Contact
I'd like you to send me an email on <mahdi.elu@gmail.com> about anything you'd want to say about this software.


