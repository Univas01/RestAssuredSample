package com.qa.restapi;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static  io.restassured.matcher.RestAssuredMatchers.*;
import static  org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ToolsQAPostRequest {
	
	@Test
	public void postRequest() throws IOException{
				
		String requestBody = generateString("toolsQAPayload.json");
			
		RestAssured.baseURI =  "http://restapi.demoqa.com";
		Response res = given().
			header("Content-Type", "application/json").
			body(requestBody).
			log().all().
			
		when().
			post("/customer/register").	
			
		then().
		//assertThat().statusCode(201).
		extract().response();
		
		String response = res.asString();
		System.out.println("Response");
		System.out.println(response);
		
		int statusCode = res.statusCode();
		System.out.println("Status Code is :- " +statusCode);
		
		String faultId = res.jsonPath().get("FaultId");
		System.out.println(faultId);

	}
	
	public static String generateString(String filename) throws IOException {
		String filePath = System.getProperty("user.dir")+"/Payloads/"+filename;
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

}
