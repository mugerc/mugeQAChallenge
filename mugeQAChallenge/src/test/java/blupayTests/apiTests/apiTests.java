package blupayTests.apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;

import static io.restassured.RestAssured.baseURI;

public class apiTests {

	public Response postLoginHelper() throws InterruptedException {
		Response response;
		//String token = null;

		RestAssured.baseURI = "https://dev2.roketapp.site/identity/user/login";
		JSONObject request = new JSONObject();
		request.put("phoneNumber", "+905322052229");
		request.put("password", "Serdar.1");

		Thread.sleep(4000);

		response = (Response) RestAssured.given().header("Content-Type", "application/json").body(request.toString()).when().post(baseURI).then().log().all().extract().response();
		return response;
	}

	public String postLogin() throws InterruptedException {
		Response response = postLoginHelper();
		JSONObject responseJson = new JSONObject(response.getBody().asString());
		String token = responseJson.getJSONObject("data").getJSONObject("tokenInformation").getString("token");
		return token;
	}

	@Test
	public void postLoginTest() throws InterruptedException {

		Response response = postLoginHelper();

		int statusCode = response.getStatusCode();
		System.out.println("The response code is " + statusCode);
		JSONObject responseJson = new JSONObject(response.getBody().asString());

		String token = responseJson.getJSONObject("data").getJSONObject("tokenInformation").getString("token");

		Boolean isTokenExist = responseJson.getJSONObject("data").getJSONObject("tokenInformation").has("token");
		Boolean isRefreshTokenExist = responseJson.getJSONObject("data").getJSONObject("tokenInformation").has("refreshToken");
		Boolean isExpireDateExist = responseJson.getJSONObject("data").getJSONObject("tokenInformation").has("expireAt");
		Boolean isMetaExist = responseJson.getJSONObject("data").has("meta");
		String buildVersion = responseJson.getJSONObject("meta").getString("buildVersion");
		String locale = responseJson.getJSONObject("meta").getString("locale");

		Assert.assertNotNull(isTokenExist);
		Assert.assertNotNull(isRefreshTokenExist);
		Assert.assertNotNull(isExpireDateExist);
		Assert.assertNotNull(isMetaExist);
		Assert.assertEquals(buildVersion, "1.0-SNAPSHOT");
		Assert.assertEquals(locale, "tr");
	}


	@Test
	public void getUserTest() throws InterruptedException {

		Response response = null;
		String BearerToken = postLogin();

		RestAssured.baseURI = "https://dev2.roketapp.site/identity/user";
		JSONObject request = new JSONObject();

		response = (Response) RestAssured.given().header("Content-Type", "application/json").header("Authorization", "Bearer " + BearerToken).when().get(baseURI).then().log().all().extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("The response code is " + statusCode);
		JSONObject responseJson = new JSONObject(response.getBody().asString());
		String name = responseJson.getJSONObject("data").getString("name");
		String lastName = responseJson.getJSONObject("data").getString("lastname");
		String userName = responseJson.getJSONObject("data").getString("username");
		Boolean kycStatus = responseJson.getJSONObject("data").getBoolean("kycStatus");
		String accountType = responseJson.getJSONObject("data").getString("accountType");
		String buildVersion = responseJson.getJSONObject("meta").getString("buildVersion");
		String locale = responseJson.getJSONObject("meta").getString("locale");

		Assert.assertEquals(name, "Hasan Serdar");
		Assert.assertEquals(lastName, "Hamzaoğulları");
		Assert.assertEquals(userName, "anonymous4291732");
		Assert.assertEquals(kycStatus.booleanValue(), true);
		Assert.assertEquals(accountType, "PRIVATE");
		Assert.assertEquals(buildVersion, "1.0-SNAPSHOT");
		Assert.assertEquals(locale, "tr");
	}
}
