## Dell Hangman game challenge

### Tech stack

This application was created using [Spring Boot](https://spring.io/projects/spring-boot) and 
[Angular](https://angular.io)

### Running the application (compiled ui)

You can run this aplication using gradlew command

* using a command prompt or terminal, enter in the application folder (`cd path/to/application`.
* run the command `./gradlew bootRun` (in linux) or `gradlew.bat bootRun` (in windows)
* the application will be at [http://localhost:8080](http://localhost:8080)

### Runnig the application (dev mode)

Run the server (see above)
Run client: `cd ui; npm run start`

You should access the client in http://localhost:4200

*Note:* Client is configured to proxy all */api/* request to http://localhost:8080

### Testing the application

Run the command `gradlew check` 

Test reports can be read from **build/reports/tests/index.html** and coverage in
**build/reports/jacoco/test/html/index.html**

### Web deployment

Application is published using pivotal cloud foundry cloud service.
The app can be accessed in http://hangman-turbulent-baboon-gp.cfapps.io/

In order to publish the application:

Building:

1. enter the ui folder and build the client with the command `ng build --prod`
2. Delete all files in root **src/main/resources/static/**
3. Copy the all files in **ui/dist/ui/** to server folder **src/main/resources/static**
4. build the server with command: `./gradlew build`

Note: This cold be automated with a gradle task

Deployment:

* Download and install the cf cli
* Login to your org `cf login`
* enter application directory
* run `cf push` command
