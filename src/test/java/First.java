import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class First {
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
    public void testUntitledTestCase() throws Exception {
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
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
