import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

   @Test(priority = 1)
    public void testSuccessfulLogin() {
        login("zizi", "zi@123");

        // Assertion for successful login, verify user is redirected to dashboard
       Assert.assertEquals(driver.getCurrentUrl(), "https://www.demoblaze.com/index.html");

       // Check if the element with id "nameofuser" contains the text "welcomeMINAAA"
       WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
      Assert.assertTrue(welcomeMessage.getText().contains("welcomeMINA"));

       // Check for the presence of logout button after successful login
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2")));
       Assert.assertTrue(logoutButton.isDisplayed(), "Logout button not found");

      // Click on logout button
       logoutButton.click();
   }

    @Test(priority = 2)
    public void testInvalidUsername() {
        login("", "12345");
        // Assertion for invalid username, verify appropriate error message/alert is displayed
        Assert.assertTrue(isAlertPresentWithText("User does not exist."));
    }

    @Test(priority = 3)
    public void testInvalidPassword() {
        login("atiq", "");
        // Assertion for invalid password, verify appropriate error message/alert is displayed
        Assert.assertTrue(isAlertPresentWithText("Wrong password."));
    }

    @Test(priority = 4)
    public void testEmptyUsernameAndPassword() {
        login("", "");
        // Assertion for empty username and password, verify appropriate error message/alert is displayed
        Assert.assertTrue(isAlertPresentWithText("Please fill out Username and Password."));
    }

    @AfterClass
    public void closeBroweser() {
        // Close the WebDriver instance after all test methods have been executed
        driver.quit();
    }

    private void login(String username, String password) {
        // Navigate to the login page
        driver.get("https://www.demoblaze.com/index.html");
        WebElement log = driver.findElement(By.id("login2"));
        log.click();

        // Enter username
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginusername")));
        usernameField.sendKeys(username);

        // Enter password
        WebElement passwordField = driver.findElement(By.id("loginpassword"));
        passwordField.sendKeys(password);

        // Click on login button only if both username and password are not empty

            WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(),'Log in')]"));
            loginButton.click();


        // Wait for alert to be present
        wait.until(ExpectedConditions.alertIsPresent());
    }

    private boolean isAlertPresentWithText(String expectedText) {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert text: " + alertText); // Print alert text for debugging
            boolean result = alertText.equals(expectedText);
            alert.accept();
            return result;
        } catch (Exception e) {
            return false;
        }
    }
}

