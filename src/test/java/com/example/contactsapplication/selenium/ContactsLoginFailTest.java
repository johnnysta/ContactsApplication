package com.example.contactsapplication.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ContactsLoginFailTest {
    private WebDriver driver;

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
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void contactsLoginFail() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // Test name: ContactsLoginFail
        // Step # | name | target | value
        // 1 | open | /home |
        driver.get("http://localhost:4200/home");
        // 2 | setWindowSize | 1936x1048 |
        driver.manage().window().setSize(new Dimension(1936, 1048));
        // 3 | click | css=.btn | 
        driver.findElement(By.id("loginButton")).click();
        // 4 | click | id=email |
        driver.findElement(By.id("email")).click();
        // 5 | type | id=email | a@a.hu
        driver.findElement(By.id("email")).sendKeys("a@a.hu");
        // 6 | click | id=password |
        driver.findElement(By.id("password")).click();
        // 7 | type | id=password | ssssss
        driver.findElement(By.id("password")).sendKeys("ssssss");
        // 8 | click | css=.btn-success |
        driver.findElement(By.id("submitButton")).click();
        // 9 | click | css=.alert |
        driver.findElement(By.id("otherServerError")).click();
        // 10 | assertText | css=small | Bad credentials.
        assertEquals(driver.findElement(By.id("otherServerError")).getText(), "Bad credentials.");
    }
}
