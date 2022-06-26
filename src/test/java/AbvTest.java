import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;

public class AbvTest {
    private WebDriver driver;

    @Before
    public void setUp(){
        System.setProperty("webdriver.gecko.driver", "...\\Drivers\\geckodriver.exe"); //Required in order to run tests in Firefox (geckodriver.exe required). Replace the path with your own path of the geckodriver.exe file.
        driver = new FirefoxDriver();//Specify the driver required for the browser of use.
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @Test
    public void TestLogin_ValidCredentials_ShouldLoginCorrectly() throws InterruptedException {
        driver.get("https://www.abv.bg");//
        String validUsername = "some_username";
        String validPassword = "some_passowd";
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("loginBut"));
        usernameField.clear();
        usernameField.sendKeys(validUsername);
        passwordField.clear();
        passwordField.sendKeys(validPassword);
        loginBtn.click();
        assertEquals("https://passport.abv.bg/app/profiles/login", driver.getCurrentUrl());//compare URLs
        Thread.sleep(500);
        assertEquals("https://nm30.abv.bg/Mail.html", driver.getCurrentUrl());//compare URL
    }
    @Test
    public void TestSendMail_AllRequiredFieldsPopulated_ShouldSendAndRecieveCorrectly(){
        try {
            this.TestLogin_ValidCredentials_ShouldLoginCorrectly(); //gives error because of Thread.sleep(500) being unhandled
        } catch(InterruptedException e) {
            System.out.println("got interrupted!");
        }
        WebElement writeLetter = driver.findElement(By.className("abv-button"));
        writeLetter.click();

        String validSubject = "Test mail sending Selenium";
        WebElement subjectField = driver.findElement(By.className("gwt-TextBox"));
        subjectField.click();
        subjectField.clear();
        subjectField.sendKeys(validSubject);

        String validText = "Test body of the test mail";
        WebElement textField = driver.findElement(By.className("gwt-RichTextArea"));
        WebElement textField2 = driver.findElement(By.tagName("body"));
        textField.click();
        textField2.click();
        textField2.sendKeys(validText);

        String validReciever = "some_email";
        WebElement recieverField = driver.findElement(By.xpath("(//td[@class='clientField'])[2]"));
        WebElement recieverField2 = driver.findElement(By.xpath("(//input[@class='fl'])"));
        recieverField.click();
        recieverField2.sendKeys(validReciever);

        WebElement sendEmail = driver.findElement(By.className("abv-button"));
        sendEmail.click();
    }
    @After
    public void terDown(){

    }
}
