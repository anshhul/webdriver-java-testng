package com.prosper.framework;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.base.Function;

public abstract class BasePage implements WebdriverWaitTime {
	private final TreeSet<String> browserHandles = new TreeSet<String>();
	static Logger logger = Logger.getLogger(BasePage.class);

	public WebDriver driver;
	public  String  currentWindow;
	public  String currWindow;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		waitForPageToLoad();
		verifyPageElements();
	}

	public abstract void waitForPageToLoad();

	public abstract void verifyPageElements();

	/**
	 * Verify and click on element
	 * 
	 * @param
	 */

	public void verifyAndClick(WebElement element) {
		logger.info("Verify and Click on element: " + element);
		Assert.assertNotNull(element);
		Assert.assertTrue(element.isEnabled());
		element.click();
	}

	public void navigateToDMGoWithProsper() {
		driver.navigate().to(driver.getCurrentUrl().concat(PropertyManager.getProperty("DMGoWithProsper")));
	}

	/**
	 * Verify and click on element
	 * 
	 * @param
	 */
	public void verifyAttribute(WebElement element, String attrKey,
			String expectedValue) {
		Assert.assertNotNull(element);
		Assert.assertEquals(element.getAttribute(attrKey), expectedValue);
	}

	/**
	 * check if element is present or not . If present returns true otherwise
	 * false
	 * 
	 * @return
	 */
	public boolean isElementVisible(WebElement element) {
		assertNotNull(element);
		return element.isDisplayed();
	}




	/**
	 * check if element is present or not . If present returns true otherwise
	 * false
	 * 
	 * @return
	 */
	public void verifyElementVisible(WebElement element) {
		assertNotNull(element);
		assertTrue(element.isDisplayed());
	}

	public void fluentwait( final By locator){
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>( driver )
				.withTimeout(90, TimeUnit.SECONDS)
				.pollingEvery(250, TimeUnit.MILLISECONDS)
				.ignoring( NoSuchElementException.class, StaleElementReferenceException.class);
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
	}
	/**
	 * To Type into text box . takes two parameter
	 * 
	 * @param email
	 *            :- Value to be typed
	 */

	public void type(WebElement element, String email) {
		waitForElementToBeVisible(element);
		assertNotNull(element);
		element.sendKeys(email + "\t");
	}
	public void clearThenType(WebElement element, String value) {
		assertNotNull(element);
		element.clear();
		element.sendKeys(value + "\t");
	}
	/**
	 * Attempts to click on an element multiple times (to avoid stale element
	 * exceptions caused by rapid DOM refreshes)
	 * 
	 * @param by
	 *            By element locator

	 * @param elementLocators
	 */
	public void dependableClick(By elementLocators) {
		final int MAXIMUM_WAIT_TIME = 10;
		final int MAX_STALE_ELEMENT_RETRIES = 5;
		int retries = 0;
		while (true) {
			try {
				new WebDriverWait(driver, WEBDRIVER_WAIT_TIME)
				.until(ExpectedConditions.elementToBeClickable(elementLocators))
				.sendKeys(Keys.ENTER);
				return;
			} catch (StaleElementReferenceException e) {
				if (retries < MAX_STALE_ELEMENT_RETRIES) {
					retries++;
					continue;
				} else {
					throw e;
				}
			}
		}
	}

	/**
	 * @param element
	 * @return
	 */
	public List<String> getDropDownOptions(WebElement element) {
		List<String> optionList = new ArrayList<String>();
		Select select = new Select(element);
		for (WebElement option : select.getOptions()) {
			optionList.add(option.getText());
		}
		return optionList;
	}

	public void selectOption(List<WebElement> elementList, String itemToSelect) {
		logger.info("Select item : " + itemToSelect + ": " + elementList);
		for (WebElement item : elementList) {
			if (getText(item).contains(itemToSelect)) {
				verifyAndClick(item);
				break;
			}
		}
	}

	/**
	 * To select a value from drop down box
	 * 
	 * @param value
	 *            :value to be selected
	 */
	public void selectDropDownByVisibleText(WebElement element, String value) {
		assertNotNull(element);
		Select select = new Select(element);
		select.selectByVisibleText(value);
	}


	public String getSelectedvalueByVisibleText(WebElement element){
		String selectedvalue = null;
		Select select = new Select(element);
		selectedvalue =select.getFirstSelectedOption().getText();
		return selectedvalue;

	}
	/*
	 * verify selected value in a drop down
	 */
	public void verifyselectDropDownValue(WebElement element, String value) {
		assertNotNull(element);
		Select select = new Select(element);
		select.getFirstSelectedOption().getText().contains(value);
	}

	/**
	 * To select a value from drop down box
	 * 
	 * @param value
	 *            :value to be selected
	 */
	public void selectDropDownByValue(WebElement element, String value) {
		Assert.assertNotNull(element);
		Select select = new Select(element);
		select.selectByValue(value);
	}

	/**
	 * Wait for a element Present
	 * 
	 * @param elmLocator
	 *            Locator of the element
	 * @param timeInSecs
	 *            time in seconds
	 */
	public WebElement waitForElementPresent(By elmLocator, long timeInSecs) {

		WebDriverWait wait = new WebDriverWait(driver, timeInSecs);
		wait.until(ExpectedConditions.presenceOfElementLocated(elmLocator));
		return driver.findElement(elmLocator);
	}

	public void WaitForElementToBeClickable(WebElement element, long timeInSecs) {
		assertNotNull(element);
		WebDriverWait wait = new WebDriverWait(driver, timeInSecs);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void WaitForElementToBeClickable(WebElement element) {
		assertNotNull(element);
		WebDriverWait wait = new WebDriverWait(driver, WEBDRIVER_WAIT_TIME);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForElementToBeVisible(WebElement element) {
		assertNotNull(element);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Wait for a element Present
	 * 
	 * @param elmLocator
	 *            Locator of the element
	 */
	public void waitForElementPresent(By elmLocator) {
		WebDriverWait wait = new WebDriverWait(driver, WEBDRIVER_WAIT_TIME);
		wait.until(ExpectedConditions.presenceOfElementLocated(elmLocator));
	}
	public boolean waitForElementToNotBePresent(final By by, final int timeoutInSeconds) {
		try {
			ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(final WebDriver webDriver) {
					WebElement element = webDriver.findElement(by);
					return !element.isDisplayed();
				}
			};
			WebDriverWait w = new WebDriverWait(driver, timeoutInSeconds);
			w.until(condition);
		} catch (Exception ex) {
			logger.info("Element is still displayed");
		}
		return true;
	}
	/*
	 * without wait time
	 */
	public void waitForElementClickable(By elmLocator) {
		WebDriverWait wait = new WebDriverWait(driver, WEBDRIVER_WAIT_TIME);
		wait.until(ExpectedConditions.elementToBeClickable(elmLocator));
	}

	public void verifyElementSelected(WebElement element) {
		Assert.assertNotNull(element);
		Assert.assertTrue(element.isSelected());
	}

	public void verifyElementContainText(WebElement element, String text) {

		Assert.assertNotNull(element);
		Assert.assertTrue(getText(element).contains(text));
		System.out.println("test");
	}

	/**
	 * Verify Page Title
	 */
	public void verifyPageTitle(String pageTitle) {
		String pTitle = driver.getTitle();
		Assert.assertTrue(pTitle.toLowerCase()
				.contains(pageTitle.toLowerCase()));
	}

	/**
	 * Get Text from HTML
	 */
	public String getText(WebElement element) {
		Assert.assertNotNull(element);
		return element.getText().replaceAll("[^\\x00-\\x7f]", "");
	}

	/**
	 * Get result from Databseand
	 */
	public void verifyDBValue(String databaseName, String query,
			String columnName, Object expectedValue) {

		logger.info("Verify D/B Value");
		DatabaseManager2 dm = new DatabaseManager2();
		ResultSet dbResult;
		try {
			dbResult = dm.DBQuery(query);
			while (dbResult.next()) {
				logger.info(dbResult.getObject(columnName));

				Assert.assertEquals(dbResult.getString(columnName),
						expectedValue);

				logger.info("Databse Query Result: " + dbResult
						+ "\nExpected Result:\t" + expectedValue);
				logger.info("Databse Query Result: " + dbResult
						+ "\nExpected Result:\t" + expectedValue);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				dm.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get result from Database for multiple columns
	 * 
	 * @param query
	 * @param columnName
	 * @return
	 */
	public Object fetchColumnValue(String query, String columnName)  {

		logger.info("Verify D/B Value");
		DatabaseManager2 dm = new DatabaseManager2();
		ResultSet dbResult = null;
		Object value = null;
		try {
			dbResult = dm.DBQuery(query);
			while (dbResult.next()) {
				value = dbResult.getObject(columnName);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if(dm!=null)
					dm.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return value;

	}

	/**
	 * @param query
	 */
	public void executeQuery(String query,String userid,String connectionString) {
		DatabaseManager3 dm = new DatabaseManager3(connectionString);
		dm.executeQuery(query,userid);
	}

	public void excuteprocecure(String dbName,String procedurename, int uid,int ficoScore,String firstName,String lastName,String address,String state,String city,String zipCode,String ssn,String dob)
			throws SQLException {
		DatabaseManager2 dm = new DatabaseManager2();
		dm.callProcedure(dbName, procedurename, uid,ficoScore,firstName,lastName,address,state,city,zipCode,ssn,dob);
	}

	public void resetOfferCode(String offerCode) throws SQLException{
		logger.info("Offer Code: "+offerCode);
		Connection connection = null;
		CallableStatement callableStatement = null;
		if (getCurrentEnvUrl().contains("stg2")) {
			try {
				connection = DriverManager.getConnection(SelectProspectDB());
				callableStatement = connection.prepareCall("{call ..QA_ResetDMProspects(?)}");
				callableStatement.setString(1, offerCode);
				int i = callableStatement.executeUpdate();
				System.out.println(i);
			} catch (SQLException e) {
				logger.warn(e.getMessage());
			}
		} else {
			DatabaseManager3 dm = new DatabaseManager3(SelectProspectDB());
			dm.executeQuery("update prospect set engagementdate=NULL, userid =NULL,modifieddate =null,emailaddress =null where offercode =?", offerCode);
		}
	}

	public void excuteProcecure(String connectionString, String procedurename,String userID)
			throws SQLException {

		DatabaseManager2 dm = new DatabaseManager2();
		dm.invokeProcedure(connectionString, procedurename,userID);
	}
	public ArrayList<HashMap<String, Object>> getQueryResult(String query) {

		logger.info("Getting rows from Database...");

		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();

		DatabaseManager2 dm = new DatabaseManager2();
		ResultSet dbResult = null;

		try {
			dbResult = dm.DBQuery(query);

			ResultSetMetaData meta = null;
			meta = dbResult.getMetaData();

			// get column names
			int colCount = meta.getColumnCount();

			ArrayList<String> cols = new ArrayList<String>();

			for (int index = 1; index <= colCount; index++)
				cols.add(meta.getColumnName(index));

			// fetch rows
			while (dbResult.next()) {
				// try{

				HashMap<String, Object> row = new HashMap<String, Object>();

				for (String colName : cols) {
					Object val = dbResult.getObject(colName);
					if (dbResult.wasNull()) {
						val = "NULL";
					}
					row.put(colName, val);
				}
				rows.add(row);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();

		}
		return rows;

	}

	/**
	 * Get Text from HTML and Validate result from Database
	 */
	public void verifyDBResult(String databaseName, String query,
			String columnName, WebElement element) {
		logger.info("Verify Result with D/B Value");
		DatabaseManager2 dm = new DatabaseManager2();
		try {
			ResultSet dbResult = dm.DBQuery(query);
			while (dbResult.next()) {
				logger.info(dbResult.getString(columnName));
			}
			Assert.assertNotNull(element);
			String textValue = element.getText();
			logger.info("Databse Query Result: " + dbResult
					+ "\nExpected Result:\t" + textValue);
			logger.info("Databse Query Result: " + dbResult
					+ "\nExpected Result:\t" + textValue);
			Assert.assertEquals(dbResult, textValue);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Get count of web element list and valdiate the count
	 * 
	 * @param element
	 * @param text
	 */
	public void verifyElementCount(List<WebElement> element, String text) {
		Assert.assertNotNull(element);
		Assert.assertEquals(element.size(), text);
	}

	public void verifyAssertionTrue(boolean condition, String failureMessage) {
		Assert.assertTrue(condition, failureMessage);
	}

	/**
	 * Asserts that two objects are equal. If they are not, an AssertionError,
	 * with the given message, is thrown.
	 * 
	 * @param actual
	 *            : actual the actual value
	 * @param expected
	 *            : expected the expected value
	 * @param failureMessage
	 *            : message the assertion error message
	 */
	public void verifyAssertionTrue(Object actual, Object expected,
			String failureMessage) {
		Assert.assertEquals(actual, expected, failureMessage);
	}

	/**
	 * Opens given url
	 * 
	 * @param driver
	 *            : driver object
	 * @param url
	 *            : URL you wish to navigate to
	 */
	public void openUrl(WebDriver driver, String url) {
		logger.info("Navigating to url: " + url);
		driver.get(url);;

	}

	/**
	 * verify whether element is present or not
	 * 
	 * @param element
	 *            : web element object
	 * @return true if element is present, false otherwise
	 */
	public boolean isElementPresent(WebElement element, long timeInSecs) {
		try {
			driver.manage().timeouts()
			.implicitlyWait(timeInSecs, TimeUnit.SECONDS);
			element.getTagName();
			driver.manage().timeouts()
			.implicitlyWait(WEBDRIVER_WAIT_TIME, TimeUnit.SECONDS);
			return true;
		} catch (NoSuchElementException e) {
			driver.manage().timeouts()
			.implicitlyWait(WEBDRIVER_WAIT_TIME, TimeUnit.SECONDS);
			return false;
		}
	}

	/**
	 * Clear the values of the Element
	 */

	public void clearField(WebElement element) {
		element.clear();
	}

	/**
	 * Get random number
	 */

	public int getRandomNumber(int minValue, int maxValue) {
		return new Random().nextInt(maxValue - minValue + 10) + minValue;
	}

	/**
	 * Get current date and time in specified format
	 * 
	 * @param format
	 *            desired format for e.g. MM-DD-YYYY
	 * @return Current date in the format specified as parameter
	 */
	public String getCurrentTimeInFormat(String format) {
		return new SimpleDateFormat(format).format(Calendar.getInstance()
				.getTime());
	}

	/**
	 * Get current date and time in specified format and timezone
	 * 
	 * @param format
	 *            Desired format for e.g. MM-DD-YYYY
	 * @param timeZone
	 *            Desired TimeZone e.g America/Los_Angeles
	 * @return Current date in the format specified
	 */
	public String getCurrentTimeInFormat(String format, String timeZone) {
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		return df.format(new Date());
	}

	/**
	 * This function is used to switch to current window when multiple windows
	 * have opened.
	 *
	 * @return
	 */
	public boolean switchToCurrentWindow() {
		currentWindow = driver.getWindowHandle();
		Set<String> availableWindows = driver.getWindowHandles();
		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				if (!windowId.equals(currentWindow)) {
					driver.switchTo().window(windowId);
					driver.manage().window().maximize();
					return true;
				} else {
					continue;
				}
			}
		}
		driver.switchTo().window(currentWindow);
		return true;
	}

	public void switchToNewlyOpenedWindow() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String parentWindow = driver.getWindowHandle();
		browserHandles.toString();
		if (browserHandles.contains(parentWindow) == false) {
			browserHandles.add(parentWindow);
		}
		Set<String> newHandleIdentifierSet = driver.getWindowHandles();
		newHandleIdentifierSet.removeAll(browserHandles);
		String popUpHandle = newHandleIdentifierSet.iterator().next();
		driver.switchTo().window(popUpHandle);
	}

	public void switchToNewlyOpenedWindowUsingTitle(String title) {
		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> it=allWindows.iterator();
		while(it.hasNext()) {
			driver.switchTo().window(it.next());
			if(driver.getTitle().contains(title)){
				break;
			}
		}
	}

	/**
	 * use the method to close after switching on windows
	 */
	public void closeAllButOneBrowser() {
		// make sure we close any multiple windows we have open
		while (driver.getWindowHandles().size() > 1) {
			driver.close();
		}
		// and make sure we end up pointing the webDriver at the open browser
		driver.switchTo().window(driver.getWindowHandles().iterator().next());
	}
	public void closeCurrentWindowAndMoveToPrevious(String title) {
		// make sure we close any multiple windows we have open
		while (driver.getWindowHandles().size() > 1) {
			driver.close();
		}
		// and make sure we end up pointing the webDriver at the open browser
		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> it=allWindows.iterator();
		while(it.hasNext()) {
			driver.switchTo().window(it.next());
			if(driver.getTitle().contains(title)){
				break;
			}
		}
	}

	/**
	 * This function is used to switch back to previous window when multiple
	 * windows have opened.
	 *
	 * @return
	 */
	public boolean switchBackToPreviousWindow() {
		String OpenedWindow = driver.getWindowHandle();
		driver.close();
		driver.switchTo().window(OpenedWindow);
		return true;
	}

	/**
	 * Mouse Hover on an Element
	 */
	public void hoverOver(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	/**
	 * Hover over an element and click
	 * 
	 * @param webElement
	 *            : Object of located element
	 */
	public void hoverClick(WebElement webElement) {
		Actions action = new Actions(driver);
		action.moveToElement(webElement).click().build().perform();
	}

	public void waitForDisappear(final By by) {

		ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					assertNotNull(driver.findElement(by));
					WebDriverWait wait = new WebDriverWait(driver,
							WEBDRIVER_WAIT_TIME);
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(by));
					return false;
				} catch (NoSuchElementException e) {
					return true;

				}

			}

		};
	}



	public void waitForElementDisappear(By by){
		logger.info("Waiting for loading spinner started at: "+getCurrentTimeInFormat("hh:mm:ss"));
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int count = 0;
		while(driver.findElements(by).size()>0){
			count++;
			if(count >= 100){
				WebDriverWait wait=new WebDriverWait(driver, WEBDRIVER_WAIT_TIME);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
				logger.info("Thank Info loading div to is completed");
				break;
			}
		}
		logger.info("Waiting for loading spinner Finished at: "+getCurrentTimeInFormat("hh:mm:ss"));
	}

	public void waitForElementDisappear(By by,String timeout){
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int count = 0;
		while(driver.findElements(by).size()>0){
			count++;
			if(count >= 100){
				logger.info(count);
				driver.manage().timeouts()
				.implicitlyWait(WEBDRIVER_WAIT_TIME, TimeUnit.SECONDS);
				logger.info("Thank Info loading div to is completed");
				break;
			}
		}
	}





	public void selectValueFromDropDown(List<WebElement> webElements,
			String valueToBeSelected) {
		logger.info("Select: " + webElements);
		for (WebElement webelement : webElements) {
			if (getText(webelement).contains(valueToBeSelected)) {
				verifyAndClick(webelement);
				break;
			}
		}
	}

	/**
	 * This 'getDate()' function is used to convert the string into date and add
	 * or subtract specified number of days to particular date.
	 * 
	 * @param date
	 * @param noOfDays
	 * @return
	 */
	public String getDate(String date, int noOfDays) {
		String newDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date date1 = formatter.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(date1);
			c.add(Calendar.DATE, noOfDays); // number of days to add
			newDate = formatter.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newDate;
	}

	/**
	 * This getCurrentUrl() function is used to get current url of the web page.
	 * 
	 * @return
	 */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * This navigateToBack() function is used to navigate to back web page.
	 */
	public void navigateToBack() {
		driver.navigate().back();
	}

	public void navigateToForward() {
		driver.navigate().forward();
	}

	/**
	 * This function is used to verify length of entered text into any web
	 * object is within the range.
	 * 
	 * @param webElement
	 * @param maxLength
	 */
	public void verifyLimitOfEnteredValue(WebElement webElement, int maxLength) {
		Assert.assertTrue(getText(webElement).length() <= maxLength,
				"Length of entered text is not in range.");
	}

	/*
	 * This function verify that Element is not Selected
	 */

	public void verifyElementNotSelected(WebElement element) {
		Assert.assertNotNull(element);
		Assert.assertFalse(element.isSelected());
	}

	/**
	 * This function is used to clear all cookies.
	 */
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	// To generate Random string, charac, Alphanumers
	public static enum Mode {
		ALPHA, ALPHANUMERIC, NUMERIC
	}

	public static String generateRandomString(int length, Mode mode)
			throws Exception {

		StringBuffer buffer = new StringBuffer();
		String characters = "";

		switch (mode) {

		case ALPHA:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;

		case ALPHANUMERIC:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;

		case NUMERIC:
			characters = "1234567890";
			break;
		}

		int charactersLength = characters.length();

		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString();
	}

	// Http resonp code - 200 OK
	public boolean checkResponse(String url) {
		try {
			int resp_code = Request.Get(url).execute().returnResponse()
					.getStatusLine().getStatusCode();
			logger.info("Respose code for URL " + url + " is -> "
					+ resp_code);
			if (resp_code == 200)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}






	public String getAttribute(WebElement element, String attrKey) {
		return element.getAttribute(attrKey);
	}

	public String replaceAll(String text) {
		return text.replaceAll("[^\\d\\.]", "");
	}


	//****************************


	public ArrayList<HashMap<String, Object>> getQueryResult1(String query, String connectionString) {

		logger.info("Getting rows from Database...");
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();

		DatabaseManager3 dm = new DatabaseManager3(connectionString);

		ResultSet dbResult = null;

		try {
			/*if(dbName=="uatProspectDBUrl")
			{
				String query1="select top 5  OfferCode from prospect..prospect where campaignprogramid='4DD2BC34-05B7-4E40-9944-1F0DDA05994D'";
				dbResult = dm.DBQuery(query1);
			}else{*/
			dbResult = dm.DBQuery(query);
			//}
			ResultSetMetaData meta = null;
			meta = dbResult.getMetaData();

			// get column names
			int colCount = meta.getColumnCount();

			ArrayList<String> cols = new ArrayList<String>();

			for (int index = 1; index <= colCount; index++)
				cols.add(meta.getColumnName(index));

			// fetch rows
			while (dbResult.next()) {
				// try{

				HashMap<String, Object> row = new HashMap<String, Object>();

				for (String colName : cols) {
					Object val = dbResult.getObject(colName);
					if (dbResult.wasNull()) {
						val = "NULL";
					}
					row.put(colName, val);
				}
				rows.add(row);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return rows;

	}


	public void reloadPage(){
		driver.navigate().refresh();
		waitForPageToLoadCompletely();
	}
	public String SelectCircleoneDB() {

		String	envURI = PropertyManager.getProperty("envURI");

		if(envURI==null){
			String envName  = PropertyManager.getProperty("envName");

			if (envName.equalsIgnoreCase("qa34")) {
				return PropertyManager.getProperty("q34DB01Url");
				
			} else if (envName.equalsIgnoreCase("stage2")) {
				return PropertyManager.getProperty("stage2CircleoneDBUrl");
				
			} else if (envName.equalsIgnoreCase("stage")) {
				return PropertyManager.getProperty("circleoneDBUrl");

			}else if(envName.equalsIgnoreCase("qa32")) {
				return PropertyManager.getProperty("q32DB01Url");

			} else if(envName.equalsIgnoreCase("uat")) {
				return PropertyManager.getProperty("uatCircleoneDBUrl");

			} else if(envName.equalsIgnoreCase("qa20")){
				return PropertyManager.getProperty("qa20db1CircleoneUrl");
			}
			else {
				return null;
			}
		}else {
			return PropertyManager.getProperty("circleoneCS");
		}
	}

	public String SelectProspectDB() {

		String	envURI = PropertyManager.getProperty("envURI");

		if(envURI==null){
			String	env = PropertyManager.getProperty("envName");

			if (env.equalsIgnoreCase("stage")) {
				return PropertyManager.getProperty("STGDBProspectUrl");

			} else if (env.equalsIgnoreCase("stage2")) {
				return PropertyManager.getProperty("stage2dbProspectUrl");

			} else if (env.equalsIgnoreCase("qa32")) {
				return PropertyManager.getProperty("prospectDBUrl");

			} else if(env.equalsIgnoreCase("uat")) {
				return PropertyManager.getProperty("uatProspectDBUrl");

			} else if(env.equalsIgnoreCase("qa34")) {
				return PropertyManager.getProperty("qa34ProspectDBUrl");
				
			}else {
				return null;
			}
		}else {
			logger.info(PropertyManager.getProperty("prospectCS"));
			return PropertyManager.getProperty("prospectCS");
		}
	}

	public String SelectSpectrumDB() {

		String	envURI = PropertyManager.getProperty("envURI");

		if(envURI==null){
			String	env = PropertyManager.getProperty("envName");

			if (env.equalsIgnoreCase("stage")) {
				return PropertyManager.getProperty("STGDBProspectUrl");

			} else if (env.equalsIgnoreCase("qa32")) {
				return PropertyManager.getProperty("prospectDBUrl");

			} else if(env.equalsIgnoreCase("uat")) {
				return PropertyManager.getProperty("uatProspectDBUrl");

			} else if(env.equalsIgnoreCase("qa34")) {
				return PropertyManager.getProperty("qa34ProspectDBUrl");
			} else if(env.equalsIgnoreCase("qa20")) {
				return PropertyManager.getProperty("qa20SpectrumDBUrl");
			}
			else {
				return null;
			}
		}else {
			logger.info(PropertyManager.getProperty("prospectCS"));
			return PropertyManager.getProperty("prospectCS");
		}
	}


	public void waitForJQuery(WebDriver driver) {
		(new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				JavascriptExecutor js = (JavascriptExecutor) d;
				return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
			}
		});
	}

	public void waitForPageToLoadCompletely() {
		WebDriverWait wait = new WebDriverWait(driver,20);
		ExpectedCondition<Boolean> expectation = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		try {
			wait.until(expectation);
			//			waitForJQuery(driver);
			Thread.sleep(1000);
			((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
		} catch(Throwable error) {
			((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			Assert.assertFalse(true, "Timeout waiting for Page Load Request to complete.");
		}
	}



	public void javaScriptClick(WebElement element){
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("$('#continue').click();");
		//executor.executeScript("document.getElementById('continue').click();");
	}

	public void waitForThankYouSpinnerToDissappear(){
		waitForElementDisappear(By.cssSelector(".spinnerContent"));
		waitForPageToLoadCompletely();
	}


	public Cookie getCookie(String cookieName){
		return driver.manage().getCookieNamed(cookieName);
	}

	public String getCookieValue(String cookieName){
		String value = null;
		try {
			value = URLDecoder.decode(getCookie(cookieName).getValue(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	public void verifyPageSourceContains(String expectedText){
		Assert.assertTrue(driver.getPageSource().contains(expectedText));
	}

	public String  getCurrentEnvUrl() {
		String envName= PropertyManager.getProperty("envName");
		String envURI= PropertyManager.getProperty("envURI");

		if(envURI==null){
		
		if (envName.equalsIgnoreCase("stage"))
			return PropertyManager.getProperty("stgEnvURI");
				
		else if (envName.equalsIgnoreCase("qa34")) 
			return PropertyManager.getProperty("qa34EnvURI");

		else if(envName.equalsIgnoreCase("qa32")) 
			return PropertyManager.getProperty("qaEnvURI");

		else if (envName.equalsIgnoreCase("uat"))
			return PropertyManager.getProperty("uatEnvURI");
		
		else if (envName.equalsIgnoreCase("stage2")) 
			return PropertyManager.getProperty("stg2EnvURI");
		
		else return null;
		} else {
			return envURI;
		}

	}
}



