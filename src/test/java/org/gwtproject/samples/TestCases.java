package org.gwtproject.samples;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.gwtproject.samples.models.Contact;
import org.gwtproject.samples.pages.CellListPage;
import org.gwtproject.samples.pages.CellTreePage;
import org.gwtproject.samples.pages.CustomDataGridPage;
import org.gwtproject.samples.utils.ElementWorker;
import org.junit.Assert;
import org.junit.Test;

public class TestCases extends BaseTest {

    CellListPage cellListPage = new CellListPage(driver);

    CellTreePage cellTreePage = new CellTreePage(driver);

    CustomDataGridPage customDataGridPage = new CustomDataGridPage(driver);

    ElementWorker elementWorker = new ElementWorker(super.driver);

    Contact contact = Contact.createRandomContact();

    @DisplayName("Генерация 50-ти новых контактов")
    @Description("Сгенерировать 50 контактов, проверить что контакты добавились")
    @Test
    public void generate50UsersTest() {
        cellListPage.open(cellListPage.URL);
        elementWorker.checkVisibility(cellListPage.getPageTitle());
        int numberBeforeGenerationNew = cellListPage.getCurrentUsersNumber();
        elementWorker.waitAndClick(cellListPage.getGenerate50());
        Assert.assertEquals(cellListPage.getCurrentUsersNumber(), numberBeforeGenerationNew + 50);
    }

    @DisplayName("Проверка создания нового контакта на странице Cell List")
    @Description("Создать новый контакт, с категорией Businesses, и выбрать дату рождения через календарь, проверить что контакт добавился")
    @Test
    public void createBusinessUserTest() {
        cellListPage.open(cellListPage.URL);
        elementWorker.checkVisibility(cellListPage.getPageTitle());
        cellListPage.createNewBusinessUser(contact);
        cellListPage.checkUserCreated(contact.getFirstName(), contact.getLastName());
    }

    @DisplayName("Проверка создания нового контакта на странице CellTree")
    @Description("Переключиться через пункт меню на «Cell Tree», в группе Businesses найти созданный контакт и отметить его чекбоксом, проверить что правй части под текстом «Selected:» отображается имя и фамилия контакта")
    @Test
    public void checkNewUserExistsOnCellTreePageTest() {
        cellListPage.open(cellListPage.URL);
        cellListPage.createNewBusinessUser(contact);
        elementWorker.waitAndClick(cellListPage.getCellTreeButton());
        elementWorker.checkVisibility(cellTreePage.getPageTitle());
        cellTreePage.findContact(contact);
        cellTreePage.checkContactExist(contact);
    }

    @DisplayName("Проверка наличия нового контакта на Custom Data Grid и наличия друзей")
    @Description("Переключиться через пункт меню на «Custom Data Grid» найти созданный контакт и проверить: проверить корректность рассчитанного возраста в поле «Age». Проверить что список друзей не пустой")
    @Test
    public void checkNewUserOnCustomDataGridPageTest() {
        cellListPage.open(cellListPage.URL);
        cellListPage.createNewBusinessUser(contact);
        elementWorker.waitAndClick(cellListPage.getCustomDataGridButton());
        elementWorker.checkVisibility(customDataGridPage.getPageTitle());
        customDataGridPage.findAndCheckContact(contact);
    }
}
