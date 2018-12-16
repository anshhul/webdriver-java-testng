package WebdriverPro.web.framework;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.xml.stream.events.EndDocument;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Testing {

	WebDriver driver;

	String chromeDriver = "/src/test/resources/chromedriver.exe";

	@BeforeMethod
	public void start() {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + chromeDriver);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	
	@AfterMethod(dependsOnMethods="screenshot", alwaysRun= true)
	public void end() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	@Test
	public void testMethodOne() {
		System.out.println("test method one starts::::::");
	}

	@AfterMethod
	public void screenshot(ITestResult result) throws IOException {
		File file = null;

		System.out.println("instance name:" + result.getInstanceName());
		System.out.println("Get Name: " + result.getName());
System.out.println(result.isSuccess());
		String time = new SimpleDateFormat("ddMMMyyy_hhmmaaa").format(Calendar.getInstance().getTime());
		
		
		if (!result.isSuccess()) {

			file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file,
				new File("C:\\\\screenshots\\\\" + result.getInstanceName() + "_" + result.getName() + "_" + time + ".png"));

		}
	}

//	public static void main(String[] args) {
//		System.out.println(System.getProperty("user.dir"));
//	}

}
