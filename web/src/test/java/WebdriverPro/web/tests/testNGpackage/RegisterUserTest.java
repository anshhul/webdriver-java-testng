package WebdriverPro.web.tests.testNGpackage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterUserTest {

	@Test(groups= {"smoke", "p1"})
	public void createNewUser() {
		System.out.println("creating new user...");
		Assert.assertTrue(true);
	}

	@Test
	public void registerWithExistingUser() {
		System.out.println("Registering with existing user...");
		Assert.assertTrue(true);
	}

}
