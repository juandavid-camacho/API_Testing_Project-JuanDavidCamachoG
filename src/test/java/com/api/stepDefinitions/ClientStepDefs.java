package com.api.stepDefinitions;

import com.api.models.Client;
import com.api.requests.ClientRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ClientStepDefs {

    private static final Logger logger = LogManager.getLogger(ClientStepDefs.class);

    private final ClientRequest clientRequest = new ClientRequest();

    private Response response;

    private Client newClient;

    @Given("there are at least {int} registered clients in the system")
    public void thereAreRegisteredClientsInTheSystem(int condition){

        response = clientRequest.getClients();
        logger.info(response.jsonPath().prettify());

        List<Client> clients = clientRequest.getClientsEntity(response);

        //This if statement aims to comply with the condition that there must be at least 3 clients in the system
        if (clients.size() < condition) {

            int difference = condition - clients.size();
            logger.info("There are not enough clients in the system to meet the condition ("+condition+"), now adding "+difference+"...");

            for (int i = 0; i < difference; i++) {

                response = clientRequest.addNewClient();
                Assert.assertEquals(201, response.statusCode());
                logger.info("Added "+(i+1)+" client(s) with status code: "+response.statusCode());

            }

            response = clientRequest.getClients();
            logger.info(response.jsonPath().prettify());

        }

    }

    @When("I send a GET request to retrieve the full list of clients")
    public void iSendAGETRequestToRetrieveTheFullListOfClients(){

        response = clientRequest.getClients();
        logger.info("Full list of clients retrieved successfully!");

    }

    @Then("the response should have a status code {int}")
    public void theResponseShouldHaveAStatusCode(int statusCode){

        Assert.assertEquals(statusCode, response.statusCode());
        logger.info("Status code: "+response.statusCode());

    }

    @Then("validates the response with the client list JSON schema")
    public void validatesTheResponseWithTheClientListJSONSchema() {

        Assert.assertTrue(clientRequest.validateSchema(response, "schemas/clientListSchema.json"));
        logger.info("Schema validated successfully!");

    }

    @Given("I have a client with the following details:")
    public void iHaveAClientWithTheFollowingDetails(DataTable dataTable) {

        List<String> inputs = dataTable.row(1);
        newClient = Client.builder().name(inputs.get(0)).lastName(inputs.get(1)).country(inputs.get(2)).city(inputs.get(3)).email(inputs.get(4)).phone(inputs.get(5)).build();
        
        logger.info("Client "+newClient.getName()+" created");

    }

    @When("I send a POST request to create a new client")
    public void iSendAPOSTRequestToCreateANewClient() {

        response = clientRequest.addClient(newClient);
        logger.info("POST request sent successfully!");
        logger.info(response.jsonPath().prettify());

    }

    /**
     * This method creates a client based on the response obtained in order to
     * compare it to the client that should have been inserted (newClient)
     */
    @Then("the response should include the details of the new client")
    public void theResponseShouldIncludeTheDetailsOfTheNewClient() {

        Client clientFromAPI = clientRequest.getClient(response);
        clientFromAPI.setId(null);

        Assert.assertEquals(newClient, clientFromAPI);
        logger.info("The response contains the details of the new client");

    }

    @Then("validates the response with the client JSON schema")
    public void validatesTheResponseWithTheClientJSONSchema() {

        Assert.assertTrue(clientRequest.validateSchema(response, "schemas/clientSchema.json"));
        logger.info("Schema validated successfully!");

    }
}
