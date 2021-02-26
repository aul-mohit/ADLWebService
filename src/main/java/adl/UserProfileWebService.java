package adl;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserProfileWebService extends mian {
	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "userprofile")
	public static Object[][] userprofile() {
		return ReadData.dataProvider("ADLWebService", "UserProfile");
	}

	@Test(dataProvider = "userprofile")
	public void verifyUserProfileData(String[] inputArray) throws Exception {
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

		String token = response.jsonPath().getString("token");

		//// hit user profile werb service
		Response userresponse = null;
		try {
			userresponse = RestAssured.given().params("typeId", "1111").header("Content-Type", "application/json")
					.header("Authorization", "bearer " + token).when()
					.get("https://qainternal.api.aulcorp.com/user-preferences");
		} catch (Exception e) {
			System.out.println(e.toString());	
			
		}

		int statusCode = userresponse.getStatusCode();

		assertEquals(statusCode, 200);
	}
	
	
	
}
