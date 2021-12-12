package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrder {
    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void SetUp() {
        //prepare Selenium
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSetForm() {
        driver.get("http://localhost:9999");
        System.out.print("");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ордов Олег");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998887766");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldCheckFieldName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ордов 1111");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998887766");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.className("input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual, "Текст сообщения не совпадает!");
    }

    @Test
    public void shouldCheckEmptyFieldName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998887766");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.className("input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual, "Текст сообщения не совпадает!");
    }

    @Test
    public void shouldCheckFieldPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Орлов Олег");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("879998887766");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual, "Текст сообщения не совпадает!");
    }

    @Test
    public void shouldCheckEmptyFieldPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Орлов Олег");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual, "Текст сообщения не совпадает!");
    }

    @Test
    public void shouldCheckCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Орлов Олег");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998887766");
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid")).getCssValue("color");
        String expected = "rgba(255, 92, 92, 1)";
        assertEquals(expected, actual, "Цвет не совпадает!");
    }
}
