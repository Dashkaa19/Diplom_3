package tests;

import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import page_object.LoginPage;
import page_object.MainPage;
import page_object.ProfilePage;
import utils.GenerateUser;

import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class TransitionsPersonalAccountTest {

    private WebDriver driver;
    private String driverType;
    private User user;
    private String accessToken = null;
    private UserClient userClient = new UserClient();
    private ValidatableResponse response;

    public TransitionsPersonalAccountTest(String driverType) {
        this.driverType = driverType;
    }

    @Before
    public void setUp() {
        user = GenerateUser.getRandomUser();
        response = userClient.createUser(user);
        accessToken = response.extract().path("accessToken");

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
        // Open the tested website
        driver.navigate().to("https://stellarburgers.nomoreparties.site/");
    }

    @After
    public void tearDown() {
        if (accessToken != null && !accessToken.isEmpty()) {
            userClient.deleteUser(StringUtils.substringAfter(accessToken, " "));
            accessToken = null;
        }
        driver.quit();
    }

    @Parameterized.Parameters(name = "Browser test results: {0}")
    public static Object[][] getBrowserTypes() {
        return new Object[][]{
                {"chromedriver"},
                {"yandexdriver"},
        };
    }

    @Test
    @DisplayName("Navigation by clicking on 'Personal Account'")
    public void transitionToProfilePageTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.authorization(user.getEmail(), user.getPassword());
        mainPage.waitForPlaceOrderButton();
        mainPage.clickOnAccountButton();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForLoadProfilePage();
    }

    @Test
    @DisplayName("Navigation to constructor from personal account")
    public void transitionToConstructorFromProfilePageTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.authorization(user.getEmail(), user.getPassword());
        mainPage.waitForPlaceOrderButton();
        mainPage.clickOnAccountButton();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForLoadProfilePage();
        mainPage.clickOnConstructorButton();
        mainPage.waitForLoadMainPage();
    }

    @Test
    @DisplayName("Navigation to constructor by clicking the 'Stellar Burgers' logo")
    public void transitionToStellarBurgersFromProfilePageTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.authorization(user.getEmail(), user.getPassword());
        mainPage.waitForPlaceOrderButton();
        mainPage.clickOnAccountButton();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForLoadProfilePage();
        mainPage.clickOnConstructorButton();
        mainPage.waitForLoadMainPage();
        mainPage.clickOnAccountButton();
        profilePage.waitForLoadProfilePage();
        mainPage.clickOnLogo();
        mainPage.waitForLoadMainPage();
    }

    @Test
    @DisplayName("Log out from account")
    @Description("Check logout functionality via 'Logout' button in the personal account.")
    public void exitFromProfileTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnAccountButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForLoadEntrance();
        loginPage.authorization(user.getEmail(), user.getPassword());
        mainPage.waitForLoadMainPage();
        mainPage.clickOnAccountButton();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForLoadProfilePage();
        profilePage.clickOnExitButton();
        mainPage.waitForInvisibilityLoadingAnimation();
        loginPage.waitForLoadEntrance();
    }
}
