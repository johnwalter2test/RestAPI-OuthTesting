package org.restassured;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.File;
import java.util.Map;
import java.util.Random;

public class stepDefinitions extends ProjectBase {
    private static Response response = null;

    private static RequestSpecification requestSpecification = null;
    private static String accessToken = "";
    private static String projectId = "";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String GRANT_TYPE = "grant_type";
    private static String issueId = "";
    private static final String BASE_URI = properties.getProperty("baseURI");
    private static final JSONObject requestParams = new JSONObject();

    @Given("I have prepared the headers for the request")
    public void iHavePreparedTheHeadersForTheRequest() {
        requestSpecification = RestAssured.given()
                .auth().preemptive().basic(properties.getProperty("basic.client.id"),
                        properties.getProperty("basic.client.secret"))
                .contentType(ContentType.URLENC)
                .formParam(GRANT_TYPE, PASSWORD)
                .formParam(USERNAME, properties.getProperty("auth.user"))
                .formParam(PASSWORD, properties.getProperty("auth.password"));

        customData.setPathUrl("/api/v4/projects/");
    }
//
//    public void encrypt(String content, String fileName) {
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        byte[] iv = cipher.getIV();
//
//        try (FileOutputStream fileOut = new FileOutputStream(fileName);
//             CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
//            fileOut.write(iv);
//            cipherOut.write(content.getBytes());
//        }
//    }

    @When("I send the get request for the project")
    public void iSendTheGetRequestForTheProject() {
        response = requestSpecification
                .get(BASE_URI + "/api/v4/projects/" + projectId);
    }

    @Then("I should see the status code {int}")
    public void iShouldSeeTheStatusCode(int code) {
        Assert.assertEquals(response.getStatusCode(), code);

        logger.info(response.prettyPrint());
    }

    @When("I send the POST request for the token")
    public void iSendThePOSTRequestForTheToken() {
        response = requestSpecification.when()
                .post(BASE_URI + "/oauth/token");
    }

    @And("I should see the access token in response")
    public void iShouldSeeTheAccessTokenInResponse() {
        accessToken = response.jsonPath()
                .get("access_token").toString();
    }

    @Given("I have parameterized the access token in the request header")
    public void iHaveParameterizedTheAccessTokenInTheRequestHeader() {
        requestSpecification = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken);
    }

    @When("I send a POST request for {string} with")
    public void iSendAPOSTRequestForWith(String project, Map<String, String> table) {
        String projectOperation = "";

        if (project.equalsIgnoreCase("NewProject"))
            projectOperation = customData.getPathUrl();
        else if (project.equalsIgnoreCase("NewIssues"))
            projectOperation = customData.getPathUrl() + projectId + "/issues";

        Random random = new Random();

        requestParams.put("name", table.get("name") + random.nextInt(100000));
        requestParams.put("title", table.get("title") + random.nextInt(100000));
        requestParams.put("description", table.get("description"));
        requestParams.put("path", table.get("path") + random.nextInt(100000));

        response = requestSpecification
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .when()
                .post(BASE_URI + projectOperation);
    }

    @And("I should see the project id in the response")
    public void iShouldSeeTheProjectIdInTheResponse() {
        projectId = response.jsonPath()
                .get("id").toString();
    }

    @And("I should see the issue id in the response")
    public void iShouldSeeTheIssueIdInTheResponse() {
        issueId = response.jsonPath()
                .get("iid").toString();
    }

    @When("I send the delete request for the {}")
    public void iSendTheDeleteRequestForTheProject(String type) {
        String endpoint = "";

        if (type.equalsIgnoreCase("project"))
            endpoint = BASE_URI + customData.getPathUrl() + projectId;
        else if (type.equalsIgnoreCase("issues"))
            endpoint = BASE_URI + customData.getPathUrl() + projectId + "/issues/" + issueId;

        response = requestSpecification
                .delete(endpoint);
    }

    @When("I send a PUT request with {string} as {string}")
    public void iSendAPUTRequestWithAs(String key, String value) {
        response = requestSpecification
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam(key, value)
                .when()
                .put(BASE_URI + customData.getPathUrl() + projectId + "/issues/" + issueId);
    }

    @When("I upload the metric image for the issues")
    public void iUploadTheMetricImageForTheIssues() {
        response = requestSpecification
                .contentType("multipart/form-data")
                .multiPart("file", new File("D:\\Downloads\\dummyImage.png"))
                .when()
                .post(BASE_URI + customData.getPathUrl() + projectId + "/issues/" + issueId + "/metric_images");
    }

    @And("I should see the {string} as {string} in response")
    public void iShouldSeeTheAsInResponse(String key, String value) {
        Assert.assertEquals(response.jsonPath()
                .get(key).toString(), value);
    }

    @And("I should see the {string} partially equals {string} in response")
    public void iShouldSeeThePartiallyEqualsInResponse(String key, String value) {
        Assert.assertTrue(response.jsonPath()
                .get(key).toString().contains(value));
    }

    @When("I send a POST request for {string}")
    public void iSendAPOSTRequestFor(String arg0) {
        response = requestSpecification
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URI + customData.getPathUrl() + projectId + "/issues/" + issueId + "/subscribe");
    }
}
