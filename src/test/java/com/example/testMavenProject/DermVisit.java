package com.example.testMavenProject;

import java.time.Duration;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;

public class DermVisit {
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
    public void Login() throws InterruptedException {
    	performLogin("testuser030@example.com", "ValidPass123");
    	System.out.println("Login successful!");
}
    @Test(priority = 2)
    public void dermatologyVisitFlow() {
        // Step 1: Click on the Dermatology Visit button
        clickDermatologyVisit();

        // Step 2: Click on the Continue button after selecting an option
        clickContinue();

        // Step 3: Fill in insurance details
        fillInsuranceDetails("AETNA", "SEDR", "10", "15", "1996");

        // Step 4: Upload insurance photos
        uploadInsurancePhotos("C:\\Users\\HP\\Downloads\\sss.png", "C:\\Users\\HP\\Downloads\\Current Implementation.png");

        // Step 5: Click on the Add Insurance button
        clickAddInsurance();
    }
    
    // Helper methods for the test cases
    private void performLogin(String email, String password) {
    	WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();
    }
    
    private void clickDermatologyVisit() {
        WebElement dermatologyVisitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div[1]/div/div/div[2]/div[2]/div[2]/div[2]")));
        dermatologyVisitButton.click();
        System.out.println("Clicked the dermatology visit successfully from home screen");
    }
    
    private void clickContinue() {
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div[1]/div[5]/div/div/button")));
        continueButton.click();
        System.out.println("Clicked the continue button from medical intake screen successfully");
    }

    private void fillInsuranceDetails(String carrierName, String memberId, String month, String day, String year) {
    	// Click on the Insurance Carrier field to activate it and ignore the initial dropdown suggestion
        WebElement insuranceCarrierField = wait.until(ExpectedConditions.elementToBeClickable(By.className("css-2b097c-container")));
        insuranceCarrierField.click();
        
        
//        wait.until(ExpectedConditions.elementToBeClickable(By.className("css-2b097c-container")));

        // Enter "AETNA" into the field
        insuranceCarrierField.sendKeys("AETNA");

        // Wait for the matching result to appear in the dropdown
        WebElement matchingResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'AETNA')]")));

     // Ensure that the matching result is clickable before interacting
        wait.until(ExpectedConditions.elementToBeClickable(matchingResult));
        
        // Click on the matching result
        matchingResult.click();

        // Enter member ID
        WebElement memberIdField = wait.until(ExpectedConditions.elementToBeClickable(By.id("member_id")));
        memberIdField.sendKeys(memberId);

        // Enter date of birth
        WebElement monthField = driver.findElement(By.className("react-date-picker__inputGroup__input.react-date-picker__inputGroup__month"));
        monthField.sendKeys(month);

        WebElement dayField = driver.findElement(By.className("react-date-picker__inputGroup__input.react-date-picker__inputGroup__day"));
        dayField.sendKeys(day);

        WebElement yearField = driver.findElement(By.className("react-date-picker__inputGroup__input.react-date-picker__inputGroup__year"));
        yearField.sendKeys(year);
        System.out.println("Filled all the fields successfully");
    }

    private void uploadInsurancePhotos(String frontImagePath, String backImagePath) {
        // Upload front insurance photo
        WebElement frontPhotoUpload = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='insurance_front_photo']/div")));
        frontPhotoUpload.sendKeys(frontImagePath);

        // Upload back insurance photo
        WebElement backPhotoUpload = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='insurance_back_photo']/div")));
        backPhotoUpload.sendKeys(backImagePath);
        System.out.println("Uploaded Insurance images successfully");
    }

    private void clickAddInsurance() {
        WebElement addInsuranceButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("flex items-center justify-center font-bold p-8 w-full focus:outline-none focus:ring-2 bg-orange text-white rounded-lg cursor-pointer text-3xl mt-6")));
        addInsuranceButton.click();
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
    
    }
