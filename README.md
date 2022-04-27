**## Mini BankC Assessment**
This is an assessment about a mini bank.

# Introduction
Welcome to Mini BankC.  

1.  A Spring Boot server that is deployed as Docker container and can handle a mini bank.
2.  There are three main endpoint for manipulating data.
3.  A H2 database used to hold the data.

Documentation is: http://localhost:8080/swagger-ui/index.html

0- Ping service
GET http://localhost:8080/v1/customers/ping

1- Add Account to current customer:
POST http://localhost:8080/v1/customers/1/accounts

2- Get customer information:
GET http://localhost:8080/v1/customers/1

This file will be completed...

