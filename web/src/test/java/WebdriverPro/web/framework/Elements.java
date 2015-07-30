package WebdriverPro.web.framework;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Elements {

	public WebDriver driver;
	
	public Elements(WebDriver driver){
		this.driver = driver;
	}
	
	public WebElement getElement(By by){
		return driver.findElement(by);
	}
	
	public List<WebElement> getElements(By by){
		return driver.findElements(by);
	}
	
	public void click(By by){
		getElement(by).click();
	}
	
	public void enter(By by, String value){
		getElement(by).sendKeys(value);
	}
	
}
