package WebdriverPro.web.tests.testNGpackage;

import org.testng.annotations.Test;

public class UserTypeTests {
	
	
	@Test
	public void guestUserTest() {
		System.out.println("testing with GUEST user..");
	}
	
	@Test
	public void freeUserTest() {
		System.out.println("testing with FREE user..");
	}
	
	@Test
	public void premiumUserTest() {
		System.out.println("testing with PREMIUM user..");
	}
	
	@Test(groups= {"sanity", "p1"})
	public void adminUserTest() {
		System.out.println("testing with ADMIN user..");
	}

}
