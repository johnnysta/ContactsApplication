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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


//Prerequisites: the database should contain a user with email "d@d.hu", and password  "majom"
//This account should contain zero contacts.

public class DeleteContactTest {

    private WebDriver driver;
    JavascriptExecutor js;

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


    @Test
    public void deleteContactTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.get("http://localhost:4200/home");
        driver.manage().window().setSize(new Dimension(1936, 1048));

        driver.findElement(By.id("navbarNav")).click();
        List<WebElement> elements = driver.findElements(By.id("loggedInAsText"));
        if (elements.size() > 0) {
            driver.findElement(By.id("logoutButton")).click();
        }
        driver.findElement(By.id("loginButton")).click();
        driver.findElement(By.id("email")).sendKeys("d@d.hu");
        driver.findElement(By.id("password")).sendKeys("majom");
        driver.findElement(By.id("submitButton")).click();

        js.executeScript("window.scrollTo(0,0)");
        driver.findElement(By.id("addNewContact")).click();
        driver.findElement(By.id("firstName")).sendKeys("Alfréd");
        driver.findElement(By.id("lastName")).sendKeys("Piszok");
        driver.findElement(By.id("email")).sendKeys("alfred.piszok@gjkgk.hu");
        driver.findElement(By.id("birthDate")).sendKeys("2024-04-22");

        {
            WebElement element2 = driver.findElement(By.id("saveButton"));
            Actions actions = new Actions(driver);
            actions.moveToElement(element2).click().build().perform();
        }

        js.executeScript("window.scrollTo(0,0)");

        {
            WebElement element2 = driver.findElement(By.id("deleteButton"));
            Actions actions = new Actions(driver);
            actions.moveToElement(element2).click().build().perform();
        }

        driver.findElement(By.id("confirmDeleteButton")).click();

        assertEquals(driver.findElement(By.id("noDataText")).getText(), "No contacts available for this user. You can add contacts by clicking the button below.");
    }


}
