import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;

import POJO.AddPlace;
import POJO.Location;

public class SpecBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		AddPlace addPlace = new AddPlace();
		addPlace.setAccuracy(50);
		addPlace.setAddress("29, side layout, cohen 09");
		addPlace.setLanguage("Kannada");
		addPlace.setName("Appala gowda");
		addPlace.setPhone_number("(+91) 983 893 3937");
		addPlace.setWebsite("http://google.com");
		
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		addPlace.setLocation(loc);
		
		ArrayList<String> al = new ArrayList();
		al.add("shoe park");
		al.add("shop");
		addPlace.setTypes(al);
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON)
		.addQueryParam("key", "qaclick123").build();
		
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
		RequestSpecification request = given().spec(req).body(addPlace);
		
		String resp = request.when().post("/maps/api/place/add/json")
		.then().log().all().spec(res).extract().response().asString();
		
		System.out.println(resp);
		
	}

}
