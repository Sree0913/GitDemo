import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;



public class CreateIssue {


	public static void main(String[] args) throws IOException {
	RestAssured.baseURI = "https://sreeramkantheti-apitesting.atlassian.net/";
		
		String response = given()
		.header("Content-Type","application/json")
		.header("Authorization","Basic c3JlZXJhbWthbnRoZXRpMUBnbWFpbC5jb206QVRBVFQzeEZmR0YwMmU5T3FkWF9zZC15Yk9MQlRvaWc4RjBFQlh4RVVQSTBlMjNQZXpwQ1pWVGdWOFNPZDRXQ20tVm9SRXBELVRfUXFMdXpEVHpGdFhNdWpiU19qbXVqM3J0WGg3UzBuMHFRMDI5c0NUVUNNSGVHMUxBLWlhUVVLNTAxRm03akpTaHBXTnBUWFNONjB6Y21FbTRpeDFYTDBvcjBUblNmMWNQcnAzenlLd052RmZFPTJCMUI3MTU2")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SCRUM\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Issue2 created with Rest Assured API automation.\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}")
		.post("rest/api/3/issue")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String issueId = js.getString("id");
		System.out.println(issueId);
		
	
		//Add Attachment
		

	given().pathParam("key", issueId)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic c3JlZXJhbWthbnRoZXRpMUBnbWFpbC5jb206QVRBVFQzeEZmR0YwMmU5T3FkWF9zZC15Yk9MQlRvaWc4RjBFQlh4RVVQSTBlMjNQZXpwQ1pWVGdWOFNPZDRXQ20tVm9SRXBELVRfUXFMdXpEVHpGdFhNdWpiU19qbXVqM3J0WGg3UzBuMHFRMDI5c0NUVUNNSGVHMUxBLWlhUVVLNTAxRm03akpTaHBXTnBUWFNONjB6Y21FbTRpeDFYTDBvcjBUblNmMWNQcnAzenlLd052RmZFPTJCMUI3MTU2")
		.multiPart("file",new File("C:\\Users\\sreer\\Downloads\\ADD-DeletePlaceAPIs.docx"))
		.post("rest/api/3/issue/{key}/attachments")
		.then().log().all().statusCode(200);
	}

}
