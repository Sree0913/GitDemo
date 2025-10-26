import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.Reusable;

public class DynamicJson {

	
	@Test(dataProvider="BooksData")
	public void checkAddBook(String isbn, String aisle) {
		
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json").body(Payload.addBook(isbn, aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = Reusable.rawToJson(response);
		String Id = js.get("ID");
		System.out.println(Id);
		
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		
		return new Object[][] {{"htydh","1234"}, {"ijkmsk","7645"},{"jjan","7465"}};
	}
}
