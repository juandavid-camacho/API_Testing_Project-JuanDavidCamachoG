package com.api.requests;

import com.api.utils.Constants;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class BaseRequest {

    protected Response requestGET(String endpoint, Map<String, ?> headers){

        return RestAssured.given().contentType(Constants.VALUE_CONTENT_TYPE).headers(headers).when().get(endpoint);

    }

    protected Response requestPOST(String endpoint, Map<String, ?> headers, Object body){

        return RestAssured.given().contentType(Constants.VALUE_CONTENT_TYPE).headers(headers).body(body).when().post(endpoint);

    }

    /**
     * This is a POST method with an empty body in order for a new entity to be created by default with its parameters
     * @param endpoint endpoint URL
     * @param headers headers for the POST
     * @return the response to the POST
     */
    protected Response requestEmptyPOST(String endpoint, Map<String, ?> headers){

        return RestAssured.given().contentType(Constants.VALUE_CONTENT_TYPE).headers(headers).when().post(endpoint);

    }

    protected Response requestPUT(String endpoint, Map<String, ?> headers, Object body){

        return RestAssured.given().contentType(Constants.VALUE_CONTENT_TYPE).headers(headers).body(body).when().put(endpoint);

    }

    protected Map<String, String> createBaseHeaders(){

        Map<String, String> header = new HashMap<>();
        header.put(Constants.CONTENT_TYPE, Constants.VALUE_CONTENT_TYPE);
        return header;

    }

    //This method was created here in order to reuse it in both ClientRequest and ResourceRequest
    public boolean validateSchema(Response response, String path){

        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
            return true;
        } catch (AssertionError e){
            return false;
        }

    }

}
