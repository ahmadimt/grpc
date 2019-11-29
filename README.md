# Getting Started with gRPC

## Project Structure
The project is a Gradle multi-projects build. 

````
.
├── README.md
├── build.gradle
├── gradle
│   └── wrapper
├── gradlew
├── gradlew.bat
├── grpc_client
│   ├── build
│   ├── build.gradle
│   ├── gradle
│   ├── gradlew
│   ├── gradlew.bat
│   ├── out
│   ├── settings.gradle
│   └── src
├── grpc_server
│   ├── build
│   ├── build.gradle
│   ├── gradle
│   ├── gradlew
│   ├── gradlew.bat
│   ├── out
│   ├── settings.gradle
│   └── src
└── settings.gradle
````

Project `grpc_client` has client specific code while `grpc_server` contains code that are specific for creating server.


## Building Project
From the root directory, run `./gradlew clean build`. This will clean previous build, if any, and create a new build of the project. It also generate Java classes from `helloworld.proto` into `build->generated->proto->main` in both client and server project.

## Running the Application

###  Starting the gRPC server
Navigate to `HelloWorldServer` class present in `com.imti` package, right click on it and run the `main` method. Once the server is started it will print message like below in your console.
````
Nov 29, 2019 2:53:24 PM com.imti.HelloWorldServer start
INFO: Server started, listening on port 50,051
````

### Sending message from Client
NOTE: Please make sure the server is up and running before running the client.

Navigate to `HelloWorldClient` class present in `com.imti` package, right click on it and run the `main` method. This should print something like this on console:
````
Nov 29, 2019 2:58:34 PM com.imti.HelloWorldClient greet
INFO: Will try to greet World .... 
Nov 29, 2019 2:58:34 PM com.imti.HelloWorldClient greet
INFO: Greeting: Server says hello World 
````