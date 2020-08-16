import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import sun.plugin.javascript.navig.Link;

public class InsuranceTest {
    WebDriver driver;
    String baseUrl="http://www.sberbank.ru/ru/person";
    String baseUrl2="https://online.sberbankins.ru/store/travel/";
WebElement element;


    @Before
    public void beforeTest(){
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }


    @Test
    public void testInsurance() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        element=driver.findElement(By.xpath("//button[@class='lg-menu__link']//*[contains(text(),'Страхование')]"));

        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        String travelInsurance= "//li[@class='lg-menu__sub-item']//*[contains(text(),'Страхование путешественников')]";

        ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(travelInsurance)));
        driver.findElement(By.xpath(travelInsurance)).click();
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

        String originalWindow = driver.getWindowHandle();
        final Set<String> oldWindowsSet = driver.getWindowHandles();

        String signOnline= "//div[@class='t-content']//*[contains(text(),'Оформить онлайн')]";
        ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(signOnline)));
        driver.findElement(By.xpath(signOnline)).click();
        driver.manage().timeouts().implicitlyWait(50,TimeUnit.SECONDS);

        String newWindowHandle = (new WebDriverWait(driver, 30))
                .until(new ExpectedCondition<String>() {
                           public String apply(WebDriver driver) {
                               Set<String> newWindowsSet = driver.getWindowHandles();
                               newWindowsSet.removeAll(oldWindowsSet);
                               return newWindowsSet.size() > 0 ?
                                       newWindowsSet.iterator().next() : null;
                           }
                       }
                );
        driver.switchTo().window(newWindowHandle);
        System.out.println("New window title: " + driver.getTitle());

        //String signOnline2= "//div[@class='row']//*[contains(text(),'Оформить')]";
        String signOnline2= "/html/body/app/ng-component/div/div/div/app-setup-product/div/form/div/div[1]/div/button";
        ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(signOnline2)));
        driver.findElement(By.xpath(signOnline2)).click();

        fillField(By.id("surname_vzr_ins_0"), "Иванов");
        fillField(By.id("name_vzr_ins_0"), "Иван");


        fillField(By.id("person_lastName"), "Иванов");
        fillField(By.id("person_firstName"), "Иван");
        fillField(By.id("person_middleName"), "Иванович");

        driver.findElement(By.xpath("//*[contains(text(),'Мужской')]")).click();

        fillField(By.id("passportSeries"), "8878");
        fillField(By.id("passportNumber"), "453746");
        fillField(By.id("documentIssue"), "увд москвы");

        driver.findElement(By.id("birthDate_vzr_ins_0")).click();
        driver.findElement(By.id("birthDate_vzr_ins_0")).sendKeys("15.09.1988");

        driver.findElement(By.id("person_birthDate")).click();
        driver.findElement(By.id("person_birthDate")).sendKeys("15.09.1988");

        driver.findElement(By.id("documentDate")).click();
        driver.findElement(By.id("documentDate")).sendKeys("15.09.2000");

        driver.findElement(By.xpath("//*[contains(text(),'Продолжить')]")).click();
        System.out.println("Тест завершен!");

    }



    public void fillField(By locator, String value){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

    @After
    public void afterTest(){
       driver.quit();
    }
}
