package blupayTests.apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class apiTests {

	public String postLogin() throws InterruptedException {

		Response response;
		//String token = null;

		RestAssured.baseURI = "https://dev2.roketapp.site/identity/user/login";
		JSONObject request = new JSONObject();
		request.put("phoneNumber", "+905322052229");
		request.put("password", "Serdar.1");
		given().header("Content-Type", "application/json").
				body(request.toString()).
		when().post(baseURI).
		then();
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		Thread.sleep(4000);

		response = (Response) RestAssured.given().header("Content-Type", "application/json").body(request.toString()).when().post(baseURI).then().log().all().extract().response();

		int statusCode = response.getStatusCode();
		JSONObject responseJson = new JSONObject(response.getBody().asString());

		String token = responseJson.getJSONObject("data").getJSONObject("tokenInformation").getString("token");
		return token;
	}

	@Test
	public void postLoginTest() throws InterruptedException {

		Response response = null;
		//String token = null;
		String buildVersion = null;

		RestAssured.baseURI = "https://dev2.roketapp.site/identity/user/login";
		JSONObject request = new JSONObject();
		request.put("phoneNumber", "+905322052229");
		request.put("password", "Serdar.1");
		given().header("Content-Type", "application/json").
				body(request.toString()).
				when().post(baseURI).
				then();
		System.out.println("=====================================================");
		System.out.println("=====================================================");
		Thread.sleep(4000);

		System.out.println(request);

		response = (Response) RestAssured.given().header("Content-Type", "application/json").body(request.toString()).when().post(baseURI).then().log().all().extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("The response code is " + statusCode);
		JSONObject responseJson = new JSONObject(response.getBody().asString());
		System.out.println("muge is here "+responseJson);

		String token = responseJson.getJSONObject("data").getJSONObject("tokenInformation").getString("token");

		if (responseJson.getJSONObject("data").getJSONObject("tokenInformation").has("token")) {
			System.out.println("The token is generated");
		}
		if (responseJson.has("meta")) {
			System.out.println("The meta is generated");
		}
		if (responseJson.has("buildVersion")) {
			System.out.println("The buildVersion is generated"+ buildVersion);
		}
	}


	@Test
	public void getUserTest() throws InterruptedException {

		Response response = null;
		String BearerToken = postLogin();
		System.out.println("muge is here for get" + BearerToken);
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
		if (responseJson.getJSONObject("data").has("name")) {
			System.out.println("The name is " + name);
		}
		if (responseJson.getJSONObject("data").has("lastname")) {
			System.out.println("The lastName is " + lastName);
		}
		if (responseJson.getJSONObject("data").has("username")) {
			System.out.println("The userName is " + userName);
		}
		if (responseJson.getJSONObject("data").has("kycStatus")) {
			System.out.println("The kycStatus is " + kycStatus);
		}
	}
}
