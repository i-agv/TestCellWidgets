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

import java.util.List;

@Getter
public class CustomDataGridPage extends BasePage {

    @FindBy(xpath = "//div[@class='GNHGC04CHJ'][text()='Custom Data Grid']")
    WebElement pageTitle;

    @FindBy(xpath = "//td[@class='GNHGC04CLH'][2]/img[@class='gwt-Image']")
    WebElement lastPageButton;

    @FindBy(xpath = "//b[contains(text(), 'Selected:')]/../div")
    WebElement selectedText;

    public CustomDataGridPage(WebDriver driver) {
        super(driver);
    }

    ElementWorker elementWorker = new ElementWorker(driver);

    @Step("Поиск и проверка контакта в Custom Data Grid")
    public void findAndCheckContact(Contact contact) {
        elementWorker.waitAndClick(lastPageButton);
        WebElement rowWithContact = getDriver().findElement(By.xpath
                (String.format("//tr[child::td[3][text()='%s']][child::td[4][text()=\"%s\"]]",
                        contact.getFirstName(), contact.getLastName())));
        String rowNumber = rowWithContact.getAttribute("__gwt_row");
        String ageOnPage = rowWithContact.findElement(By.xpath("./td[5]")).getText().trim();
        elementWorker.waitAndClick(rowWithContact.findElement(By.xpath("./td[2]/a")));
        List<WebElement> numberOfFriends = getDriver().findElements(By.xpath
                (String.format("//tr[@__gwt_row='%s'][contains(@class, 'GNHGC04CEG')]", rowNumber)));
        Assert.assertTrue("List of friends is empty!", numberOfFriends.size() > 1);

        //TODO BUG: Возраст на странице считается некорректно!
        Assert.assertEquals("Incorrect age! Date of birth is " + contact.getBirthDate().toString(),
                contact.getAge(contact.getBirthDate()), Integer.parseInt(ageOnPage));
    }
}