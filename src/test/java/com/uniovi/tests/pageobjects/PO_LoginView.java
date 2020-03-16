package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView {

	public static void fillForm(WebDriver driver, String usernamep, String passwordp) {
		WebElement username=driver.findElement(By.name("username"));
		username.click();
		username.clear();
		username.sendKeys(usernamep);
		WebElement pasword=driver.findElement(By.name("password"));
		pasword.click();
		pasword.clear();
		pasword.sendKeys(passwordp);
		
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

}
