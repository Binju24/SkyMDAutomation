package com.example.testMavenProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
//import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class signup {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        // No need to specify the path to ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait for 10 seconds
        driver.manage().window().maximize();
        driver.get("https://patient.skymdstaging.com/login");
    }

    @Test(priority = 1)
    public void testValidSignUp() throws InterruptedException {
        clickSignUpButtonFromLoginPage();
        enterEmail("testuser023@example.com");
        clickNextButton();
        enterPassword("ValidPass123");
        checkTermsAndConditions();
        clickContinueButton();
        completeProfile("John", "Doe", "7102000106", "92121");
        clickFinishSignUpButton();
        Thread.sleep(5000);

        // Assert that sign-up is successful
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "https://patient.skymdstaging.com/home";
        Assert.assertEquals(currentUrl, expectedUrl, "Sign-up was not successful. Expected URL: " + expectedUrl + ", but got: " + currentUrl);
        System.out.println("Sign Up Successfull! The current URL matches the expected URL.");
        
        // Perform logout to prepare for the next test
        performLogout();
        Thread.sleep(5000);
    }

    @Test(priority = 2)
    public void testInvalidEmail() throws InterruptedException {
        clickSignUpButtonFromLoginPage();
        enterEmail("testt@emmaiill");
        Thread.sleep(4000);
        WebElement nextButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/form/div/div[1]/div[2]/button"));
        Thread.sleep(4000);
        System.out.println("button identified");
        Thread.sleep(4000);
     // Check if the "Next" button is enabled
        boolean isNextButtonEnabled = nextButton.isEnabled();
        Thread.sleep(4000);

     // Assert that the "Next" button is not enabled 
        Assert.assertFalse(isNextButtonEnabled, "The 'Next' button is enabled for an invalid email.");
        System.out.println("Invalid Email Condition Pass! The Next button is not enabled.");
    }

    @Test(priority = 3)
    public void testEmptyFields() throws InterruptedException {
        //clickSignUpButtonFromLoginPage();
        
        Thread.sleep(500);
        WebElement emailField = driver.findElement(By.id("email"));

     // Click on the field
     emailField.click();

     // Clear the field by sending keys
     emailField.sendKeys(Keys.CONTROL + "a"); // Select all text
     emailField.sendKeys(Keys.DELETE);        // Delete the selected text
     System.out.println("Cleared the field");
        
     WebElement nextButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/form/div/div[1]/div[2]/button"));
     Thread.sleep(4000);
     System.out.println("button identified");
     Thread.sleep(4000);
  // Check if the "Next" button is enabled
     boolean isNextButtonEnabled = nextButton.isEnabled();
     Thread.sleep(4000);

  // Assert that the "Next" button is not enabled 
     Assert.assertFalse(isNextButtonEnabled, "The 'Next' button is enabled for an invalid email.");
     System.out.println("Empty Email Condition Pass! The Next button is not enabled.");
    }

    @Test(priority = 4)
    public void testPasswordRequirements() throws InterruptedException {
        enterEmail("testuser01@example.com");
        clickNextButton();
        enterPassword("short");
        Thread.sleep(5000);
        checkTermsAndConditions();
        Thread.sleep(5000);
        clickContinueButton();

        // Assert that an error message is displayed
        Thread.sleep(5000);
        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"signup_next\"]"));

        // Check if the "Next" button is enabled
           boolean isContinueButtonEnabled = continueButton.isEnabled();

        // Assert that the "Next" button is not enabled 
           Assert.assertFalse(isContinueButtonEnabled, "The 'Next' button is enabled for an invalid email.");
           System.out.println("Password criteria check passed");
    }

    @Test(priority = 5)
    public void testDuplicateEmail() throws InterruptedException {
    	Thread.sleep(500);
        WebElement emailField = driver.findElement(By.id("email"));

     // Click on the field
     emailField.click();

     // Clear the field by sending keys
     emailField.sendKeys(Keys.CONTROL + "a"); // Select all text
     emailField.sendKeys(Keys.DELETE);        // Delete the selected text
     System.out.println("Cleared the email field");
     
        enterEmail("binju@f22labs.com");
        
        WebElement passwordField = driver.findElement(By.id("password"));

        // Click on the field
        passwordField.click();

        // Clear the field by sending keys
        passwordField.sendKeys(Keys.CONTROL + "a"); // Select all text
        passwordField.sendKeys(Keys.DELETE);        // Delete the selected text
        System.out.println("Cleared the password field");
        
        enterPassword("binju@f22labs.com");
        
        clickContinueButton();

        // Assert that an error message is displayed
        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"signup_next\"]"));

        // Check if the "Next" button is enabled
           boolean isContinueButtonEnabled = continueButton.isEnabled();

        // Assert that the "Next" button is not enabled 
           Assert.assertFalse(isContinueButtonEnabled, "The 'Next' button is enabled for an invalid email.");
        System.out.println("Duplicate Email Check Pass");
    }

    // Helper methods for the test cases
    private void clickSignUpButtonFromLoginPage() {
        WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[1]/div/form/div/div[7]/p/a")));
        signUpButton.click();
    }

    private void clickFinishSignUpButton() {
        WebElement finishSignUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("GTM_portal_signup")));
        finishSignUpButton.click();
    }

    private void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        emailField.clear();
        emailField.sendKeys(email);
    }

    private void clickNextButton() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[1]/div/form/div/div[1]/div[2]/button")));
        nextButton.click();
    }

    private void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    private void checkTermsAndConditions() {
        WebElement termsCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[1]/div/form/div/div[1]/div[3]/div[1]/img")));
        termsCheckbox.click();
    }

    private void clickContinueButton() {
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("signup_next")));
        continueButton.click();
    }

    private void completeProfile(String firstName, String lastName, String mobile, String zipCode) {
        WebElement firstNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("first_name")));
        firstNameField.clear();
        firstNameField.sendKeys(firstName);

        WebElement lastNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("last_name")));
        lastNameField.clear();
        lastNameField.sendKeys(lastName);

        WebElement mobileField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div[1]/div/form/div/div[3]/div/input")));
        mobileField.clear();
        mobileField.sendKeys(mobile);

        WebElement zipCodeField = wait.until(ExpectedConditions.elementToBeClickable(By.id("zip_code")));
        zipCodeField.clear();
        zipCodeField.sendKeys(zipCode);
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

//    @AfterClass
//    public void teardown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}