package page_object;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

public class MainPage {

    private final WebDriver driver;

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By accountButton = By.xpath(".//a[@href='/account']");
    private final By logo = By.xpath(".//div/a[@href='/']");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private final By bunsButton = By.xpath("//span[@class='text text_type_main-default'][text()='Булки']");
    private final By saucesButton = By.xpath("//span[@class='text text_type_main-default'][text()='Соусы']");
    private final By fillingsButton = By.xpath("//span[@class='text text_type_main-default'][text()='Начинки']");
    private final By activityTopping = By.xpath("//div[starts-with(@class,'tab_tab__1SPyG tab_tab_type_current__2BEPc')]//span");

    public By bunsImg = By.xpath(".//img[@alt='Краторная булка N-200i']");
    public By bunsText = By.xpath(".//h2[text()='Булки']");
    public By saucesImg = By.xpath(".//p[text()='Соус с шипами Антарианского плоскоходца']");
    public By fillingsImg = By.xpath(".//img[@alt='Плоды Фалленианского дерева']");
    public By textBurgerMainPage = By.xpath(".//section/h1[text()='Соберите бургер']");
    private final By placeOrderButton = By.xpath(".//button[contains(text(),'Оформить заказ')]");
    private final By saveButton = By.xpath(".//button[contains(text(),'Сохранить')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Click 'Login to account' button")
    public void clickOnLoginButton() {
        driver.findElement(loginButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Click 'Account' button")
    public void clickOnAccountButton() {
        driver.findElement(accountButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Click 'Stellar Burgers' logo")
    public void clickOnLogo() {
        driver.findElement(logo).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Click 'Constructor' button")
    public void clickOnConstructorButton() {
        driver.findElement(constructorButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Click 'Buns' button")
    public void clickOnBunsButton() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(bunsButton).click();
    }

    @Step("Click 'Sauces' button")
    public void clickOnSaucesButton() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(saucesButton).click();
    }

    @Step("Click 'Fillings' button")
    public void clickOnFillingButton() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(fillingsButton).click();
    }

    public void checkToppingBun() throws InterruptedException {
        Thread.sleep(500);
        String countActivity = driver.findElement(activityTopping).getText();
        assertEquals("Булки", countActivity);
    }

    public void checkToppingSauce() throws InterruptedException {
        Thread.sleep(500);
        String countActivity = driver.findElement(activityTopping).getText();
        assertEquals(countActivity, "Соусы");
    }

    public void checkToppingFillings() throws InterruptedException {
        Thread.sleep(1000);
        String countActivity = driver.findElement(activityTopping).getText();
        assertEquals(countActivity, "Начинки");
    }

    @Step("Wait for main page to load, text 'Соберите бургер' appears")
    public void waitForLoadMainPage() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(textBurgerMainPage));
    }

    @Step("Wait for 'Place order' button to appear")
    public void waitForPlaceOrderButton() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(placeOrderButton));
    }

    @Step("Wait for 'Save' button to appear")
    public void waitForSaveButton() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(saveButton));
    }

    @Step("Wait for loading animation to disappear")
    public void waitForInvisibilityLoadingAnimation() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (By.xpath(".//img[@src='./static/media/loading.89540200.svg' and @alt='loading animation']")));
        waitDocReady();
    }

    @Step("Wait for full document load")
    public void waitDocReady() {
        new WebDriverWait(driver, 20)
                .until((ExpectedCondition<Boolean>) wd ->
                        ((JavascriptExecutor) wd)
                                .executeScript("return document.readyState")
                                .equals("complete"));
    }
}
