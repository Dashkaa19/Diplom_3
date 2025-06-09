package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;
import model.User;
import org.hamcrest.Matchers;

import java.util.Locale;

public class UserClient extends Config{


    @Step("Send POST request to /api/auth/register to create a new user")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .log().all()
                .post(Urls.USER_PATH + "register")
                .then()
                .log().all();
    }

    @Step("Send DELETE request to /api/auth/user to delete the current user")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .log().all()
                .delete(Urls.USER_PATH + "user")
                .then()
                .log().all();
    }
}
