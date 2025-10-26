import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import POJO.Api;
import POJO.WebAutomation;
import POJO.getCourseAPI;

public class oAuthTestDeserialized {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[] expectedList = {"Selenium Webdriver Java","Cypress","Protractor"};
		
		String token_related = given()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type","client_credentials")
		.formParam("scope", "trust")
		.when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(token_related);
		String accessToken = js.getString("access_token");
		
		getCourseAPI gc = given()
				.queryParam("access_token", accessToken)
				.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
				.then().extract().response().as(getCourseAPI.class);
		
		System.out.println(gc.getInstructor());
		System.out.println(gc.getLinkedIn());
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apiCourses = gc.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++) {
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		ArrayList<String> actualList = new ArrayList<String>();
		
		List<WebAutomation> WebAutomationTitles = gc.getCourses().getWebAutomation();
		for(int i=0;i<WebAutomationTitles.size();i++) {
			actualList.add(WebAutomationTitles.get(i).getCourseTitle());
		}
		
		List<String> l=Arrays.asList(expectedList);
		Assert.assertTrue(actualList.equals(l));

	}

}
