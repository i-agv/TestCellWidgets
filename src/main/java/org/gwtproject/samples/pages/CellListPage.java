package org.gwtproject.samples.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.gwtproject.samples.models.Contact;
import org.gwtproject.samples.utils.ElementWorker;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Getter
public class CellListPage extends BasePage {

    public final String URL = "http://samples.gwtproject.org/samples/Showcase/Showcase.html#!CwCellList";

    @FindBy(xpath = "//div[@class='GNHGC04CHJ'][text()='Cell List']")
    WebElement pageTitle;

    @FindBy(xpath = "//td[@class='GNHGC04CIJ']/button[@class='gwt-Button']")
    WebElement generate50;

    @FindBy(xpath = "//div[@class='gwt-HTML'][contains(text(),'0 - ')]")
    WebElement numberOfUsers;

    @FindBy(xpath = "//td[text() = ' Last Name: ']/..//input")
    WebElement lastNameInput;

    @FindBy(xpath = "//td[text() = ' First Name: ']/..//input")
    WebElement firstNameInput;

    @FindBy(xpath = "//td[text() = ' Birthday: ']/..//input")
    WebElement birthdayCalendar;

    @FindBy(xpath = "//td[text() = ' Address: ']/..//textarea")
    WebElement addressInput;

    @FindBy(xpath = "//td[text() = ' Category: ']/..//select")
    WebElement categorySelect;

    @FindBy(xpath = "//td[text() = ' Category: ']/..//select/option[text() = 'Businesses']")
    WebElement businessesCategory;

    @FindBy(xpath = "//div[contains(@class, 'datePickerPreviousButton')]")
    WebElement calendarBackButton;

    @FindBy(xpath = "//td[@class='datePickerMonth']")
    WebElement currentMonthAndYear;

    @FindBy(xpath = "//button[text()='Create Contact']")
    WebElement createContactButton;

    @FindBy(xpath = "//div[@class='GNHGC04CKF'][text()='Cell Tree']")
    WebElement cellTreeButton;

    @FindBy(xpath = "//div[@class='GNHGC04CKF'][text()='Custom Data Grid']")
    WebElement customDataGridButton;

    public CellListPage(WebDriver driver) {
        super(driver);
    }

    ElementWorker elementWorker = new ElementWorker(driver);

    public int getCurrentUsersNumber() {
        elementWorker.checkVisibility(numberOfUsers);
        return Integer.parseInt(numberOfUsers.getText()
                .substring(numberOfUsers.getText().lastIndexOf(" ") + 1));
    }

    @Step("Создание нового контакта")
    public void createNewBusinessUser(Contact contact) {
        elementWorker.checkIsClickable(firstNameInput);
        firstNameInput.sendKeys(contact.getFirstName());
        lastNameInput.sendKeys(contact.getLastName());
        elementWorker.waitAndClick(categorySelect);
        elementWorker.waitAndClick(businessesCategory);
        selectBirthDay(contact.getBirthDate());
        addressInput.sendKeys(contact.getAddress());
        elementWorker.waitAndClick(createContactButton);
    }

    @Step("Выбор даты рождения {dateOfBirth}")
    private void selectBirthDay(Date dateOfBirth) {
        elementWorker.waitAndClick(birthdayCalendar);
        LocalDate birthDay = LocalDate.parse(dateOfBirth.toString(),
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz yyyy", Locale.US));

        long monthsBetween = ChronoUnit.MONTHS.between(
                YearMonth.from(birthDay),
                YearMonth.from(LocalDateTime.now())
        );

        for (int j = 0; j < monthsBetween; j++) {
            elementWorker.waitAndClick(calendarBackButton);
        }

        WebElement dayOfBirth = getDriver().findElement(By.xpath
                (String.format("//div[contains(@class, 'datePickerDay')][text()=%s][@tabindex=\"0\"]", birthDay.getDayOfMonth())));
        elementWorker.waitAndClick(dayOfBirth);
    }

    @Step("Проверка создания нового контакта {firstName}, {lastName}")
    public void checkUserCreated(String firstName, String lastName) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        List<WebElement> allContacts = getDriver().findElements(By.xpath("//div[@style='outline:none;']/table/tbody/tr[1]/td[2]"));

        while (allContacts.size() != getCurrentUsersNumber()) {
            jse.executeScript("arguments[0].scrollIntoView(true);", allContacts.get(allContacts.size() - 1));
            allContacts = getDriver().findElements(By.xpath("//div[@style='outline:none;']/table/tbody/tr[1]/td[2]"));
            elementWorker.checkVisibility(allContacts.get(allContacts.size() - 1));
        }
        allContacts = getDriver().findElements(By.xpath("//div[@style='outline:none;']/table/tbody/tr[1]/td[2]"));
        Assert.assertEquals(allContacts.get(allContacts.size() - 1).getText(), String.format("%s %s", firstName, lastName));
    }
}