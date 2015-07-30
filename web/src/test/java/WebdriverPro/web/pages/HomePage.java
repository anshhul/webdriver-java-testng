package WebdriverPro.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import WebdriverPro.web.framework.Elements;
import WebdriverPro.web.framework.PropertyManager;

public class HomePage extends Elements {
	
	
	private By loginBy = By.cssSelector("#header-profile a.lh-layerhandler-toggle");
	
	    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage navigateToHomePage() {
        driver.navigate().to(PropertyManager.getProperty("homePageURL"));
        return this;
    }
    
    public void clickLogin(){
    	click(loginBy);
    }

}
