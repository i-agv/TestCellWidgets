package org.gwtproject.samples;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public WebDriver driver = setProperties();

    @Before
    public void before() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @After
    public void after() {
        driver.quit();
    }

    private ChromeDriver setProperties() {
        Properties props = new Properties();
        try {
            FileInputStream fin = new FileInputStream("src\\test\\resources\\config\\application.properties");
            props.load(fin);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        System.setProperty("webdriver.chrome.driver", props.getProperty("chromeDriverPath"));
        return new ChromeDriver();
    }
}
