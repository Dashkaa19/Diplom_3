package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import page_object.LoginPage;
import page_object.MainPage;
import page_object.RegisterPage;
import utils.GenerateUser;

import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class RegistrationTest {

    private WebDriver driver;
    private String driverType;
    private User user;
    String PASSWORD_FAILED = RandomStringUtils.randomAlphanumeric(5);

    public RegistrationTest(String driverType) {
        this.driverType = driverType;
    }

    @Before
    public void setUp() {
        user = GenerateUser.getRandomUser();
        if (driverType.equals("chromedriver")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
        } else if (driverType.equals("yandexdriver")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/yandexdriver");
            ChromeOptions options = new ChromeOptions();
            options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
            driver = new ChromeDriver(options);
        }

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Navigate to the tested site
        driver.navigate().to("https://stellarburgers.nomoreparties.site/");
    }

    @Parameterized.Parameters(name = "Browser test results: {0}")
    public static Object[][] getBrowserTypes() {
        return new Object[][]{
                {"chromedriver"},
                {"yandexdriver"},
        };
    }

    @Test
    @DisplayName("Successful user registration")
    public void successfulRegistrationTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnRegister();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.waitForLoadRegisterPage();
        registerPage.registration(user.getName(), user.getEmail(), user.getPassword());
        loginPage.waitForLoadEntrance();
        loginPage.authorization(user.getEmail(), user.getPassword());
        mainPage.waitForPlaceOrderButton();
    }

    @Test
    @DisplayName("Unsuccessful user registration with short password")
    public void failedPasswordRegistrationTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnRegister();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.waitForLoadRegisterPage();
        registerPage.registration(user.getName(), user.getEmail(), PASSWORD_FAILED);
        Assert.assertTrue("Error message is not displayed", driver.findElement(registerPage.errorPasswordText).isDisplayed());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
