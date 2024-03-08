package com.api.stepDefinitions;

import com.api.models.Resource;
import com.api.requests.ResourceRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;

public class ResourcesStepDefs {

    private static final Logger logger = LogManager.getLogger(ClientStepDefs.class);

    private final ResourceRequest resourceRequest = new ResourceRequest();

    private Response response;

    private Resource lastResource;

    private Resource newResource;

    @Given("there are registered resources in the system")
    public void thereAreRegisteredResourcesInTheSystem(){

        response = resourceRequest.getResources();
        logger.info(response.jsonPath().prettify());

        List<Resource> resources = resourceRequest.getResourcesEntity(response);

    }

    @When("I send a GET request to retrieve the full list of resources")
    public void iSendAGETRequestToRetrieveTheFullListOfResources(){

        response = resourceRequest.getResources();

    }

    @Then("the response should have status code {int}")
    public void theResponseShouldHaveAStatusCode(int statusCode){

        Assert.assertEquals(statusCode, response.statusCode());

    }

    @Then("validates the response with the resource list JSON schema")
    public void validatesTheResponseWithTheResourceListJSONSchema() {

        Assert.assertTrue(resourceRequest.validateSchema(response, "schemas/resourceListSchema.json"));
        logger.info("Schema validated successfully!");

    }

    @Given("I retrieve the last resource")
    public void iRetrieveTheLastResource() {

        response = resourceRequest.getResources();
        List<Resource> resources = resourceRequest.getResourcesEntity(response);
        lastResource = resources.get(resources.size()-1);
        
        logger.info("Last resource retrieved: "+lastResource);

    }

    @When("I send a PUT request to update the last resource")
    public void iSendAPUTRequestToUpdateTheLastResource(String json) {

        newResource = resourceRequest.createFromJson(json);
        newResource.setId(lastResource.getId());

        response = resourceRequest.editResource(newResource, newResource.getId());
        logger.info(response.getStatusCode());
    }
}
