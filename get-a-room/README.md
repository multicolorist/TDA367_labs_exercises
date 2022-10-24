# Get A Room

A web application using Vaadin/Spring Boot.

This application is aimed at students to aid in finding breakout rooms on Chalmers University of Technology at Campus Johanneberg and Lindholmen.

## Running the application

Make sure you have Java version 18 installed.

The project is a Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then go to
http://localhost:8080 in your browser.

You can also import the project to your IDE. Read more on [how to import Vaadin projects to different 
IDEs](https://vaadin.com/docs/latest/flow/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, and VS Code).

## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/getaroom-1.0-SNAPSHOT.jar`

## Deploying using Docker

To build the Dockerized version of the project, run

```
docker build . -t getaroom:latest
```

Once the Docker image is correctly built, you can test it locally using

```
docker run -p 8080:8080 getaroom:latest
```
