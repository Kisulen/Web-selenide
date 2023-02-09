import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");

    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestPositive() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Кошкина Маруся");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79998887766");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void fieldsBlank() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.className("input__sub")).getText();

        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void latinCharsInFieldName() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Maria Koshkina");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79998887766");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.className("input__sub")).getText();

        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void wrongPhoneNumber() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Кошкина Маруся");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89998887766");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'] [class='input__sub']")).getText();

        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void checkBoxNotChecked() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Кошкина Маруся");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79998887766");
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'] [class='input__sub']")).getText();

        Assertions.assertEquals("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. " +
                "Проверьте, что номер ваш и введен корректно.", text.trim());

    }


}
