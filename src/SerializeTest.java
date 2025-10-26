import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;

import POJO.AddPlace;
import POJO.Location;

public class SerializeTest {

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
		
		String res = given().queryParam("key", "qaclick123")
		.body(addPlace)
		.when().post("/maps/api/place/add/json")
		.then().log().all().extract().response().asString();
		
		System.out.println(res);
		
	}

}
