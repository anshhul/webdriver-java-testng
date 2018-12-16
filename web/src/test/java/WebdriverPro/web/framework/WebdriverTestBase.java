package WebdriverPro.web.framework;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * 
 * Base test class which starts and ends the webdriver instance.
 * Note: Each test script class must extend this class.
 * 
 * @author anshul
 */
public abstract class WebdriverTestBase {

	Logger logger = Logger.getLogger(WebdriverTestBase.class);

	protected WebDriver driver;

	@BeforeMethod(alwaysRun = true)
	public void launchBrowser(Method method) throws MalformedURLException {
		driver = WebdriverManager.getDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		logger.info("Started test method: " + method.getName());
	}

	@AfterMethod(dependsOnMethods = "takeScreenshot", alwaysRun = true)
	public void quitBrowser() {
		if (driver != null)
			driver.quit();

	}

	/**
	 * Take screenshot only when any test method is failed
	 * @param result
	 * @throws IOException
	 */
	@AfterMethod
	public void takeScreenshot(ITestResult result) throws IOException {
		String testMethodName = result.getName();
		String testClass = result.getInstanceName();
		logger.info("***** End of test :" + testClass + testMethodName);

		if (!result.isSuccess()) {
			TakesScreenshot screenshot = ((TakesScreenshot) driver);
			File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

			String time = new SimpleDateFormat("ddMMMyyy_hhmmaaa").format(Calendar.getInstance().getTime());
			// WebdriverPro.web.tests.LoginTest_verifyHomePage02Sep2018_0349AM.png
			FileUtils.copyFile(srcFile,
					new File("C:\\screenshots\\" + testClass + "_" + testMethodName + "_" + time + ".png"));
		}

	}

	// ------------Assertions----------------

	public void verify(Object actual, Object expected) {
		Assert.assertEquals(actual, expected, "Objects not matched");
	}

	public void verifyTrue(boolean condition) {
		Assert.assertTrue(condition, "Condition is not true");
	}

}
