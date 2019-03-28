package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Motorsearch {
	

	WebDriver driver;
	public static WebElement msearch(WebDriver driver){
		
		WebElement m = driver.findElement(By.xpath(".//*[@class='down' and contains(text(),'MOTOR_SEARCH')]"));
		return m;
	}

	public static WebElement vchvs(WebDriver driver){
		
		WebElement m = driver.findElement(By.xpath(".//a[text()='VCHVS']"));
		return m;
	}
	
	
	public static WebElement reg1(WebDriver driver){
		
		WebElement m = driver.findElement(By.xpath("//*[@id='strRegNo1']"));
		return m;
	}
	
	public static WebElement reg2(WebDriver driver){
		
		WebElement m = driver.findElement(By.id("strRegNo2"));
		return m;
	}


	public static WebElement reg3(WebDriver driver){
		
		WebElement m = driver.findElement(By.id("strRegNo3"));
		return m;
	}
	
	public static WebElement reg4(WebDriver driver){
		
		WebElement m = driver.findElement(By.id("strRegNo4"));
		return m;
	}
	
	
	public static WebElement viewreport(WebDriver driver){
		
		WebElement m = driver.findElement(By.xpath("//input[@value='View Report']"));
		return m;
	}
	
	
	public static WebElement logout(WebDriver driver){
		
		WebElement l = driver.findElement(By.xpath("//a[@href='./sideMenuAction.do?method=doLogOut&loggedInUserId=24358']"));
		return l;
	}
	
}
