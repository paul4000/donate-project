# donate-project

Ethereum based dapp, which aim is to gather finanses for any submitted social (or any other) project or initiative. 

## Features

1. Ethereum accounts

During registration every user gets dedicated for this app Ethereum address. For now, creation of new address is obligatory, but in the future 
it might be possible to upload already existing key file. Current implementation allows for basic key file management - downloading from server 
for future, independent funds usage. 

2. Users 

In the app there is three types of users. 
- donator - probably the most popular type of user
- initiator - user who submits initiative to the system 
- executor - final recipient of collected 

3. Flow 

a. donation of the project starts when initator allows donators to send ETH for specific project 
b. end of donation is when time limit or goal amount set by initiator is reached
c. initiator has to indicate recipients of collected donations
d. then there is the time for donators to vote, if they agree on chosen distribution of funds
e. basing on majority of votes, the project is executed or cancelled 

## Requirements 

Tested on: 
- Maven 3.3.9 
- Java 11
- locally run Ethereum node by Geth 1.8.23
- contracts were compiled by solc 0.5.3
