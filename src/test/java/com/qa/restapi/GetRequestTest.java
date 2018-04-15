package com.qa.restapi;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetRequestTest {

	@Test
	public void getWeatherDetailsTest(){

		//1. Define baseURI
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

		//2. Define http request
		RequestSpecification httpRequest = RestAssured.given();

		//3. Make a GET request
		Response response = httpRequest.request(Method.GET, "/Pune");
		
		//4. Get response body
		String responseBody = response.getBody().asString();
		System.out.println("********************RESPONSE BODY********************");
		System.out.println("Response body is "+responseBody);

		//5. Get status code and validate it
		int responseCode = response.getStatusCode();
		System.out.println("********************STATUS CODE********************");
		System.out.println("Response code ==> "+ responseCode);
		assertEquals(responseCode, 200);

		//6. Get the headers
		Headers hearders = response.getHeaders();
		System.out.println("********************HEADERS********************");
		System.out.println("Headers ==> "+ hearders);

		// Validate value within response body
		Assert.assertEquals(responseBody.contains("Pune"), true);

		// Extract a node text using JsonPath
		System.out.println("**********GET VALUES USING JSONPATH**********");
		JsonPath jsonPath = response.jsonPath();

		String city = jsonPath.get("City");
		System.out.println("Value of city is:- "+city);
		
		String Temperature = jsonPath.get("Temperature");
		System.out.println("Value of Temperature is:- "+Temperature);
		Assert.assertEquals(Temperature.contains("celsius"), true);
		

	}

}
