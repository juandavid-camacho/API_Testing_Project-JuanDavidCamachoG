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

    public boolean validateSchema(Response response, String path){

        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
            return true;
        } catch (AssertionError e){
            return false;
        }

    }

}
