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


public class ASignUpTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @Test
    public void signupsuccessfully() {
        signup("enassssGamalllllll", "012344410235");
        Assert.assertTrue(isAlertPresentWithText("Sign up successful."));

    }
    @Test
    public void testvalidusernameandblankpassword(){
        signup("Enasssssss", "");
        Assert.assertTrue(isAlertPresentWithText("Please fill out Username and Password."));
    }
    @Test
    public void testblankusernameandvalidpassword(){
        signup("", "123456789");
        Assert.assertTrue(isAlertPresentWithText("Please fill out Username and Password."));
    }

    @Test
    public void testrepeatedusernameandpassword() {
        signup("enaaaaassss", "0112389");
        Assert.assertTrue(isAlertPresentWithText("This user already exist."));

    }
    @Test
    public void testblankusernameandpassword() {
        signup("", "");
        Assert.assertTrue(isAlertPresentWithText("Please fill out Username and Password."));

    }

    @Test
    public void testusernameandpasswordwithspecialcharacters() {
        signup("enassssss!s$@gmail", "1/2**333-758-@456");
        Assert.assertTrue(isAlertPresentWithText("Sign up successful."));

    }
    @Test
   public void testusernameandpasswordwitonlyspace() {
      signup("", "");
     Assert.assertTrue(isAlertPresentWithText("Please fill out Username and Password."));

   }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    private void signup(String username, String password) {
        driver.get("https://www.demoblaze.com/index.html");
        WebElement signup = driver.findElement(By.id("signin2"));
        signup.click();

        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("sign-username")));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.id("sign-password"));
        passwordField.sendKeys(password);


        WebElement SignUpButton = driver.findElement(By.xpath("//button[contains(text(),'Sign up')]"));
        SignUpButton.click();


        wait.until(ExpectedConditions.alertIsPresent());
    }

    private boolean isAlertPresentWithText(String expectedText) {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert text: " + alertText);
            boolean result = alertText.equals(expectedText);
            alert.accept();
            return result;
        } catch (Exception e) {
            return false;
        }

    }
}