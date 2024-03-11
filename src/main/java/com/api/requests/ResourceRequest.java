package com.api.requests;

import com.api.models.Resource;
import com.api.utils.Constants;
import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class ResourceRequest extends BaseRequest{

    private String endpoint = Constants.BASE_URL.concat(Constants.RESOURCES_URL);

    public Response getResources(){

        return requestGET(endpoint, createBaseHeaders());

    }

    public List<Resource> getResourcesEntity(Response response){

        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);

    }

    public Response editResource(Resource resource, String idToEdit){

        return requestPUT(endpoint.concat("/"+idToEdit), createBaseHeaders(), resource);

    }

    public Resource createFromJson(String json){

        Gson gson = new Gson();
        return gson.fromJson(json, Resource.class);

    }

    public Resource getResource(Response response){

        return response.as(Resource.class);

    }

    public Response addNewResource(){

        return requestEmptyPOST(endpoint, createBaseHeaders());

    }

}
