package WebdriverPro.web.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import WebdriverPro.web.framework.Elements;
import WebdriverPro.web.framework.PropertyManager;

public class HomePage extends Elements {
	
	
	private By loginBy = By.cssSelector("#header-profile a.lh-layerhandler-toggle");
	private By loginButtonBy = By.id("ll-btn");
	private By menuIemsDivBy = By.cssSelector(".lh-layerhandler-menu #nav li.lh-layerhandler"); 
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

    public void SelectMenuOptions(String menuItem, String subItem){
    	List<WebElement> itemsList = getElements(menuIemsDivBy);
    	for (WebElement item : itemsList) {
    		System.out.println(item.getText());
    		if(item.getText().equals(menuItem)){
    			hoverOver(item);
    			List<WebElement> links = item.findElements(By.cssSelector("div.lh-layerhandler-menu li a"));

    			for (WebElement link : links) {
    				System.out.println(link.getText());
    				if(link.getText().contains(subItem)){
    					link.click();
    					break;
    				}
    			}
    			break;
    		}
    	}
    }

    
    public void clickEurope(){
    	click(By.linkText("Europe"));
    }
    
    public void selectEconomy(String option){
    	selectDropdownOption(getElement(By.id("ns_7_CO19VHUC6FF8F0AELSQ6AN3IT2_Cabin")), option);
    }
    

}
