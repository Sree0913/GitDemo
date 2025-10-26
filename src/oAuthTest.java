import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class oAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String token_related = given()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type","client_credentials")
		.formParam("scope", "trust")
		.when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(token_related);
		String accessToken = js.getString("access_token");
		
		String response = given()
				.queryParam("access_token", accessToken)
				.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
				.then().extract().response().asString();
		System.out.println(response);
	}

}
