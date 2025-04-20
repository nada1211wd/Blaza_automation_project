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
        wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Increased timeout
        driver.get("https://www.demoblaze.com/");
    }

    @Test
    public void signup() {
        WebElement sign = driver.findElement(By.id("signin2"));
        sign.click();

        WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='sign-username']")));
        username.sendKeys("MINAAA");
        WebElement password = driver.findElement(By.xpath("//*[@id='sign-password']"));
        password.sendKeys("12345");
        WebElement sign_button = driver.findElement(By.xpath("//button[contains(text(),'Sign up')]"));
        sign_button.click();

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert text: " + alertText); // Print alert text for debugging
            alert.accept();
        } catch (Exception e) {
            System.out.println("No alert present after sign up.");
        }

        // Close the modal
        WebElement close = driver.findElement(By.xpath("//*[@id='signInModal']/div/div/div[1]/button/span"));
        close.click();
    }

    @Test(dependsOnMethods = "signup")
    public void login() {
        WebElement log = driver.findElement(By.id("login2"));
        log.click();

        WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginusername")));
        username.sendKeys("MINAAA");

        WebElement password = driver.findElement(By.id("loginpassword"));
        password.sendKeys("12345");

        WebElement login_button = driver.findElement(By.xpath("//button[contains(text(),'Log in')]"));
        login_button.click();

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert text: " + alertText); // Print alert text for debugging
            alert.accept();
        } catch (Exception e) {
            System.out.println("No alert present after login.");
        }
    }

    @Test(dependsOnMethods = "login")
    public void AddProduct() {
        WebElement mobile = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sony vaio i5")));
        mobile.click();

        WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add to cart')]")));
        addToCart.click();

        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert text: " + alertText); // Print alert text for debugging
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present after adding product to cart.");
        }
    }



    @Test(dependsOnMethods = "AddProduct")
    public void Checkout() {

        WebElement cart = driver.findElement(By.xpath("//a[contains(text(),'Cart')]"));
        cart.click();
        WebElement order = driver.findElement(By.xpath("//button[contains(text(),'Place Order')]"));
        order.click();
        WebElement Name = wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
        Name.sendKeys("Mina");
        WebElement Country = driver.findElement(By.id("country"));
        Country.sendKeys("Egypt");
        WebElement City = driver.findElement(By.id("city"));
        City.sendKeys("BNS");
        WebElement Credit = driver.findElement(By.id("card"));
        Credit.sendKeys("121514484649849");
        WebElement Month = driver.findElement(By.id("month"));
        Month.sendKeys("3");
        WebElement Year = driver.findElement(By.id("year"));
        Year.sendKeys("2024");
        WebElement Purchase = driver.findElement(By.xpath("//button[contains(text(),'Purchase')]"));
        Purchase.click();
        WebElement ok = driver.findElement(By.xpath("/html/body/div[10]/div[7]/div/button"));
        ok.click();
    }


    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }
}
