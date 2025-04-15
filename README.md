Spring Boot Java 17 Application - ioco Challenge
Welcome to the ioco Challenge project! This is a Spring Boot 17 application designed for backend services. Below are the setup instructions for pulling the project from GitHub and running it locally.

**Prerequisites**

**Before you start, ensure you have the following installed on your machine:**
- Java 17+ (JDK)
- Maven (Recommended: v3.8+)
- Git


**Check Installed Versions**

Run:

java -version

mvn -version

git --version


**If Java or Maven is missing, install them:**

- For Java 17: Download JDK 17
- For Maven: Download Maven


**Clone & Install Dependencies**

**Clone the Repository**

git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git

cd YOUR_REPO_NAME


**Running the Project**

**Build & Run the Application**

Run:

mvn clean install

mvn spring-boot:run


**Starts the Spring Boot server at:**

http://localhost:8090


**Additionally you can run the application in your chosen IDE**

**Verify API Endpoints**

Test API using Postman or curl:

curl http://localhost:8090/api/countries

to run the next test you will need to run the first curl as this is the entry point for the UI and populates the countries list.

curl http://localhost:8090/api/countries/{countryName}


**Testing the Application**

Run Unit Tests:

mvn test


**Runs Spring Boot unit tests using JUnit & Mockito.**

Run Integration Tests;

mvn verify


**Building & Packaging**

Build a JAR File:

mvn package


**Generates a JAR file inside target/.**

Run JAR Locally:

java -jar target/YOUR_APP.jar

Starts the application from a compiled JAR.


**Deploying the Application**

Docker (Optional)
To deploy using Docker, create a Dockerfile:

FROM openjdk:17-jdk

WORKDIR /app

COPY target/YOUR_APP.jar /app/YOUR_APP.jar

EXPOSE 8090

CMD ["java", "-jar", "YOUR_APP.jar"]


**Build and run:**

docker build -t your-app .

docker run -p 8080:8080 your-app
