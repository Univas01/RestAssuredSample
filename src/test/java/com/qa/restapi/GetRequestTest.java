package com.qa.restapi;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;


public class GetRequestTest {
	
	Logger log = Logger.getLogger(GetRequestTest.class);

	@Test
	public void getWeatherDetailsTest(){
		log.info("********************STARTING getWeatherDetailsTest TEST********************");
		//1. Define baseURI
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

		//2. Define http request
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.log().body();
		
		
		//3. Make a GET request
		Response response = httpRequest.request(Method.GET, "/Pune");
		//httpRequest.log().body();
		
		
		//4. Get response body
		String responseBody = response.getBody().prettyPrint();
		log.info("********************RESPONSE BODY********************");
		log.info("+++++++++++++++++++++++++++++++++");
		log.info("Response body is "+responseBody);
				
		//5. Get status code and validate it
		int responseCode = response.getStatusCode();
		log.info("********************STATUS CODE********************");
		log.info("Response code ==> "+ responseCode);
		Assert.assertEquals(responseCode, 200);
		
		//6. Get the headers
		Headers hearders = response.getHeaders();
		log.info("********************HEADERS********************");
		log.info("Headers ==> "+ hearders);
		System.out.println("********************HEADERS********************");
		System.out.println("Headers ==> "+ hearders);
		
		RequestLogSpecification req = httpRequest.log();
		System.out.println("RequestLogSpecification "+req);

		// Validate value within response body
		Assert.assertEquals(responseBody.contains("Pune"), true);

		// Extract a node text using JsonPath
		log.info("**********GET VALUES USING JSONPATH**********");
		JsonPath jsonPath = response.jsonPath();

		String city = jsonPath.get("City");
		System.out.println("Value of city is:- "+city);
				
		String Temperature = jsonPath.get("Temperature");
		System.out.println("Value of Temperature is:- "+Temperature);
		Assert.assertEquals(Temperature.contains("celsius"), true);
				

	}

}
