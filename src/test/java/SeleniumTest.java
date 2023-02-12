import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestPositive() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Кошкина Маруся");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79998887766");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();

        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void fieldBlankName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79998887766");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.className("input__sub")).getText();

        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void fieldBlankPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Кошкина Маруся");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.className("input__sub")).getText();

        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void latinCharsInFieldName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Maria Koshkina");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79998887766");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.className("input__sub")).getText();

        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void wrongPhoneNumber() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Кошкина Маруся");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89998887766");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'] [class='input__sub']")).getText();

        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void checkBoxNotChecked() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Кошкина Маруся");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79998887766");
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'] [class='input__sub']")).getText();

        Assertions.assertEquals("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. " +
                "Проверьте, что номер ваш и введен корректно.", text.trim());

    }


}
