import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PaymentTest {
    private WebDriver driver;
    private String baseUrl;
    private String orderNumber;
    private String totalAmount;
    private String currency;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\John\\Documents\\ShishQA-Sokolovskii\\src\\test\\chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        orderNumber = driver.findElement(By.id("order-number")).getText();
        totalAmount = driver.findElement(By.id("total-amount")).getText();
        currency = driver.findElement(By.id("currency")).getText();
    }

    @Test
    public void testConfirmed() throws Exception {

        driver.get(baseUrl);
        WebElement InputCardNumber = driver.findElement(By.id("input-card-number"));
        WebElement InputCardHolder = driver.findElement(By.id("input-card-holder"));
        Select cardExpiresMounth = new Select(driver.findElement(By.id("card-expires-month")));
        Select cardExpiresYear = new Select(driver.findElement(By.id("card-expires-year")));
        WebElement InputCardCvc = driver.findElement(By.id("input-card-cvc"));
        WebElement ActionSubmit = driver.findElement(By.id("action-submit"));
        WebElement ActionCancel = driver.findElement(By.id("action-cancel"));

        InputCardNumber.sendKeys("4000000000000002");
        InputCardHolder.sendKeys("YOBA FETTT");
        cardExpiresMounth.selectByIndex(7);
        cardExpiresYear.selectByValue("2023");
        InputCardCvc.sendKeys("777");

        ActionSubmit.click();
        driver.findElement(By.id("success")).click();

        assertEquals("Confirmed", driver.findElement(By.xpath("//*[@id=\"payment-item-status\"]/div[2]")).getText());
        assertEquals(orderNumber, driver.findElement(By.xpath("//*[@id=\"payment-item-ordernumber\"]/div[2]")).getText());
        assertEquals(totalAmount, driver.findElement(By.xpath("//*[@id=\"payment-item-total-amount\"]")).getText());
        // assertEquals(currency, driver.findElement(By.xpath("//*[@id=\"payment-item-total\"]/div[2]")));
    }

    @Test
    public void testDeclined() throws Exception {

        driver.get(baseUrl);
        WebElement InputCardNumber = driver.findElement(By.id("input-card-number"));
        WebElement InputCardHolder = driver.findElement(By.id("input-card-holder"));
        Select cardExpiresMounth = new Select(driver.findElement(By.id("card-expires-month")));
        Select cardExpiresYear = new Select(driver.findElement(By.id("card-expires-year")));
        WebElement InputCardCvc = driver.findElement(By.id("input-card-cvc"));
        WebElement ActionSubmit = driver.findElement(By.id("action-submit"));
        WebElement ActionCancel = driver.findElement(By.id("action-cancel"));

        InputCardNumber.sendKeys("5555555555554444");
        InputCardHolder.sendKeys("YOBA FETTT");
        cardExpiresMounth.selectByIndex(8);
        cardExpiresYear.selectByValue("2025");
        InputCardCvc.sendKeys("777");

        ActionSubmit.click();
        driver.findElement(By.id("success")).click();

        assertEquals("Declined by issuing bank", driver.findElement(By.xpath("//*[@id=\"payment-item-status\"]/div[2]")).getText());
        assertEquals(orderNumber, driver.findElement(By.xpath("//*[@id=\"payment-item-ordernumber\"]/div[2]")).getText());
        assertEquals(totalAmount, driver.findElement(By.xpath("//*[@id=\"payment-item-total-amount\"]")).getText());
        // assertEquals(currency, driver.findElement(By.xpath("//*[@id=\"payment-item-total\"]/div[2]")));
    }

    @Test
    public void testAuth() throws Exception {

        driver.get(baseUrl);
        WebElement InputCardNumber = driver.findElement(By.id("input-card-number"));
        WebElement InputCardHolder = driver.findElement(By.id("input-card-holder"));
        Select cardExpiresMounth = new Select(driver.findElement(By.id("card-expires-month")));
        Select cardExpiresYear = new Select(driver.findElement(By.id("card-expires-year")));
        WebElement InputCardCvc = driver.findElement(By.id("input-card-cvc"));
        WebElement ActionSubmit = driver.findElement(By.id("action-submit"));
        WebElement ActionCancel = driver.findElement(By.id("action-cancel"));

        InputCardNumber.sendKeys("4000000000000044");
        InputCardHolder.sendKeys("YOBA FETTT");
        cardExpiresMounth.selectByIndex(2);
        cardExpiresYear.selectByValue("2025");
        InputCardCvc.sendKeys("141");

        ActionSubmit.click();
        driver.findElement(By.id("success")).click();

        assertEquals("CONFIRMED", driver.findElement(By.xpath("//*[@id=\"payment-item-status\"]/div[2]")).getText());
        assertEquals(orderNumber, driver.findElement(By.xpath("//*[@id=\"payment-item-ordernumber\"]/div[2]")).getText());
        assertEquals(totalAmount, driver.findElement(By.xpath("//*[@id=\"payment-item-total-amount\"]")).getText());
        // assertEquals(currency, driver.findElement(By.xpath("//*[@id=\"payment-item-total\"]/div[2]")));
    }

    @Test
    public void test3d() throws Exception {

        driver.get(baseUrl);
        WebElement InputCardNumber = driver.findElement(By.id("input-card-number"));
        WebElement InputCardHolder = driver.findElement(By.id("input-card-holder"));
        Select cardExpiresMounth = new Select(driver.findElement(By.id("card-expires-month")));
        Select cardExpiresYear = new Select(driver.findElement(By.id("card-expires-year")));
        WebElement InputCardCvc = driver.findElement(By.id("input-card-cvc"));
        WebElement ActionSubmit = driver.findElement(By.id("action-submit"));
        WebElement ActionCancel = driver.findElement(By.id("action-cancel"));

        InputCardNumber.sendKeys("4000000000000036");
        InputCardHolder.sendKeys("YOBA FETTT");
        cardExpiresMounth.selectByIndex(2);
        cardExpiresYear.selectByValue("2025");
        InputCardCvc.sendKeys("123");

        ActionSubmit.click();

        assertEquals("Confirmed", driver.findElement(By.xpath("//*[@id=\"payment-item-status\"]/div[2]")).getText());
        assertEquals(orderNumber, driver.findElement(By.xpath("//*[@id=\"payment-item-ordernumber\"]/div[2]")).getText());
        assertEquals(totalAmount, driver.findElement(By.xpath("//*[@id=\"payment-item-total-amount\"]")).getText());
        // assertEquals(currency, driver.findElement(By.xpath("//*[@id=\"payment-item-total\"]/div[2]")));
    }

    @Test
    public void testNo3d() throws Exception {

        driver.get(baseUrl);
        WebElement InputCardNumber = driver.findElement(By.id("input-card-number"));
        WebElement InputCardHolder = driver.findElement(By.id("input-card-holder"));
        Select cardExpiresMounth = new Select(driver.findElement(By.id("card-expires-month")));
        Select cardExpiresYear = new Select(driver.findElement(By.id("card-expires-year")));
        WebElement InputCardCvc = driver.findElement(By.id("input-card-cvc"));
        WebElement ActionSubmit = driver.findElement(By.id("action-submit"));
        WebElement ActionCancel = driver.findElement(By.id("action-cancel"));

        InputCardNumber.sendKeys("4000000000000077");
        InputCardHolder.sendKeys("YOBA FETTT");
        cardExpiresMounth.selectByIndex(2);
        cardExpiresYear.selectByValue("2025");
        InputCardCvc.sendKeys("123");

        ActionSubmit.click();

        assertEquals("Confirmed", driver.findElement(By.xpath("//*[@id=\"payment-item-status\"]/div[2]")).getText());
        assertEquals(orderNumber, driver.findElement(By.xpath("//*[@id=\"payment-item-ordernumber\"]/div[2]")).getText());
        assertEquals(totalAmount, driver.findElement(By.xpath("//*[@id=\"payment-item-total-amount\"]")).getText());
        // assertEquals(currency, driver.findElement(By.xpath("//*[@id=\"payment-item-total\"]/div[2]")));
    }

    @Test
    public void ErrorNumber() throws Exception {

        driver.get(baseUrl);
        WebElement InputCardNumber = driver.findElement(By.id("input-card-number"));
        WebElement ActionSubmit = driver.findElement(By.id("action-submit"));
        WebElement ActionCancel = driver.findElement(By.id("action-cancel"));

        InputCardNumber.sendKeys("0000000000001111");
        ActionSubmit.click();

        assertEquals("Card number is not valid", driver.findElement(By.xpath("//*[@id=\"card-number-field\"]/div/label")).getText());

        Screenshot screenshot = new AShot().takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "png", new File("src\\test\\err_numb.png"));
    }

    @Test
    public void ErrorHolder() throws Exception {

        driver.get(baseUrl);
        WebElement InputCardHolder = driver.findElement(By.id("input-card-holder"));
        WebElement ActionSubmit = driver.findElement(By.id("action-submit"));
        WebElement ActionCancel = driver.findElement(By.id("action-cancel"));

        InputCardHolder.sendKeys("Имя кирилицей");
        ActionSubmit.click();

        assertEquals("Cardholder name is not valid", driver.findElement(By.xpath("//*[@id=\"card-holder-field\"]/div/label")).getText());

        Screenshot screenshot = new AShot().takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "png", new File("src\\test\\err_hold.png"));
    }

    @Test
    public void ErrorDate() throws Exception {

        driver.get(baseUrl);
        Select cardExpiresMounth = new Select(driver.findElement(By.id("card-expires-month")));
        Select cardExpiresYear = new Select(driver.findElement(By.id("card-expires-year")));
        WebElement ActionSubmit = driver.findElement(By.id("action-submit"));
        WebElement ActionCancel = driver.findElement(By.id("action-cancel"));

        cardExpiresMounth.selectByIndex(1);
        cardExpiresYear.selectByValue("2021");
        ActionSubmit.click();

        assertEquals("Invalid date", driver.findElement(By.xpath("//*[@id=\"card-expires-field\"]/div/label")).getText());

        Screenshot screenshot = new AShot().takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "png", new File("src\\test\\err_date.png"));
    }

    @Test
    public void ErrorCVC() throws Exception {

        driver.get(baseUrl);
        WebElement InputCardCvc = driver.findElement(By.id("input-card-cvc"));
        WebElement ActionSubmit = driver.findElement(By.id("action-submit"));
        WebElement ActionCancel = driver.findElement(By.id("action-cancel"));

        InputCardCvc.sendKeys("12");
        ActionSubmit.click();

        assertEquals("CVV2/CVC2/CAV2 is not valid", driver.findElement(By.xpath("//*[@id=\"card-cvc-field\"]/div/label")).getText());

        Screenshot screenshot = new AShot().takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "png", new File("src\\test\\err_cvc.png"));
    }

    @Test
    public void testScreenShot() throws IOException
    {
        driver.get(baseUrl);

        WebElement Cvc = driver.findElement(By.id("cvc-hint-toggle"));

        Actions action = new Actions(driver);
        action.moveToElement(Cvc).perform();

        Screenshot screenshot = new AShot().takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "png", new File("src\\test\\sc.png"));

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
