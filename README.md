# Video rental service

## Structure of the service

The service is written in Java 13 and based on spring-boot 2 framework. Maven is using for building the project. The service has 3 parts (and can be split on 3 microservices):

- Customer management
- Movies management
- Renting orders management
 
The service is following to hexagonal architecture and contains 3 modules:

- `application`: middleman between REST API and domain. Also it initializes all needed resources and starts the service  
- `domain`: contains all business logic
- `infrastructure`: knows about persistence layer and has connection to database

## How to build and run the service

Note: The service uses an embedded database for storing data hence it is not necessary to install something else. `infrastructure` module has `data.xml` file in `resources` with initial data (used also for acceptance tests).

### Using an IDE

1. Clone or download the repository
2. Open the project in your favorite IDE (e.q. Intellij Idea)
3. Run spring-boot `Application` class of `application` module
4. The service will be running on the port `8008`

### Using command line

1. Clone or download the repository
2. Go to the root of the project
3. Build the project using `mvn clean package'
4. Start the service: `java -jar application/target/application-1.0-SNAPSHOT.jar`
5. The service will be running on the port `8008`

### Using Docker

1. Clone or download the repository
2. Go to the root of the project
3. Build the project using `mvn clean package'
4. Run the app using the command: `docker-compose up --build` 
5. The service will be running on the port `8008`

## How to use the service

1. Open swagger-api ui: `http://localhost:8008/swagger-ui.html`
2. Request available customers using `fetchAll` endpoint of `customer-controller`
3. Request available movies using `fetchAll` endpoint of `movie-controller`
4. Choose a customerId and movieIds and rent movies for certain days using `rentMovies` of `order-controller`. OrderIds will be returned in response
5. Return orders by ids using `returnMovies` endpoint of `order-controller`

### TODO list

- Add requests validation (e.q. `rentinDays` must be positive) including business validation (e.q. limit number of movies for renting, limit max renting days)
- Customers cannot rent a movie if an order for that movie is not closed
- Migrate to Event Sourcing pattern to create orders and send events atomically