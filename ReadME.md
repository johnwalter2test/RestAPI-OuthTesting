# RestAPI-Oauth2.0-testing
    
## Project description
Maven framework created with layered architecture of RestAPI with Cucumber as a front-end to deal with testing in RestfulAPI CRUD operations. And it has been implemented with the factor of OAuth2.0 authorization
- We have used cucumber-testng annotations to handle the run configurations effortlessly with the annotations in cucumber file
- We have used maven-cucumber reporting plugin for the Project Reporting.
- This project can be easily integrate with any DevOps pipeline with passing the user credentials, bearer token & secret keys in the secure key vault.

## Initial Setup
1) Make a project with the default maven structure.

2) make note of the following command line instructions to run as mvn test:

    1) To run code, you will need to use the `mvn clean verify` command.
    2) The minimum java parameters to pass in are the following:
        - `"-Dcucumber.options=--tags @mycustomtag"`
    3) your entire mvn command should have all the parameters to perform the test like below,
        + `"mvn clean verify -Dcucumber.filter.tags=@crudOps"`

3) If you are using an IDE, create a run profile with these above parameters in maven edit configuration



### Contacts
-   John Walter [mailto:jsjohnwalter2@gmail.com](mailto:jsjohnwalter2@gmail.com) 
