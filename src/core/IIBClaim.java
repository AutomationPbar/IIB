package core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

import pom.IIBHomepage;
import pom.Motorsearch;
import utilities.DBManager;
import utilities.GetCaptcha;

public class IIBClaim {

	WebDriver driver;
	WebDriverWait wait;

	String baseurl = "https://nonlife.iib.gov.in/IIB/";
	String UserId = "24358";
	String Password = "Admin@123";
	String captcha = "";

	String regnum = "DL10CH1582";
	String ClaimIdno ="5678";
	

	String state;
	String district;
	String alpha;
	String number;
	String downloadLocation = "C:\\claimreport";
	String basePath = "C:\\eclipse\\IIBClaim\\";

	String apiURL = "https://vision.googleapis.com/v1/images:annotate?key=AIzaSyB2HrJQrXM4ZZjCGpDZYYMWFrPaAC92iJk";
	
	String LiveDB_Path = "jdbc:sqlserver://PBAGL01.ETECHACES.COM:1433;DatabaseName=ProductDB";
	private String Liveusename = "BackofficeSys";
	private String Livepassword = "MT#123#C@re";
	
	String ReplDB_Path = "jdbc:sqlserver://10.0.10.43:1433;DatabaseName=ProductDB";
	private String Replusename = "UATUser";
	private String Replpassword = "UAT123User";
	
	String tableName = "customer.ClaimDocPoints";
	
	DBManager dbm = new DBManager();

	MongoClient mongoClient = null;
	
	DB db = null;

	
	DBCollection collection = null;


