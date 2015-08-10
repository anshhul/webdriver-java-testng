package WebdriverPro.web.framework.utils;

import WebdriverPro.web.framework.PropertyManager;

public class JavaUtils {
	
	
	public static String getEnvironment(){
		return PropertyManager.getProperty("env");
	}
	
	public enum Days{
		sunday, monday, tuesday, wednesday, thursday, friday, saturday
	}
	
	public static void chooseDay(Days day){
		
		System.out.println("You've choosen: "+day);
	}
	
	public static void main(String[] args) {
		
		for (Days d : Days.values()) {
			System.out.println(d);
		}
	
		JavaUtils.chooseDay(Days.sunday);
		JavaUtils.chooseDay(Days.friday);
		
	}
	

}
