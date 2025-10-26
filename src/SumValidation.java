import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	
	@Test
	public void sumOfCourses() {
		JsonPath js = new JsonPath(Payload.CoursePrice()); 
		int purchaseAmount = js.getInt("dashboard.PurchaseAmount");
		int totalAmount = 0;
		for(int i=0; i<js.getInt("courses.size()");i++) {
			
			totalAmount = totalAmount + js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies");
		}
		Assert.assertEquals(purchaseAmount, totalAmount);
		
	}
	}

