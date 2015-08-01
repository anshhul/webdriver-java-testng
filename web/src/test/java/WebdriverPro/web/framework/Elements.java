package WebdriverPro.web.framework;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Elements {

	public WebDriver driver;
	
	public Elements(WebDriver driver){
		this.driver = driver;
	}
	
	
	
	// -------------Waits--------------
	
	public WebElement waitForPresent(By by){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	
	public void waitForDissappear(By by){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	public void waitForTextToPresent(By by, String text){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text));
	}
	
	
	// ------------Conditions------------
	
	
	public boolean isElementPrresent(By by){
		
		if(getElements(by).size() > 0)
			return true;
		else return false;
	}
	
	public boolean isElementPresent2(By by){
		try {
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			getElement(by);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			return false;
		}
	}
	
	
	
	
	// -------------Actions--------------
	public WebElement getElement(By by){
		return driver.findElement(by);
	}
	
	public List<WebElement> getElements(By by){
		return driver.findElements(by);
	}
	
	public void click(By by){
		getElement(by).click();
	}
	
	public void click(WebElement webElement){
		webElement.click();
	}
	
	public void enter(By by, String value){
		getElement(by).sendKeys(value);
	}
	
	public void hoverOver(WebElement element){
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
	}
	
	
}
