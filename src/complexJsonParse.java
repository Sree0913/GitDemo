import files.Payload;
import io.restassured.path.json.JsonPath;

public class complexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// CoursePrice() method in Payload class is created to mock the API response
		JsonPath js = new JsonPath(Payload.CoursePrice()); 
		
		//Print the size of the array in json
		System.out.println(js.getInt("courses.size()"));
		
		//Print purchase amount
		System.out.println(js.getInt("dashboard.PurchaseAmount"));
		
		//Print title of first course
		System.out.println(js.getString("courses[0].title"));
		
		//Print all course titles and respective prices
		for(int i=0; i<js.getInt("courses.size()");i++) {
			System.out.println(js.get("courses["+i+"].title")+" "+js.getInt("courses["+i+"].price"));
		
		}
		
		//Print no of copies sold by a specific course
		System.out.println("Print no of copies sold by a RPA course");
		for(int i=0; i<js.getInt("courses.size()");i++) {
			if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")) {
				System.out.println(js.getInt("courses["+i+"].copies"));
				break;
			}
		
		}
		
		//Verify if sum of all courses matches with purchase amount
		
		int purchaseAmount = js.getInt("dashboard.PurchaseAmount");
		int totalAmount = 0;
		for(int i=0; i<js.getInt("courses.size()");i++) {
			
			totalAmount = totalAmount + js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies");
		}
		if(purchaseAmount == totalAmount) {
			System.out.println("Price is tallied..");
		}
		
	}

}
