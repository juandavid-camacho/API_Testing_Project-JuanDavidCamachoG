package com.api.stepDefinitions;

import com.api.utils.Constants;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void testStart(Scenario scenario){

        logger.info("****************************************************** \n" + scenario.getName()+"\n ******************************************************");
        RestAssured.baseURI = Constants.BASE_URL;

    }

}
