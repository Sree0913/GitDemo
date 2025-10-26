import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;

import POJO.CreateOrder;
import POJO.Order;
import POJO.RequestLogin;

public class EcommAPITesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		RequestLogin loginRequest = new RequestLogin();
		loginRequest.setUserEmail("rest@automation.com");
		loginRequest.setUserPassword("Automation@123");
		
		RequestSpecification req_login = given().spec(req).body(loginRequest);
		ResponseLogin loginResponse = req_login.when().post("api/ecom/auth/login")
				.then().extract().response().as(ResponseLogin.class);
		String loginToken = loginResponse.getToken();
		System.out.println(loginToken);
		String userID = loginResponse.getUserId();
		System.out.println(userID);
		
		// Create product
		
		RequestSpecification reqCreateProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", loginToken).build();
		RequestSpecification req_CP = given().spec(reqCreateProduct).param("productName","qwerty")
		.param("productAddedBy", userID).param("productCategory", "fashion")
		.param("productSubCategory", "shirts").param("productPrice", 11500)
		.param("productDescription", "Addias Originals").param("productFor","women")
		.multiPart("productImage", new File("C:\\Users\\sreer\\OneDrive\\Pictures\\Screenshots\\Screenshot 2025-08-10 180440.png"));
		
		String res_CP = req_CP.when().post("api/ecom/product/add-product").then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(res_CP);
		System.out.println(js.getString("productId"));
		String productID = js.getString("productId");
		
		//Create order
		
		RequestSpecification reqCreateOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", loginToken).setContentType(ContentType.JSON).build();
			
		Order order = new Order();
		order.setCountry("India");
		order.setProductOrderedId(productID);
		
		ArrayList<Order> al = new ArrayList<Order>();
		al.add(order);
		
		CreateOrder createOrder = new CreateOrder();
		createOrder.setOrders(al);
		
		RequestSpecification req_CO = given().spec(reqCreateOrder).body(createOrder);
		
		String res_CO =	req_CO.when().post("/api/ecom/order/create-order")
				.then().log().all().extract().response().asString();
		
		System.out.println(res_CO);
		
		//Delete Product
		
		RequestSpecification deleteProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", loginToken).build();
		RequestSpecification deleteProductReq= given().spec(deleteProduct).pathParam("productId", productID);
		
		String final_res = deleteProductReq.when().delete("api/ecom/product/delete-product/{productId}").then().extract().response().asString();
		
		System.out.println(final_res);
	}

}
