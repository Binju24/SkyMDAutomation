package com.example.testMavenProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Signin {
	public class AppTest {
	private WebDriver driver;
    private final String url = "https://patient.skymdstaging.com/";
    private final String expectedUrlAfterLogin = "https://patient.skymdstaging.com/home";

    @BeforeTest
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);  
        Thread.sleep(5000);
    }
    
    @Test(priority = 1)
    public void testLoginWithCorrectCredentials() throws InterruptedException {
    	performLogin("binju@f22labs.com", "Welcome@123", false);
    	Thread.sleep(5000);

        // Verify the login was successful by checking the URL
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrlAfterLogin, "Login with correct credentials failed: URL mismatch.");
        System.out.println("Login successful! The current URL matches the expected URL.");
        Thread.sleep(5000);

        // Perform logout to prepare for the next test
        performLogout();
        Thread.sleep(5000);
        
    }
    
    @Test(priority = 2)
    public void testLoginWithIncorrectCredentials() throws InterruptedException {
    	
    	performLogin("incorrect-email@example.com", "incorrect-password", true);
        Thread.sleep(5000);

        // Verify the login failed by checking the URL does not match the expected
        Assert.assertNotEquals(driver.getCurrentUrl(), expectedUrlAfterLogin, "Login with incorrect credentials should not succeed.");
        System.out.println("Login failed as expected! The current URL does not match the expected URL.");
        
    }
    
    @Test(priority = 3)
    public void testLoginWithCorrectEmailIncorrectPassword() throws InterruptedException {
    	 Thread.sleep(5000);
    	performLogin("binju@f22labs.com", "incorrect-password", true);
       

        // Verify the login failed by checking the URL does not match the expected
        Assert.assertNotEquals(driver.getCurrentUrl(), expectedUrlAfterLogin, "Login with correct email but incorrect password should not succeed.");
        System.out.println("Login failed as expected with correct email but incorrect password! The current URL does not match the expected URL.");
        Thread.sleep(5000);
    }
    
    @Test(priority = 4)
    public void testLoginWithIncorrectEmailCorrectPassword() throws InterruptedException {
        performLogin("incorrect-email@example.com", "Welcome@123", true);
        Thread.sleep(5000);

        // Verify the login failed by checking the URL does not match the expected
        Assert.assertNotEquals(driver.getCurrentUrl(), expectedUrlAfterLogin, "Login with incorrect email but correct password should not succeed.");
        System.out.println("Login failed as expected with incorrect email but correct password! The current URL does not match the expected URL.");
        Thread.sleep(5000);
    }
    

    private void performLogin(String email, String password, boolean clearFields) throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        Thread.sleep(5000);
        
        System.out.println("Clear fields: " + clearFields);
        
        if (clearFields) {
        	Thread.sleep(5000);
            emailField.clear();
            Thread.sleep(5000);
            passwordField.clear();
            Thread.sleep(5000);
        }
        Thread.sleep(5000);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    private void performLogout() throws InterruptedException {
        // Perform the logout sequence
        driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/ul/li[4]/a/div")).click();
        Thread.sleep(2000);  // Reduce the wait time for better test speed
        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div[1]/div/div/img")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div[2]/div/div[7]/div/p")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div[4]/div/div/div/div[2]/button[2]")).click();
        Thread.sleep(2000);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
	}

}
