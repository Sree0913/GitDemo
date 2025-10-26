import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.Payload;
import files.Reusable;


public class Basic {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
	// Validate if the Add place API is working as expected
	//addPlace() is a method created in Payload class to send json body
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json\r\n"
				).body(new String(Files.readAllBytes(Paths.get("C:\\Users\\sreer\\OneDrive\\Desktop\\Postman work space\\addPlace.json"))))
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String place_id=js.getString("place_id");
		
		System.out.println(place_id);
		
	// Update the place with new address
		
	String newAddress = "70 winter walk, London";
		
	given().queryParam("key", "qaclick123").header("Content-Type", "application/json\r\n")
	.body("{\r\n"
			+ "\"place_id\":\""+place_id+"\",\r\n"
			+ "\"address\":\""+newAddress+"\",\r\n"
			+ "\"key\":\"qaclick123\"\r\n"
			+ "}")
	.when().put("/maps/api/place/update/json")
	.then().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
	
	// Get the place after updating the new address
	
	String updatedPlace = given().queryParam("key", "qaclick123").queryParam("place_id", place_id)
	.when().get("/maps/api/place/get/json")
	.then().assertThat().statusCode(200).extract().response().asString();
	//System.out.println(updatedPlace);

	String Actual_address = Reusable.rawToJson(updatedPlace).getString("address");
	
	System.out.println(Actual_address);
	Assert.assertEquals(Actual_address, newAddress);
	
	
	}

}
