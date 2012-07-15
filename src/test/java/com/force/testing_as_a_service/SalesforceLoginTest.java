package com.force.testing_as_a_service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * 
 * @author gwester
 */
@RunWith(Parameterized.class)
public class SalesforceLoginTest {
	private final WebDriver driver;
	
	private static String username;
	private static String password;
	
	@Rule public final TestRule rule;
	
	public SalesforceLoginTest(WebDriver anyDriver) {
		this.driver = anyDriver;
		
		// we want to override the failure message to include the driver that failed
		this.rule = new TestWatcher() {
	        @Override
	        protected void failed(final Throwable e, final org.junit.runner.Description description) {
	            final AssertionError x = new AssertionError(
	                    driver.getClass().getSimpleName() + "." + description.getDisplayName() + ": " + e.getMessage());
	            x.setStackTrace(e.getStackTrace());
	            throw x;
	        }
	    };
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		// need to do this before "new ChromeDriver"
		if(!System.getProperties().contains("webdriver.chrome.driver")) {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
		}
		
		Object[][] data = new Object[][] { { new HtmlUnitDriver(true) }, 
				{ new FirefoxDriver() }, 
				{ new ChromeDriver() } 
				/*{ new InternetExplorerDriver() }*/ };
		return Arrays.asList(data);
	}
	
	@BeforeClass
	public static void setUpClass() {
		SalesforceLoginTest.username = System.getenv("test_username");
		SalesforceLoginTest.password = System.getenv("test_password");
		assertNotNull("set environment variable 'test_username'", SalesforceLoginTest.username);
		assertNotNull("set environment variable 'test_password'", SalesforceLoginTest.password);
	}
	
	@Before
	public void setUp() {
		driver.get("https://login.salesforce.com");
	}
	
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	@AfterClass
	public static void tearDownClass() {
		SalesforceLoginTest.username = null;
		SalesforceLoginTest.password = null;
	}
	
	@Test
	public void testLogin() {
		final String loginPageTitle = "salesforce.com - Customer Secure Login Page";
		LoginPage page = new LoginPage(driver);
		assertEquals("Login page did not load", loginPageTitle, driver.getTitle());
		page.executeLogin(SalesforceLoginTest.username, SalesforceLoginTest.password);
		assertFalse("Login failed", driver.getTitle().equals(loginPageTitle));
	}
}
