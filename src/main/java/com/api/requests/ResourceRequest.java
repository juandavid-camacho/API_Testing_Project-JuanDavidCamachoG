package com.api.requests;

import com.api.models.Resource;
import com.api.utils.Constants;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class ResourceRequest extends BaseRequest{

    private String endpoint;

    public Response getResources(){

        endpoint = Constants.BASE_URL.concat(Constants.RESOURCES_URL);
        return requestGET(endpoint, createBaseHeaders());

    }

    public List<Resource> getResourcesEntity(Response response){

        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);

    }

}
