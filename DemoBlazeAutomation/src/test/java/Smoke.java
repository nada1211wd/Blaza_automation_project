import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;

public class Smoke {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void openBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://www.demoblaze.com/");
    }

    @Test
    public void signup() {
        WebElement sign = wait.until(ExpectedConditions.elementToBeClickable(By.id("signin2")));
        sign.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-username")));
        WebElement username = driver.findElement(By.id("sign-username"));
        username.clear();
        username.sendKeys("fat");

        WebElement password = driver.findElement(By.id("sign-password"));
        password.clear();
        password.sendKeys("abe*2024");

        WebElement sign_button = driver.findElement(By.xpath("//button[contains(text(),'Sign up')]"));
        sign_button.click();

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present after sign up.");
        }

        // Close modal safely
        WebElement close = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='signInModal']//button[@class='btn btn-secondary' or @class='close']")));
        close.click();
    }

    @Test(dependsOnMethods = "signup")
    public void login() {
        WebElement log = wait.until(ExpectedConditions.elementToBeClickable(By.id("login2")));
        log.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        WebElement username = driver.findElement(By.id("loginusername"));
        username.clear();
        username.sendKeys("fat");

        WebElement password = driver.findElement(By.id("loginpassword"));
        password.clear();
        password.sendKeys("abe*2024");

        WebElement login_button = driver.findElement(By.xpath("//button[contains(text(),'Log in')]"));
        login_button.click();

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present after login.");
        }
    }

    @Test(dependsOnMethods = "login")
    public void AddProduct() {
        WebElement mobile = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sony vaio i5")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", mobile);

        WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add to cart')]")));
        addToCart.click();

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present after adding product to cart.");
        }
    }

    @Test(dependsOnMethods = "AddProduct")
    public void Checkout() {
        WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Cart')]")));
        cart.click();

        WebElement order = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Place Order')]")));
        order.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Mina");
        driver.findElement(By.id("country")).sendKeys("Egypt");
        driver.findElement(By.id("city")).sendKeys("BNS");
        driver.findElement(By.id("card")).sendKeys("121514484649849");
        driver.findElement(By.id("month")).sendKeys("3");
        driver.findElement(By.id("year")).sendKeys("2024");

        WebElement purchase = driver.findElement(By.xpath("//button[contains(text(),'Purchase')]"));
        purchase.click();

        WebElement ok = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[10]/div[7]/div/button")));
        ok.click();
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }
}

