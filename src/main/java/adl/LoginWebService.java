package adl;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

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

		Map<String, String> jsonAsMap = new HashMap<>();
		jsonAsMap.put("username", inputArray[2]);
		jsonAsMap.put("password", inputArray[3]);
		jsonAsMap.put("remember", "false");
		Response response = null;
		try {
			response = RestAssured.given().header("Content-Type", "application/json").body(jsonAsMap).when()
					.post("https://qainternal.api.aulcorp.com/login");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		int statusCode = response.getStatusCode();

		if (inputArray[4].toLowerCase().equals("valid"))
			assertEquals(statusCode, 200);
		else if (inputArray[4].toLowerCase().equals("invalid"))
			assertEquals(statusCode, 401);
		else
			assertEquals(false, true);
	}
}
