import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class cart {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testAddProductToCart() {
        // Navigate to the product page
        driver.get("https://www.demoblaze.com/index.html");
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sony vaio i5")));
        productLink.click();

        // Click on "Add to cart" button
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add to cart')]")));
        addToCartButton.click();

        // Check for the alert with message "Product added"
        String expectedAlertMessage = "Product added";
        Assert.assertTrue(isAlertPresentWithText(expectedAlertMessage), "Alert message does not match expected: " + expectedAlertMessage);

        // Accept the alert
        acceptAlert();
    }

    @AfterClass
    public void tearDown() {
        // Close the WebDriver instance after all test methods have been executed
        driver.quit();
    }

    private boolean isAlertPresentWithText(String expectedText) {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("Alert text: " + alertText); // Print alert text for debugging
            boolean result = alertText.contains(expectedText);
            alert.accept();
            return result;
        } catch (Exception e) {
            return false;
        }
    }

    private void acceptAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (Exception e) {
            System.out.println("No alert present.");
        }
    }
}
