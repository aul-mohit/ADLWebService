package adl;

import static org.testng.Assert.assertEquals;

import org.codehaus.jettison.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginWebService extends mian {

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "login")
	public static Object[][] login() {
		return ReadData.dataProvider("ADLWebService", "Login");
	}

	@Test(dataProvider = "login")
	public void loginWithGivenUserName(String[] inputArray) throws Exception {
		RestAssured.baseURI = "https://restapi.demoqa.com/customer";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();

		requestParams.put("username", inputArray[2]);
		requestParams.put("password", inputArray[3]);
		requestParams.put("remember", "false");

		request.body(requestParams.toString());

		Response response = request.post("/register");

		int statusCode = response.getStatusCode();

		if (inputArray[3].toLowerCase().equals("valid"))
			assertEquals(statusCode, 200);
		else if (inputArray[3].toLowerCase().equals("invalid"))
			assertEquals(statusCode, 401);
		else
			assertEquals(false, true);
	}
}