	@BeforeTest
	public void setup() throws SQLException {
		
		try {
			MongoClientOptions options = MongoClientOptions.builder().readPreference(ReadPreference.secondaryPreferred()).requiredReplicaSetName("rs3")
					.connectionsPerHost(150).connectTimeout(6000).socketTimeout(8000).build();
			String password = "cOM3KmO8e5K4d6u8"; 
			MongoCredential credential = MongoCredential.createScramSha1Credential("InsurerAutoDBUser", "InsurerAutomation", password.toCharArray());
			
			List<ServerAddress> serverList = Arrays.asList(new ServerAddress("10.80.30.186", 27017), new ServerAddress("10.80.40.253", 27017), new ServerAddress("10.80.30.187",27017));
				mongoClient = new MongoClient(serverList,  Arrays.asList(credential), options);
				//mongoClient = new MongoClient(Arrays.asList(new ServerAddress("10.80.30.186", 27017), new ServerAddress("10.80.40.253", 27017), new ServerAddress("10.80.30.187",27017)), options);
				db = mongoClient.getDB("InsurerAutomation");
			collection = db.getCollection("DocUrl");
			System.out.println("Mongo connected");
		} catch (Exception e) {
			System.err.println("caught Exception while getting connection to mongoDB, msg:" + e.getMessage());
		}

		System.setProperty("webdriver.chrome.driver", "C://eclipse//chromedriver.exe");

		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("download.default_directory", downloadLocation);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		prefs.put("download.prompt_for_download", false);

		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 5);
		try{
		//dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);
				dbm.DBConnection(ReplDB_Path, Replusename, Replpassword);
		}catch(SQLException e){
			e.printStackTrace();
		}

	}

	@Test
	public void loginIIB() throws URISyntaxException {
try{
		driver.get(baseurl);
		driver.manage().window().maximize();

		state = regnum.substring(0, 2);
		System.out.println(state);

		district = regnum.substring(2, 4);
		System.out.println(district);

		alpha = regnum.substring(4, regnum.length() - 4);
		System.out.println(alpha);

		number = regnum.substring(regnum.length() - 4);
		System.out.println(number);

		String invalidRegNo = "";

		try {

			loginfunction();

			Thread.sleep(2000);
			Alert alert = null;

			try {

				alert = driver.switchTo().alert();

			} catch (Exception e) {

			}

			try {
				if (!alert.equals(null)) {

					// System.out.println("Alert Messgae : " + alert.getText());

					alert.accept();

					// Thread.sleep(3000);
					driver.get(baseurl);
					Thread.sleep(3000);
					loginfunction();
				}
			} catch (Exception e) {

			}

			Alert alert1 = null;

			try {

				alert1 = driver.switchTo().alert();

			} catch (Exception e) {

			}

			try {
				if (!alert1.equals(null)) {

					System.out.println("Alert Messgae : " + alert1.getText());

					alert1.accept();

					// Thread.sleep(3000);
					driver.get(baseurl);

					loginfunction();
				}
			} catch (Exception e) {

			}

			wait.until(ExpectedConditions.elementToBeClickable(IIBHomepage.accept(driver))).click();
			wait.until(ExpectedConditions.elementToBeClickable(Motorsearch.msearch(driver))).click();
			wait.until(ExpectedConditions.elementToBeClickable(Motorsearch.vchvs(driver))).click();

			WebElement f = driver.findElement(By.xpath("//iframe[contains(@id,'fid')]"));

			driver.switchTo().frame(f);

			Thread.sleep(1500);

			wait.until(ExpectedConditions.visibilityOf(Motorsearch.reg1(driver))).sendKeys(state);
			wait.until(ExpectedConditions.visibilityOf(Motorsearch.reg2(driver))).sendKeys(district);
			wait.until(ExpectedConditions.visibilityOf(Motorsearch.reg3(driver))).sendKeys(alpha);
			wait.until(ExpectedConditions.visibilityOf(Motorsearch.reg4(driver))).sendKeys(number);

			wait.until(ExpectedConditions.elementToBeClickable(Motorsearch.viewreport(driver))).click();

			Thread.sleep(3000);

			Alert alert2 = null;

			try {

				alert2 = driver.switchTo().alert();

				invalidRegNo = alert2.getText();

				invalidRegNo = invalidRegNo.toLowerCase();

				alert2.accept();

				try {

					if (invalidRegNo.contains("enter valid registration")) {

						teardown();

					}

				} catch (Exception e) {

				}

			} catch (Exception e) {

			}

			try {

				WebElement f1 = driver.findElement(By.xpath("//iframe[contains(@id,'myframe')]"));
				Point flocation = f1.getLocation();
				int fx = flocation.x;
				int fy = flocation.y;
				System.out.println("Frame coordinates " + " X :" + flocation.x + " Y :" + flocation.y);

				driver.switchTo().frame(f1);
				JavascriptExecutor je = (JavascriptExecutor) driver;

				WebElement rt = driver.findElement(By.xpath("//table[@id='table0']"));
				
				je.executeScript("arguments[0].scrollIntoView(true);",rt);
				
				File reporta = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				
				FileUtils.copyFile(reporta, new File("c:\\claimreport\\screenshot1.png"));

				/*File report = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				

				try {

					Thread.sleep(1500);

					BufferedImage rp = null;
					try {
						rp = ImageIO.read(report);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Point point = rt.getLocation();

					int height = rt.getSize().getHeight();
					int width = rt.getSize().getWidth();

					System.out.println("height:" + height);
					System.out.println("width :" + width);
					System.out.println("PointX:" + point.getX());
					System.out.println("PointY:" + point.getY());
					int tabx = 0;
					int taby = 0;

					tabx = point.getX() + 120 + fx;
					taby = point.getY() + 160 + fy;
					
					

					System.out.println("tabPointX:" + tabx);
					System.out.println("tabPointY:" + taby);

					//BufferedImage reportscreen = rp.getSubimage(tabx, fy, width + 30, 420);
					BufferedImage reportscreen = rp.getSubimage(fx, fy, width + 30, 420);


					ImageIO.write(reportscreen, "png", report);

					FileUtils.copyFile(report, new File("c:\\claimreport\\screenshot1.png"));

				} catch (Exception e) {

					e.printStackTrace();
				}*/

			} catch (Exception e) {

				e.printStackTrace();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		String policyScheduleDocURL = "";
		String pdfNamePath = "c:\\claimreport\\screenshot1.png";
		try {

			policyScheduleDocURL = GetFileUrl("PolicyDocument", pdfNamePath, number, "Image");

			FileUtils.deleteQuietly(new File(pdfNamePath));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (invalidRegNo.contains("enter valid registration")) {

				policyScheduleDocURL = null;
			}
			
			if(!policyScheduleDocURL.isEmpty() && !policyScheduleDocURL.equals(null)) {
				
			
			System.out.println(policyScheduleDocURL);
			JSONObject AppData = new JSONObject();
			AppData.put("RegistrationNo", regnum);
			AppData.put("DocUrl", policyScheduleDocURL);
			AppData.put("CreatedOn", java.time.LocalDateTime.now());
			AppData.put("Source", "IIB");
			AppData.put("PortalUrl", baseurl);
			
			LocalDateTime createdon = java.time.LocalDateTime.now();
			 String date = createdon.toString();

			DBObject dbObject = (DBObject) JSON.parse(AppData.toString());
			collection.insert(dbObject);
			
			//dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);
			dbm.DBConnection(ReplDB_Path, Replusename, Replpassword);
			dbm.SetData(ClaimIdno, "56" , "0", "1", regnum, policyScheduleDocURL, date,"124", tableName);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

} catch (Exception e) {

}	

	}

	@AfterTest
	public void teardown() {

		try {
			// Thread.sleep(5000);
			driver.switchTo().defaultContent();

			wait.until(ExpectedConditions.elementToBeClickable(Motorsearch.logout(driver))).click();
			Thread.sleep(2000);
			driver.close();
			driver.quit();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static File lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice;
	}

	public String GetFileUrl(String FileName, String pfilePath, String appNo, String type)
			throws IOException, URISyntaxException {
		String content = "";
		JSONObject docObj = new JSONObject();
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			StringBuilder payLoad = new StringBuilder("{").append("\"ApplicationNo\":\"").append(appNo).append("\"}");
			String encoSt = URLEncoder.encode(payLoad.toString(), "UTF-8");
			String url = "http://api.policybazaar.com/cs/repo/uploadInsurerPortalDoc?payloadJSON=" + encoSt;
			HttpPost post = new HttpPost(url);

			File file = new File(pfilePath);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			if (type.equalsIgnoreCase("Image")) {
				builder.addBinaryBody("file", file, ContentType.create("image/jpg"), pfilePath);
			} else {
				builder.addBinaryBody("file", file, ContentType.create("application/pdf"), pfilePath);
			}
			HttpEntity entity = builder.build();
			post.setEntity(entity);

			HttpResponse response = client.execute(post);
			content = EntityUtils.toString(response.getEntity());
			docObj = new JSONObject(content);
			System.out.println("output URL:" + content.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return docObj.getString("docUrl");
	}

	public void loginfunction() throws Exception {
		try {

			// Thread.sleep(3000);

			/*WebElement fr1 = driver.findElement(By.xpath(".//iframe[contains(@name,'contentFrame')]"));
			Point flocation1 = fr1.getLocation();
			int fx1 = flocation1.x;
			int fy1 = flocation1.y;
			System.out.println("Frame coordinates " + " X :" + flocation1.x + " Y :" + flocation1.y);

			wait.until(ExpectedConditions.visibilityOf(IIBHomepage.loginnonlife(driver))).click();
			Thread.sleep(3000);

			driver.switchTo().frame("contentFrame");*/
			wait.until(ExpectedConditions.visibilityOf(IIBHomepage.userid(driver))).sendKeys(UserId);
			wait.until(ExpectedConditions.visibilityOf(IIBHomepage.password(driver))).sendKeys(Password);

			WebElement c = IIBHomepage.captchaimage(driver);

			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// Get the location of element on the page

			String capText = GetCaptcha.captchatext(c, driver, screenshot, 0, 0, 0, 0, basePath);

			// Thread.sleep(1500);

			wait.until(ExpectedConditions.visibilityOf(IIBHomepage.captcha(driver))).sendKeys(capText);

			Thread.sleep(1500);

			wait.until(ExpectedConditions.elementToBeClickable(IIBHomepage.submit(driver))).click();

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

}
