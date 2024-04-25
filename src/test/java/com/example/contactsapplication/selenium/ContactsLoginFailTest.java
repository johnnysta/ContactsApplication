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


//Prerequisite: the database contains a user with email "a@a.hu", and password other than"ssssss"

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
        driver.get("http://localhost:4200/home");
        driver.manage().window().setSize(new Dimension(1936, 1048));
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("email")).sendKeys("a@a.hu");
        driver.findElement(By.id("password")).sendKeys("ssssss");
        driver.findElement(By.id("submitButton")).click();
        assertEquals(driver.findElement(By.id("otherServerError")).getText(), "Bad credentials.");
    }
}
