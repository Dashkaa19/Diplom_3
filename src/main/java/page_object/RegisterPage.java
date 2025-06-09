package page_object;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    private final WebDriver driver;

    private final By nameField = By.xpath(".//div[./label[text()='Имя']]/input[@name='name']");
    private final By emailField = By.xpath(".//div[./label[text()='Email']]/input[@name='name']");
    private final By passwordField = By.xpath(".//div[./label[text()='Пароль']]/input[@name='Пароль']");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    public final By errorPasswordText = By.xpath(".//p[text()='Некорректный пароль']");
    public final By registerText = By.xpath(".//div/h2[text()='Регистрация']");
    public final By loginButton = By.cssSelector("[href=\"/login\"]");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickOnRegisterButton() {
        driver.findElement(registerButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Click on 'Login' button")
    public void clickOnLoginButton() {
        driver.findElement(loginButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Register user")
    public void registration(String name, String email, String password) throws InterruptedException {
        setName(name);
        setEmail(email);
        setPassword(password);
        Thread.sleep(1000);
        clickOnRegisterButton();
    }

    @Step("Wait for the 'Register' page to load")
    public void waitForLoadRegisterPage() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(registerText));
    }

    @Step("Wait for loading animation to disappear")
    public void waitForInvisibilityLoadingAnimation() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath(".//img[@src='./static/media/loading.89540200.svg' and @alt='loading animation']")));
    }
}
