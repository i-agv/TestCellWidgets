package org.gwtproject.samples.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementWorker {

    WebDriver driver;

    public ElementWorker(WebDriver driver) {
        this.driver = driver;
    }

    public void waitAndClick(WebElement w) {
        checkIsClickable(w);
        w.click();
    }

    public void checkVisibility(WebElement w) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(w));
    }

    public void checkIsClickable(WebElement w) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(w));
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(w));
    }
}
