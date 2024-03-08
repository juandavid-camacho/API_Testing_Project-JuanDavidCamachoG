package com.api.stepDefinitions;

import com.api.models.Client;
import com.api.requests.ClientRequest;
import io.cucumber.datatable.DataTable;
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

    @Given("there are registered clients in the system")
    public void thereAreRegisteredClientsInTheSystem(){

        response = clientRequest.getClients();
        logger.info(response.jsonPath().prettify());

        List<Client> clients = clientRequest.getClientsEntity(response);

        Assert.assertEquals(clients.size(), 2);
    }

    @When("I send a GET request to retrieve the full list of clients")
    public void iSendAGETRequestToRetrieveTheFullListOfClients(){

        response = clientRequest.getClients();

    }

    @Then("the response should have a status code {int}")
    public void theResponseShouldHaveAStatusCode(int statusCode){

        Assert.assertEquals(statusCode, 200);

    }

    @Then("validates the response with the client list JSON schema")
    public void validatesTheResponseWithTheClientListJSONSchema() {

        Assert.assertTrue(clientRequest.validateSchema(response, "schemas/clientListSchema.json"));
        logger.info("Schema validated successfully!");

    }

    @Given("I have a client with the following details:")
    public void iHaveAClientWithTheFollowingDetails(DataTable dataTable) {

        Map<String, String> clientEntered = dataTable.asMap(String.class, String.class);
        Client newClient = Client.builder().name(clientEntered.get("name")).build();
        
        logger.info("Client "+newClient.getName()+" created");

    }

    @When("I send a POST request to create a new client")
    public void iSendAPOSTRequestToCreateANewClient() {
    }
}
