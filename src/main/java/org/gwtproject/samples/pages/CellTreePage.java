package org.gwtproject.samples.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.gwtproject.samples.models.Contact;
import org.gwtproject.samples.utils.ElementWorker;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class CellTreePage extends BasePage {

    @FindBy(xpath = "//div[@class='GNHGC04CHJ'][text()='Cell Tree']")
    WebElement pageTitle;

    @FindBy(xpath = "//div[@class='GNHGC04CNE'][contains(text(), 'Businesses')]/../div[1]/img")
    WebElement businessesExpandButton;

    @FindBy(xpath = "//b[contains(text(), 'Selected:')]/../div")
    WebElement selectedText;

    public CellTreePage(WebDriver driver) {
        super(driver);
    }

    ElementWorker elementWorker = new ElementWorker(driver);

    @Step("Поиск нового контакта")
    public void findContact(Contact contact) {
        elementWorker.waitAndClick(businessesExpandButton);
        String firstLetter = contact.getFirstName().substring(0, 1);
        elementWorker.waitAndClick(getDriver().findElement(By.xpath
                (String.format("//div[contains(text(), '%s (')]/../div/img", firstLetter))));
        elementWorker.checkVisibility(getDriver().findElement(By.xpath
                (String.format("//td[2][text() = '%s %s']", contact.getFirstName(),
                        contact.getLastName()))));
    }

    @Step("Проверка наличия и выбора контакта в Cell Tree")
    public void checkContactExist(Contact contact) {
        String fullName = contact.getFirstName() + " " + contact.getLastName();
        WebElement checkBox = getDriver().findElement(By.xpath
                (String.format("//td[2][text() = '%s']/ancestor::tr/td/input", fullName)));
        elementWorker.waitAndClick(checkBox);
        elementWorker.checkVisibility(selectedText);
        Assert.assertEquals(selectedText.getText(), fullName);
    }
}