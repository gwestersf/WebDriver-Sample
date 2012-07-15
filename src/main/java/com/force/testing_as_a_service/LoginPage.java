package com.force.testing_as_a_service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object for Salesforce Login.
 * 
 * @author gwester
 */
public class LoginPage {
	
	private final WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void executeLogin(String username, String password) {
		WebElement usernameElement = driver.findElement(By.id("username"));
		usernameElement.sendKeys(username);
		
		WebElement passwordElement = driver.findElement(By.id("password"));
		passwordElement.sendKeys(password);
		
		WebElement loginForm = driver.findElement(By.id("login"));
		loginForm.submit();
	}
}
