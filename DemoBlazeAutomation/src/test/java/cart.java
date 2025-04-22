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
        
    }
    @Test
    public void testRemoveProductFromCart() {
        // Navigate to the home page
        driver.get("https://www.demoblaze.com/index.html");

        // Add a product first (Sony vaio i5)
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sony vaio i5")));
        productLink.click();

        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add to cart')]")));
        addToCartButton.click();

        // Handle alert
        String expectedAlertMessage = "Product added";
        Assert.assertTrue(isAlertPresentWithText(expectedAlertMessage), "Alert message does not match expected: " + expectedAlertMessage);

        // Navigate to Cart
        WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur")));
        cartLink.click();

        // Wait for product to appear in cart
        WebElement deleteLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Delete']")));
        deleteLink.click();

        // Optional: Wait a bit to ensure deletion processed (can replace with wait for cart to be empty)
        wait.until(ExpectedConditions.invisibilityOf(deleteLink));

        // Validate that product was removed (cart empty)
        WebElement cartTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
        Assert.assertTrue(cartTable.getText().isEmpty(), "Cart is not empty after deletion");
    }

    @Test
    public void testAddmultiProductToCart() {
        // Navigate to the home page
        driver.get("https://www.demoblaze.com/index.html");

        // Add first product (Sony_vaio i5)
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sony vaio i5")));
        productLink.click();

        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add to cart')]")));
        addToCartButton.click();

        String expectedAlertMessage = "Product added";
        Assert.assertTrue(isAlertPresentWithText(expectedAlertMessage), "Alert message does not match expected: " + expectedAlertMessage);

        // Navigate back to home page
        driver.get("https://www.demoblaze.com/index.html");

        // Add second product (Samsung galaxy s7)
        WebElement productLink2 = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Samsung galaxy s7")));
        productLink2.click();

        WebElement addToCartButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add to cart')]")));
        addToCartButton2.click();

        String expectedAlertMessage2 = "Product added";
        Assert.assertTrue(isAlertPresentWithText(expectedAlertMessage2), "Alert message does not match expected: " + expectedAlertMessage2);
    }

    
    
    

    @AfterClass
    public void Closebrowser() {
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

  
}

