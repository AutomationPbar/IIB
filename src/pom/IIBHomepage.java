package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IIBHomepage {

	
	WebDriver driver;
	
	public static WebElement loginnonlife(WebDriver driver){
		
		WebElement loglife = driver.findElement(By.xpath("//a[@href='Login_2.jsp']"));
		return loglife;
	}
	
	
	
	public static WebElement userid(WebDriver driver){
		
		WebElement userid = driver.findElement(By.xpath("//*[@id='swUsername']"));
		return userid;
	}
	
	public static WebElement password(WebDriver driver){
		
		WebElement pw = driver.findElement(By.id("swPassword"));
		return pw;
	}
	
	public static WebElement captcha(WebDriver driver){
		
		WebElement c = driver.findElement(By.id("jcaptcha_txt"));
		return c;
	}
	
	public static WebElement captchaimage(WebDriver driver){
		
		WebElement ci = driver.findElement(By.xpath("//img[@src='jcaptcha.jpg']"));
		return ci;
	}
	
	
	public static WebElement submit(WebDriver driver){
		
		WebElement s = driver.findElement(By.xpath("//img[@src='Images3/BtnSubmit.jpg']"));
		return s;
	}
	
	
	public static WebElement accept(WebDriver driver){
		
		WebElement a = driver.findElement(By.xpath("//input[@value='Accept']"));
		return a;
	}
}


