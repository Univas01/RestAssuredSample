package com.qa.restapi;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostRequestTest {

	@SuppressWarnings("unchecked")
	@Test
	public void postWeatherDetailsTest(){

		//Step 1: Create a Request pointing to the Service Endpoint
		//a. Define baseURI
		RestAssured.baseURI = "http://restapi.demoqa.com/customer";

		//b. Define http request
		RequestSpecification httpRequest = RestAssured.given();
		
		//httpRequest.auth().basic("username", "ola");
		

		//Step 2: Create a JSON request which contains all the fields
		JSONObject requestParameter = new JSONObject();

		requestParameter.put("FirstName", "Ohlar17");
		requestParameter.put("LastName", "Adeola17");
		requestParameter.put("UserName", "ohlarsimpleuser17");
		requestParameter.put("Password", "application17");
		requestParameter.put("Email",  "ohlarsimpleuser17@gmail.com");

		//Step 3: Add JSON body in the request and send the Request

		//a. Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");

		//b. Add the JSON to the body of the request
		httpRequest.body(requestParameter.toJSONString());

		//c. Post the request and check the response
		Response response = httpRequest.post("/register");

		// Step 4: Validate the Response with jsonPath

		//a. statusCode validation
		int statusCode = response.statusCode();
		System.out.println("StatusCode ==> " +statusCode);
		/*Assert.assertEquals(statusCode, 201);

		//b. successCode validation
		String successCode = response.jsonPath().get("SuccessCode");
		System.out.println("SuccessCode ==> "+successCode);
		Assert.assertEquals(successCode, "OPERATION_SUCCESS");

		//c. message validation
		String message = response.jsonPath().get("Message");
		System.out.println("Message ==> "+ message);
		Assert.assertEquals(message, "Operation completed successfully");*/

		System.out.println("===============================================");
		System.out.println(response.getBody().prettyPrint());
		System.out.println("===============================================");

		if(statusCode == 201){
			AssertJUnit.assertEquals(response.jsonPath().get("SuccessCode"), "OPERATION_SUCCESS");
			AssertJUnit.assertEquals(response.jsonPath().get("Message"), "Operation completed successfully");
		}
		else if(statusCode != 201){
			AssertJUnit.assertEquals(response.jsonPath().get("FaultId"),"User already exists");
			AssertJUnit.assertEquals(response.jsonPath().get("fault"),"FAULT_USER_ALREADY_EXISTS");

		}

		/*ResponseBody body = response.getBody();

		if(response.statusCode() == 200){
			RegistrationFailureResponse responseBody = body.as(RegistrationFailureResponse.class);
			Assert.assertEquals("User already exists", responseBody.FaultId);
			Assert.assertEquals("FAULT_USER_ALREADY_EXISTS", responseBody.fault);
		}
		else if(response.statusCode() == 201){
			RegistrationSuccessResponse responseBody = body.as(RegistrationSuccessResponse.class);
			Assert.assertEquals("OPERATION_SUCCESS", responseBody.SuccessCode);
			Assert.assertEquals("Operation completed successfully", responseBody.Message);
		}*/
	}
}
