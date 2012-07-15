package com.force.testing_as_a_service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * 
 * @author gwester
 */
public class SalesforceLoginTest {
	private static WebDriver driver;
	
	@BeforeClass
	public static void setUpClass() {
		driver = new FirefoxDriver();
	}
	
	@Before
	public void setUp() {
		driver.get("https://login.salesforce.com");
	}
	
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}
	
	@AfterClass
	public static void tearDownClass() {
		driver.quit();
		driver = null;
	}
	
	@Test
	public void testLogin() {
		final String loginPageTitle = "salesforce.com - Customer Secure Login Page";
		LoginPage page = new LoginPage(driver);
		assertEquals("Login page did not load", loginPageTitle, driver.getTitle());
		page.executeLogin(System.getenv("test_username"), System.getenv("test_password"));
		assertFalse("Login failed", driver.getTitle().equals(loginPageTitle));
	}
}
