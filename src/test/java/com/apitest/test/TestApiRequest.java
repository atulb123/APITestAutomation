package com.apitest.test;

import org.json.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.apitest.utils.GetPropertiesData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.*;

public class TestApiRequest {
	@Test(priority = 1)
	public void verifyRecordsCount() throws Throwable {
		FileHandler fh = new FileHandler("MyLogFile.log");
		RestAssured.given().contentType(ContentType.JSON).header("charset", "UTF-8").when()
				.get(GetPropertiesData.getPropertyValue("baseURI")
						+ GetPropertiesData.getPropertyValue("request_1_path"))
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).body("size()", is(100));
	}

	@Test(priority = 2)
	public void verifySingleRecord() throws Throwable {
		RestAssured.given().contentType(ContentType.JSON).header("charset", "UTF-8").when()
				.get(GetPropertiesData.getPropertyValue("baseURI")
						+ GetPropertiesData.getPropertyValue("request_2_path"))
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().body("id", equalTo(1));
	}

	@Test(priority = 3)
	public void verifyInvalidResponse() throws Throwable {
		RestAssured.given().contentType(ContentType.JSON).header("charset", "UTF-8").when()
				.get(GetPropertiesData.getPropertyValue("baseURI")
						+ GetPropertiesData.getPropertyValue("request_3_path"))
				.then().assertThat().statusCode(404).log().all();
	}

	@Test(priority = 4)
	public void createRecord() throws Throwable {
		RestAssured.given()
				.body("{\r\n" + "\"title\": \"foo\",\r\n" + "\"body\": \"bar\",\r\n" + "\"userId\": 1\r\n" + "}")
				.header("charset", "UTF-8").when()
				.post(GetPropertiesData.getPropertyValue("baseURI")
						+ GetPropertiesData.getPropertyValue("request_1_path"))
				.then().assertThat().statusCode(201).and().body("id", equalTo(101));
	}

	@Test(priority = 5)
	public void updateRecord() throws Throwable {
		JSONObject obj = new JSONObject();
		obj.put("id", 1);
		obj.put("title", "abc");
		obj.put("body", "xyz");
		obj.put("userId", 1);
		RestAssured.given().body(obj).header("charset", "UTF-8").when()
				.put(GetPropertiesData.getPropertyValue("baseURI")
						+ GetPropertiesData.getPropertyValue("request_2_path"))
				.then().assertThat().statusCode(200).and().body("id", equalTo(obj.get("id")));
	}

	@Test(priority = 6)
	public void deleteRecord() throws Throwable {
		RestAssured.given().header("charset", "UTF-8").when().delete(
				GetPropertiesData.getPropertyValue("baseURI") + GetPropertiesData.getPropertyValue("request_2_path"))
				.then().assertThat().statusCode(200);
	}
}
