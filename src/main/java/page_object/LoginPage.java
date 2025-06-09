package page_object;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;

    public final By entrance = By.xpath(".//main/div/h2[text()='Вход']");
    private final By emailField = By.xpath(".//div[@class='input pr-6 pl-6 input_type_text input_size_default']/input[@name='name']");
    private final By passwordField = By.xpath(".//div[@class='input pr-6 pl-6 input_type_password input_size_default']/input[@name='Пароль']");
    public final By enterButton = By.xpath(".//div/a[@href='/']");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By constructorButton = By.xpath(".//a/p[text()='Конструктор']");
    private final By registerLink = By.xpath(".//a[@href='/register' and text()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath(".//a[@href='/forgot-password' and text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Enter Email")
    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Enter password")
    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Click 'Login' button")
    public void clickOnLoginButton() {
        driver.findElement(loginButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Click 'Register' link")
    public void clickOnRegister() {
        driver.findElement(registerLink).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Click 'Forgot password' link")
    public void clickOnForgotPasswordLink() {
        driver.findElement(forgotPasswordLink).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Click 'Constructor' button")
    public void clickOnConstructorButton() {
        driver.findElement(constructorButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Click 'Stellar Burgers' logo")
    public void clickOnLogo() {
        driver.findElement(enterButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Authorize user")
    public void authorization(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickOnLoginButton();
    }

    @Step("Wait for 'Вход' text to be visible")
    public void waitForLoadEntrance() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(entrance));
    }

    @Step("Wait until loading animation disappears")
    public void waitForInvisibilityLoadingAnimation() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (By.xpath(".//img[@src='./static/media/loading.89540200.svg' and @alt='loading animation']")));
    }
}
