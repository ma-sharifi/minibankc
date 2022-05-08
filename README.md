## Mini Bank C Assessment
This is an assessment about handling a mini bank.
The assessment consists of an API to be used for opening a new "current account" of already existing customers.

# Introduction
Welcome to Mini Bank C.  
## The main features of this mini bank:
1. Open an account for existing customer (specified by customer id) and add transaction if initial credit be more than zero.
2. Get customer information by passing customer id to it.

## It also has:
1. Create a customer
2. Get list of all customers/accounts
3. Get an account by id
4. Expose a ping for considering service is up.
5. 2 language support (nl, en) as a query param. ?lang=en OR lang=nl
6. Provided the X-Request-Id header for opening an account due to POST is not idempotent. X-Request-Id: ABCD. 
   X-Request-Id-X-RateLimit-Remaining , X-Request-Id-X-RateLimit-Limit added to response header.
7. Add JMX MiniBankC-XManagement for managing Application with JMX, like clear Caffeine cache.  

## About This software:
1.  A Spring Boot server that is deployed as Docker container and can handle a mini bank.
2.  There are tow main endpoints for manipulating data /v1/customers and /v1/accounts.
3.  A H2 database used to hold the data in RAM. It has entities. Customer ,Account ,Account Transaction. 
    Each customer can have multiple accounts , and each account can have multiple accountTransactions.
4.  Used a Caffeine as a cache for storing X-Request-Id to handle Idempotence.
## MiniBankC Database Diagram
![](https://s22.picofile.com/file/8449605868/db_diagram.jpg "")

# Documentation
## Swagger: 
    documentation is: http://localhost:8080/swagger-ui/index.html
## Postman: 
    You can find Postman file in postman folder in root of project.
    For each API call, was written test with Postman.

0- Ping service
GET http://localhost:8080/v1/customers/ping

1- Add Account to current customer:
POST http://localhost:8080/v1/customers/1/accounts
After account opened, return 201 and its URL location will put on headers with key Location.

2- Get customer details:
GET http://localhost:8080/v1/customers/1

3- Get account details:
GET http://localhost:8080/v1/accounts/2

4- Get all customers
GET http://localhost:8080/v1/customers?sort=id,desc&page=0&size=2

5- Get all acounts
http://localhost:8080/v1/accounts?sort=id,asc&page=0&size=2

## Initial Configuration
1.	Apache Maven (http://maven.apache.org)  This code have been compiled with Java version 11.
2.	Git Client (http://git-scm.com)
3.  Docker(https://www.docker.com/products/docker-desktop)

# The build command
Will execute the [Spotify dockerfile plugin](https://github.com/spotify/dockerfile-maven) defined in the pom.xml file.  

## How To Use
To clone and run this application, you'll need [Git](https://git-scm.com), [Maven](https://maven.apache.org/), [Java 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html). From your command line:

```bash
# Clone this repository
$ git clone https://github.com/ma-sharifi/minibankc

You can run it from Maven directly using the Spring Boot Maven plugin.
$ ./mvnw spring-boot:run
OR
$ mvn spring-boot:run -Dspring-boot.run.profiles=dev/prod/test or without profile, it will use with default

# To build the code as a docker image, open a command-line 
# window and execute the following command and build image from Dockerfile:
$ mvn clean package dockerfile:build

# Now we are going to use docker-compose to start the actual image. To start the docker image, run your Docker locally adn stay in the directory containing src and run the following command: 
$ docker-compose -f docker/docker-compose.yml up
$ docker-compose -f docker/docker-compose.yml down

# To consider code with sonarQube after running sonarQube with docker-compose, run the following command:
$ mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=8fab5c5ec0a5c69a96695722824deb9d2d41c786
# sonarQube default login  and assword is "admin" 
# Notes : you can get token from: in right top of sonarQube pannel -> User(Administrator) > My Account > Security > Generate Tokens
```
## CI/CD
Pipelines let you define how your deployed code flows from one environment to the next.
I tried to use Heroku for CI/CD but I got error "Item could not be retrieved: Internal Server Error".
Then I managed to use Github Action. My pipeline has 2 steps. One Maven for test and buil then Docker.
```bash
For CD you must do some steps:
1- Go to you docker hub account and create a Repository. I created "minibankc" repository in my docker hub for this project.
2- Define DOCKER_USERNAME, DOCKER_PASSWORD in your github repository secrets.
    Go to Github Repository->Settings->Secrets-> New repository secret:
        Name: DOCKER_USERNAME , Value: picher. bacuse picher is my docker hub account.
        Name: DOCKER_PASSWORD , Value: ***. because *** is my docker hub password.
This video can help: https://www.youtube.com/watch?v=R8_veQiYBjI
```
## Simple UI
A simple UI provided at this address: /v1/ui/customers/1?lang=nl

## For enterprise support
1- Using cache for saving data, but in financial application it must not store any balance. just for saving naming information.
2- Sharding data between multi clusters by customer surname. 
For example if we have 2 clusters, surname that start with a-j will put into cluster 1 and surname with start k-z will put to cluster 2.
I assume frequency of letter a-j in more than frequency of letter between k-z. According to this link https://en.wikipedia.org/wiki/Letter_frequency
It means with this sharding we have the same number of data between each cluster.
customer with surname Sharifi will save into cluster 2 and customer with surname Chang will save into cluster 1, because
we split data by similar things (Sharding) according to book Art of Scalability and its Scale Cube.

## Contact
I'd like you to send me an email on <mahdi.elu@gmail.com> about anything you'd want to say about this software.


