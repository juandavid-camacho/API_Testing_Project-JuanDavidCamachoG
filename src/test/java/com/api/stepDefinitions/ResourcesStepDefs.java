package com.api.stepDefinitions;

import com.api.models.Resource;
import com.api.requests.ResourceRequest;
import io.cucumber.datatable.DataTable;
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

    @Given("there are at least {int} registered resources in the system")
    public void thereAreRegisteredResourcesInTheSystem(int condition){

        response = resourceRequest.getResources();
        logger.info(response.jsonPath().prettify());

        List<Resource> resources = resourceRequest.getResourcesEntity(response);

        //This if statement aims to comply with the condition that there must be at least 5 resources in the system
        if (resources.size() < condition) {

            int difference = condition - resources.size();
            logger.info("There are not enough resources in the system to meet the condition ("+condition+"), now adding "+difference+"...");

            for (int i = 0; i < difference; i++) {

                response =resourceRequest.addNewResource();
                Assert.assertEquals(201, response.statusCode());
                logger.info("Added "+(i+1)+" resource(s) with status code: "+response.statusCode());

            }

            response =resourceRequest.getResources();
            logger.info(response.jsonPath().prettify());

        }

    }

    @When("I send a GET request to retrieve the full list of resources")
    public void iSendAGETRequestToRetrieveTheFullListOfResources(){

        response = resourceRequest.getResources();
        logger.info("Full list of resources retrieved successfully!");

    }

    @Then("the response should have status code {int}")
    public void theResponseShouldHaveAStatusCode(int statusCode){

        Assert.assertEquals(statusCode, response.statusCode());
        logger.info("Status code: "+response.statusCode());

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
        logger.info("PUT request sent successfully!");
    }

    /**
     * This method creates a resource based on the response obtained in order to
     * compare it to the fields of the resource that should have been updated
     */
    @Then("the response should have the following details")
    public void theResponseShouldHaveTheFollowingDetails(DataTable dataTable) {

        Resource updatedResourceFromAPI = resourceRequest.getResource(response);

        List<String> details = dataTable.row(1);
        Resource detailsToCompare = Resource.builder().name(details.get(0)).trademark(details.get(1)).stock(Integer.parseInt(details.get(2))).price(Float.parseFloat(details.get(3))).description(details.get(4)).tags(details.get(5)).active(Boolean.valueOf(details.get(6))).id(lastResource.getId()).build();

        Assert.assertEquals(detailsToCompare, updatedResourceFromAPI);
        logger.info("The response contains the details provided!");

    }

    @Then("validates the response with the resource JSON schema")
    public void validatesTheResponseWithTheResourceJSONSchema() {

        Assert.assertTrue(resourceRequest.validateSchema(response, "schemas/resourceSchema.json"));
        logger.info("Schema validated successfully!");

    }
}
