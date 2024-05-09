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

public class AddAddressWithWrongDataTest {

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
            WebElement element2 = driver.findElement(By.id("addNewAddressButton"));
            Actions actions = new Actions(driver);
            actions.moveToElement(element2).click().build().perform();
        }

        slowStep();
        driver.findElement(By.id("city")).click();
        slowStep();
        driver.findElement(By.id("city")).sendKeys("a");
        slowStep();
        driver.findElement(By.id("zipCode")).click();
        slowStep();
        driver.findElement(By.id("street")).click();
        slowStep();
        driver.findElement(By.id("street")).sendKeys("a");
        slowStep();
        driver.findElement(By.id("zipCode")).click();
        slowStep();
        driver.findElement(By.id("houseNumber")).click();
        slowStep();
        driver.findElement(By.id("zipCode")).click();
        slowStep();
        driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .text-danger")).click();
        assertEquals(driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .text-danger")).getText(),
                "City is a required field. Please enter a valid city name that is at least 2 characters long.");
        assertEquals(driver.findElement(By.cssSelector(".mb-3:nth-child(3) > .text-danger")).getText(),
                "Street is a required field. Please enter a valid street name that is at least 2 characters long.");
        assertEquals(driver.findElement(By.cssSelector(".mb-3:nth-child(4) > .text-danger")).getText(),
                "House number is a required field. Please enter a valid house number.");
    }

}
