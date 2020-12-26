package com.hbh.springtesting.example;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloE2ESeleniumTest2 {

    private WebDriver driver;

    @LocalServerPort
    private int port;
    
// https://github.com/bonigarcia/webdrivermanager/blob/master/README.md
    @BeforeAll
    public static void setUpClass() throws Exception {       
        //WebDriverManager.chromedriver().setup(); not available on ubuntu
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        //driver = new ChromeDriver();
        driver = new FirefoxDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void helloPageHasTextHelloWorld() {
        driver.navigate().to(String.format("http://localhost:%s/hello", port));

        WebElement body = driver.findElement(By.tagName("body"));

        assertThat(body.getText(), containsString("Hello World!"));
    }
}

