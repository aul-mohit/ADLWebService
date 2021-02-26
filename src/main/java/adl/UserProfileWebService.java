package adl;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

		assertEquals(false, false);
	}
}
