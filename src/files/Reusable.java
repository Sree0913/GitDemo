package files;

import io.restassured.path.json.JsonPath;

public class Reusable {
	
	public static JsonPath rawToJson(String s) {
		
		JsonPath jp = new JsonPath(s);
		return jp;
	}

}
