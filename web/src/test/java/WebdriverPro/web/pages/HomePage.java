package WebdriverPro.web.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

import WebdriverPro.web.framework.Elements;
import WebdriverPro.web.framework.PropertyManager;

public class HomePage extends Elements {
	
	
	private By loginBy = By.cssSelector("#header-profile a.lh-layerhandler-toggle");
	private By loginButtonBy = By.id("ll-btn");
	
	private By menuItemsBy = By.cssSelector(".lh-layerhandler-menu #nav li.lh-layerhandler>a");
	
	    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage navigateToHomePage() {
        driver.navigate().to(PropertyManager.getProperty("homePageURL"));
        return this;
    }
    
    public void clickLogin(){
    	click(waitForPresent(loginBy));
    }
    
    public boolean isLoginButtonPresent(){
    	return isElementPrresent(loginBy);
    }
    
    public void hoverOverMenu(String menuItem){
    	List<WebElement> itemsList = getElements(menuItemsBy);
    	for (WebElement item : itemsList) {
			if(item.getText().equals(menuItem)){
				hoverOver(item);
				break;
			}
		}
    }
    
    

}
