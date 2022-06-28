# Sequra Back End Coding Challenge

## Challenge overview

For this application I have decided to use a hexagonal architecture as the way of organizing the code and exposing the endpoints. Being a small app with just to features this type of architecture may be a bit overkilled but is the one that I felt more comfortable with because allows the app to evolve and be changed with ease, although at the beginning it might look a bit confusing and complicated to follow.

For the entry points in the app, I have defined the Port-Adapter layer (these would be the primary ports in a more classic definition of Hex architecture) including API endpoints, event listeners, cli endpoints, scheduled tasks...

The next layer of this application is the app one, this layer will be the one with the classes in charge of orchestrating the communication between the Domain/Business logic and the Ports-adapters.

In the domain layer we have all the entities and the services related with our domain. This way our domain is not coupled with the other layers, allowing easier changes.

### Simplifications

To make the app a bit simpler I have our entities/domain classes coupled with JPA in a purer way of implementing Hex architecture this would not be correct because the connection with a DB should be separated from the domain using ports the same way the entry points work.

### Testing Approach
I have approach testing as API unit testing instead of Class unit testing. I know is more conventional to have unit testing for each class but this couples testing with app structure and not allows refactoring.

## Missing parts
According to the challenge I had 3h to do it, I spent around 3-4h, including all the configurations needed. I have left behind the API endpoint thinking that the endpoint will be pointless without the business logic that calculates all the disbursements.

To implement the endpoint, I would use a structure similar to the one used in the Disbursements Calculation feature. Port-Adapter-Use Case-Service approach.

## Improvements

1. Implement some type of Retriable logic for the Disbursement calculation for the same week, right now if it fails, it must wait until next Monday. Emit information if it fails (logs, event) and flag orders disbursed so it is easier to see which ones have fail if someone has to check them in the DB.
   

## Run App
### Dependencies
Java 11 (Recommended Zulu-11.0.15)

Docker & Docker compose

### Start app
1. Run ```docker compose up``` in project root (This will start postgres DB)

2. Run ```mvn clean install``` in project root (Use option ```-DskiptTests``` to skip tests) 
   1. If you do not have installed you can use the maven wrapper in the project. ```./mvnw clean install```

3. Run ```java -jar target/challenge-0.0.1-SNAPSHOT.jar``` to start the application.

## DB credentials
DB name: dev_seq

user: seq

pass: seq01