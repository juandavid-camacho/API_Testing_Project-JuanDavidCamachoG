package com.api.requests;

import com.api.models.Client;
import com.api.utils.Constants;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class ClientRequest extends BaseRequest{

    private String endpoint = Constants.BASE_URL.concat(Constants.CLIENTS_URL);;

    public Response getClients(){

        return requestGET(endpoint, createBaseHeaders());

    }

    public List<Client> getClientsEntity(Response response){

        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Client.class);

    }

    public Response addClient(Client client){

        return requestPOST(endpoint, createBaseHeaders(), client);

    }

    public Response addNewClient(){

        return requestEmptyPOST(endpoint, createBaseHeaders());

    }

    public Client getClient(Response response){

        return response.as(Client.class);

    }

}
