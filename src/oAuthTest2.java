import static io.restassured.RestAssured.given;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;

public class oAuthTest2 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		WebDriverManager.chromedriver().setup(); 
        WebDriver driver = new ChromeDriver();
        
        driver.manage().window().maximize();
        
        String URL = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
        driver.get(URL);
        String username = "apiautomation95@gmail.com";
        String Pass = "7989518135@Sr";
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(username);
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        
        driver.findElement(By.cssSelector("div[class='recaptcha-checkbox-border']")).click();
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Pass);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        
        String codeURL=driver.getCurrentUrl();
        System.out.println(codeURL);
        
        String partialCode = codeURL.split("code=")[1];
        String code = partialCode.split("&scope")[0];
        
        System.out.println(code);
        
		
		/*String code_related = given().queryParam("scope", "https://www.googleapis.com/auth/userinfo.email")
				.queryParam("auth_url", "https://accounts.google.com/o/oauth2/v2/auth")
				.queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("response_type", "code")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.when().get("https://accounts.google.com/o/oauth2/v2/auth")
				.asString();*/
				
		
		String token_related = given().urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("grant_type","authorization_code")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.when().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(token_related);
		String accessToken = js.getString("access_token");
		
		String response = given()
				.queryParam("access_token", accessToken)
				.when().get("https://rahulshettyacademy.com/getCourse.php")
				.then().extract().response().asString();
		System.out.println(response);
		
		
	}

}
