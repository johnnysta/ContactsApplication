package com.example.contactsapplication.selenium;


//Prerequisite: the database should contain a user with email "a@a.hu", and password  "majom"

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

public class AddNewContactTest {

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


    void slowStep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addNewContactTest() {
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
        driver.findElement(By.id("firstName")).sendKeys("Hopkins");
        slowStep();
        driver.findElement(By.id("lastName")).sendKeys("Tuskó");
        slowStep();
        driver.findElement(By.id("email")).sendKeys("tusko.hopkins@hihkh.hu");
        slowStep();
        driver.findElement(By.id("birthDate")).sendKeys("2024-04-11");
        slowStep();
        driver.findElement(By.id("mothersName")).sendKeys("Kis Ica");
        slowStep();
        driver.findElement(By.id("ssId")).sendKeys("41241241");
        slowStep();
        driver.findElement(By.id("taxId")).sendKeys("341413412");

        slowStep();
        {
            WebElement element2 = driver.findElement(By.id("addNewPhoneButton"));
            Actions actions = new Actions(driver);
            actions.moveToElement(element2).click().build().perform();
        }

        slowStep();
        js.executeScript("window.scrollTo(0,0)");
        slowStep();
        driver.findElement(By.id("phoneUseType")).click();
        {
            WebElement dropdown = driver.findElement(By.id("phoneUseType"));
            dropdown.findElement(By.xpath("//option[. = 'WORK']")).click();
        }

        slowStep();
        driver.findElement(By.id("phoneNumber")).sendKeys("194 1988");
        slowStep();
        driver.findElement(By.id("note")).sendKeys("Note");
        slowStep();
        driver.findElement(By.id("okButton")).click();

        slowStep();
        {
            WebElement element2 = driver.findElement(By.id("addNewAddressButton"));
            Actions actions = new Actions(driver);
            actions.moveToElement(element2).click().build().perform();
        }

        slowStep();
        driver.findElement(By.id("zipCode")).sendKeys("11111");
        slowStep();
        driver.findElement(By.id("city")).sendKeys("Gödöllő");
        slowStep();
        driver.findElement(By.id("street")).sendKeys("Kossuth u.");
        slowStep();
        driver.findElement(By.id("houseNumber")).sendKeys("7");
        slowStep();
        driver.findElement(By.id("note")).sendKeys("Note");
        slowStep();
        driver.findElement(By.id("okButton")).click();

        slowStep();
        {
            WebElement element2 = driver.findElement(By.id("saveButton"));
            Actions actions = new Actions(driver);
            actions.moveToElement(element2).click().build().perform();
        }

        slowStep();
        js.executeScript("window.scrollTo(0,0)");
        slowStep();
        driver.findElement(By.cssSelector("tbody:last-child #detailsButton")).click();

        {
            String value = driver.findElement(By.id("firstName")).getAttribute("value");
            assertEquals(value, "Hopkins");
        }

        {
            String value = driver.findElement(By.id("lastName")).getAttribute("value");
            assertEquals(value, "Tuskó");
        }

        {
            String value = driver.findElement(By.id("email")).getAttribute("value");
            assertEquals(value, "tusko.hopkins@hihkh.hu");
        }

        {
            WebElement element = driver.findElement(By.cssSelector("app-phones-list-sub #phoneUseType"));
            Actions builder = new Actions(driver);
            builder.doubleClick(element).perform();
        }

        assertEquals(driver.findElement(By.cssSelector("app-phones-list-sub #phoneUseType")).getText(), "WORK");
        assertEquals(driver.findElement(By.cssSelector("app-phones-list-sub #phoneNumber")).getText(), "194 1988");

        {
            WebElement element = driver.findElement(By.cssSelector("app-addresses-list-sub #addressCity"));
            Actions builder = new Actions(driver);
            builder.doubleClick(element).perform();
        }
        assertEquals(driver.findElement(By.cssSelector("app-addresses-list-sub #addressCity")).getText(), "Gödöllő");


        {
            WebElement element = driver.findElement(By.cssSelector("app-addresses-list-sub #addressStreet"));
            Actions builder = new Actions(driver);
            builder.doubleClick(element).perform();
        }

        assertEquals(driver.findElement(By.cssSelector("app-addresses-list-sub #addressStreet")).getText(), "Kossuth u.");
    }


}
