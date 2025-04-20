import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class Checkout {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        // Initialize ChromeDriver instance
        driver = new ChromeDriver();
        // Initialize WebDriverWait with a timeout of 10 seconds
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Maximize window
        driver.manage().window().maximize();
    }

    @Test
    public void testNameAndCreditCardMandatory() {
        // Navigate to the website
        driver.get("https://www.demoblaze.com/cart.html");

        // Click on Place Order button
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Place Order')]")));
        orderButton.click();

        // Click on Purchase button without entering Name and Credit Card
        WebElement purchaseButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Purchase')]")));
        purchaseButton.click();

        // Verify alert message
        String alertText = driver.switchTo().alert().getText();
        Assert.assertEquals(alertText, "Please fill out Name and Creditcard.");

        // Dismiss the alert
        driver.switchTo().alert().dismiss();
    }
    @Test
    public void testEmptyNameAndValidCreditCard() {
        // Navigate to the website
        driver.get("https://www.demoblaze.com/cart.html");

        // Click on Place Order button
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Place Order')]")));
        orderButton.click();

        // Wait for the name field to be visible
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        // Clear any existing text in the name field
        nameField.clear();

        // Enter valid Credit Card without entering Name
        WebElement creditCardField = wait.until(ExpectedConditions.elementToBeClickable(By.id("card")));
        creditCardField.sendKeys("1234567890123456");

        // Click on Purchase button
        WebElement purchaseButton = driver.findElement(By.xpath("//button[contains(text(),'Purchase')]"));
        purchaseButton.click();

        // Verify alert message for empty Name
        String alertText = driver.switchTo().alert().getText();
        Assert.assertEquals(alertText, "Please fill out Name and Creditcard.");

        // Dismiss the alert
        driver.switchTo().alert().dismiss();
    }

    @Test
    public void testNameLengthAndFormat() {
        // Navigate to the website
        driver.get("https://www.demoblaze.com/cart.html");

        // Click on Place Order button
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Place Order')]")));
        orderButton.click();

        // Enter Name less than 2 characters
        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
        nameField.sendKeys("A");
        WebElement credit = wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
        credit.sendKeys("1234567899876542");

        // Click on Purchase button
        WebElement purchaseButton = driver.findElement(By.xpath("//button[contains(text(),'Purchase')]"));
        purchaseButton.click();

        // Verify alert message
        String alertText = driver.switchTo().alert().getText();
        Assert.assertEquals(alertText, "Please fill out Name and Creditcard.");

        // Dismiss the alert
        driver.switchTo().alert().dismiss();
    }

    @Test
    public void testCreditCardLength() {
        driver.get("https://www.demoblaze.com/cart.html");

        // Click on Place Order button
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Place Order')]")));
        orderButton.click();

        // Enter valid Name
        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
        nameField.sendKeys("Mina Talaat");

        // Enter valid Credit Card
        WebElement creditCardField = driver.findElement(By.id("card"));
        creditCardField.sendKeys("123456789086868612456");

        // Click on Purchase button
        WebElement purchaseButton = driver.findElement(By.xpath("//button[contains(text(),'Purchase')]"));
        purchaseButton.click();

        // Wait for the alert to appear
        WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successAlert = alertWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[10]/h2")));

        // Verify the alert message
        Assert.assertEquals(successAlert.getText(), "Thank you for your purchase!");
    }
    @Test
    public void testValidNameAndCreditCard() {
        // Navigate to the website
        driver.get("https://www.demoblaze.com/cart.html");

        // Click on Place Order button
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Place Order')]")));
        orderButton.click();

        // Enter valid Name
        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
        nameField.sendKeys("Mina Talaat");

        // Enter valid Credit Card
        WebElement creditCardField = driver.findElement(By.id("card"));
        creditCardField.sendKeys("1234567890123456");

        // Click on Purchase button
        WebElement purchaseButton = driver.findElement(By.xpath("//button[contains(text(),'Purchase')]"));
        purchaseButton.click();

        // Wait for the alert to appear
        WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successAlert = alertWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[10]/h2")));

        // Verify the alert message
        Assert.assertEquals(successAlert.getText(), "Thank you for your purchase!");
    }



    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
}
