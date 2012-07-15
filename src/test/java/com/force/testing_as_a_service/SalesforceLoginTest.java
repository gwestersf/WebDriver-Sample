package com.force.testing_as_a_service;

import junit.framework.TestCase;

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
public class SalesforceLoginTest extends TestCase {
	private static WebDriver driver;
	
	@BeforeClass
	public static void setUpClass() {
		driver = new FirefoxDriver();
	}
	
	@Before
	@Override
	public void setUp() {
		driver.get("https://login.salesforce.com/");
	}
	
	@After
	@Override
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
		LoginPage page = new LoginPage(driver);
		assertEquals("Login page did not load", "Customer Secure Login Page", driver.getTitle());
		page.executeLogin(System.getenv("test_username"), System.getenv("test_password"));
		assertFalse("Login failed", driver.getTitle().equals("Customer Secure Login Page"));
	}
}
