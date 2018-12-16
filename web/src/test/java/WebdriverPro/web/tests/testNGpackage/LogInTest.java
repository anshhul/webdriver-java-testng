package WebdriverPro.web.tests.testNGpackage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LogInTest {

	@Test(groups= {"p1","smoke", "sanity"})
	public void testValidLogin() {
		Assert.assertTrue(true);
	}

	@Test(groups= {"p2", "sanity"})
	public void testInvalidLogin() {
		Assert.assertTrue(true);
	}

	@Test
	public void testSessionTime() {
		Assert.assertTrue(true);
	}
	
	@Test(groups= {"regression", "p2"})
	public void testNewUserLogin() {
		Assert.assertTrue(true);
	}
	
	@Test(groups= {"regression"})
	public void testExistingUser() {
		Assert.assertTrue(true);
	}

	@Test(groups= {"p3", "regression"})
	public void testForgotPassword() {
		Assert.assertTrue(true);
	}
	
	@Test
	public void testLockedAccountLogin() {
		Assert.assertTrue(true);
	}
}
