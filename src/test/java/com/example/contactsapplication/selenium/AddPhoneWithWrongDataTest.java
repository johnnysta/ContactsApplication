package com.example.contactsapplication.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddPhoneWithWrongDataTest {

    private WebDriver driver;
    private JavascriptExecutor js;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("headless");
//        options.addArguments("disable-extensions");
//        options.addArguments("start-maximized");
//        options.addArguments("incognito");
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }


    void slowStep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addAddressWithWrongData() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.get("http://localhost:4200/home");
        driver.manage().window().setSize(new Dimension(1936, 1048));
        slowStep();
        driver.findElement(By.id("loginButton")).click();
        slowStep();
        driver.findElement(By.id("email")).sendKeys("b@b.hu");
        slowStep();
        driver.findElement(By.id("password")).sendKeys("majom");
        slowStep();
        driver.findElement(By.id("submitButton")).click();
        slowStep();
        driver.findElement(By.id("addNewContact")).click();

        slowStep();
        {
            WebElement element = driver.findElement(By.id("addNewPhoneButton"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).click().perform();
        }

        slowStep();
        driver.findElement(By.id("phoneUseType")).click();
        {
            WebElement dropdown = driver.findElement(By.id("phoneUseType"));
            dropdown.findElement(By.xpath("//option[. = 'PRIMARY']")).click();
        }

        slowStep();
        driver.findElement(By.id("phoneNumber")).sendKeys("1");
        {
            WebElement element = driver.findElement(By.id("note"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }

        slowStep();
        assertEquals(driver.findElement(By.cssSelector(".text-danger")).getText(),
                "Phone number is a required field. Please enter a valid phone number that is at least 3 characters long.");
    }
}
