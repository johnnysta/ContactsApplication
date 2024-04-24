package com.example.contactsapplication.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


//Prerequisite: the database should contain a user with email "a@a.hu", and password  "majom"

public class ChangePwFailTest {


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
    public void contactsLoginSuccessfulTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // Test name: LoginSuccessfulTest
        // Step # | name | target | value
        // 1 | open | /home |
        driver.get("http://localhost:4200/home");
        // 2 | setWindowSize | 1234x909 |
        driver.manage().window().setSize(new Dimension(1234, 909));
        // 3 | click | id=navbarNav |
        driver.findElement(By.id("navbarNav")).click();
        // 4 | verifyElementPresent | id=loggedInAsText |
        List<WebElement> elements = driver.findElements(By.id("loggedInAsText"));
        if (elements.size() > 0) {
            // 5 | click | id=logoutButton |
            driver.findElement(By.id("logoutButton")).click();
        }
        // 6 | click | id=loginButton |
        driver.findElement(By.id("loginButton")).click();
        // 7 | click | id=email |
        driver.findElement(By.id("email")).click();
        // 8 | type | id=email | a@a.hu
        driver.findElement(By.id("email")).sendKeys("a@a.hu");
        // 9 | click | id=password |
        driver.findElement(By.id("password")).click();
        // 10 | type | id=password | majom
        driver.findElement(By.id("password")).sendKeys("majom");
        // 11 | click | id=submitButton |
        driver.findElement(By.id("submitButton")).click();
        // 3 | click | linkText=Change Password |
        driver.findElement(By.linkText("Change Password")).click();
        // 4 | click | id=oldPassword |
        driver.findElement(By.id("oldPassword")).click();
        // 5 | type | id=oldPassword | vizilo
        driver.findElement(By.id("oldPassword")).sendKeys("vizilo");
        // 6 | type | id=newPassword | majom
        driver.findElement(By.id("newPassword")).sendKeys("majom");
        // 7 | type | id=confirmNewPassword | majom
        driver.findElement(By.id("confirmNewPassword")).sendKeys("majom");
        // 8 | click | css=.btn-success |
        driver.findElement(By.cssSelector(".btn-success")).click();
        // 9 | click | css=.alert |
        driver.findElement(By.cssSelector(".alert")).click();
        // 10 | assertText | id=otherServerError | Invalid password entered.
        assertEquals(driver.findElement(By.id("otherServerError")).getText(), "Invalid password entered.");
    }

}
