package page_object;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {

    private final WebDriver driver;

    private final By constructorButton = By.xpath(".//a[@href='/']/p[text()='Конструктор']");
    private final By exitButton = By.xpath(".//li/button[text()='Выход']");
    public final By textOnProfilePage = By.xpath(".//nav/p[text()='В этом разделе вы можете изменить свои персональные данные']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Click 'Constructor' button")
    public void clickOnConstructorButton() {
        driver.findElement(constructorButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Click 'Log out' button")
    public void clickOnExitButton() {
        driver.findElement(exitButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Wait for profile page to load with expected text")
    public void waitForLoadProfilePage() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(textOnProfilePage));
    }

    @Step("Wait for loading animation to disappear")
    public void waitForInvisibilityLoadingAnimation() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath(".//img[@src='./static/media/loading.89540200.svg' and @alt='loading animation']")));
    }
}
