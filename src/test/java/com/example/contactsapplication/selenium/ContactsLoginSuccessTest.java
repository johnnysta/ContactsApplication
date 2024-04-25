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

public class ContactsLoginSuccessTest {

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
    public void contactsLoginSuccessTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get("http://localhost:4200/home");
        driver.manage().window().setSize(new Dimension(1234, 909));

        driver.findElement(By.id("navbarNav")).click();
        List<WebElement> elements = driver.findElements(By.id("loggedInAsText"));
        if (elements.size() > 0) {
            driver.findElement(By.id("logoutButton")).click();
        }
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("email")).sendKeys("a@a.hu");
        driver.findElement(By.id("password")).sendKeys("majom");
        driver.findElement(By.id("submitButton")).click();
        assertEquals(driver.findElement(By.id("myContactsTitle")).getText(), "My Contacts");
    }


}
