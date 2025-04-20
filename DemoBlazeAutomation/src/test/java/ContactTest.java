import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;


public class ContactTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }
    @Test
    public void testContactsuccessfully(){
        contact("nada@gmail.com" ,"nada" , "thankyou" );
        Assert.assertTrue(isAlertPresentWithText("Thanks for the message!!"));
    }

    @Test
    public void testContactwithvalidemailandmessage(){
        contact("nada@gmail.com" ,"" , "thankyou" );
        Assert.assertTrue(isAlertPresentWithText("Thanks for the message!!"));
    }

    @Test
    public void testContactwithblankfields(){
        contact("" ,"" , "" );
        Assert.assertFalse(isAlertPresentWithText("Please fill out Contact Email,Contact Name and Message."));
    }

    @Test
    public void testContactwithinvalidemailformat(){
        contact("nadaaaglmailcom" ,"nada" , "thankyou" );
        Assert.assertFalse(isAlertPresentWithText("Please Enter Valid Contact Email."));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    private void contact(String email, String name, String message) {
        driver.get("https://www.demoblaze.com/index.html");
        WebElement contact = driver.findElement(By.xpath("//a[contains(text(),'Contact')]"));
        contact.click();

        WebElement ContactEmailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("recipient-email")));
        ContactEmailField.sendKeys(email);

        WebElement ContactNameField = driver.findElement(By.id("recipient-name"));
        ContactNameField.sendKeys(name);

        WebElement MessageField = driver.findElement(By.id("message-text"));
        MessageField.sendKeys(message);


        WebElement SendMessageButton = driver.findElement(By.xpath("//button[contains(text(),'Send message')]"));
        SendMessageButton.click();


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