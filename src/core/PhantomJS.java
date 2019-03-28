package core;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
 
 
public class PhantomJS 
 
{
        static WebDriver driver;
    String phantom_path;
     
@Test
 
public void RunPhantom() throws InterruptedException
 {
	DesiredCapabilities caps = new DesiredCapabilities();
	caps.setJavascriptEnabled(true);                
	caps.setCapability("takesScreenshot", true);  
	caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"C:\\Users\\Avani\\Downloads\\phantomjs-2.1.1-windows\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
	WebDriver driver = new  PhantomJSDriver(caps);
    // phantom_path= System.getProperty("user.dir")+ "/phantomjs";
     
   //Set the path of the phantomjs in the properties
    //System.setProperty("phantomjs.binary.path", phantom_path);
     
   //Initialize PhantomJS Driver
   // driver= new PhantomJSDriver();
     
    driver.manage().window().maximize();
     
  //Open desired link
   driver.navigate().to("https://spectrum.maxbupa.com/Spectrum/Login.aspx");
     
 //Get & Print the title of navigated page
  System.out.println("Title of the page is ----> "+driver.getTitle());
  
     
 }
 
@AfterTest
 
public void closebrowser()
 {
    System.out.println("Thank You! for reading the blog");
   // driver.quit();
 }
}