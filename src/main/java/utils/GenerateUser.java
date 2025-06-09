package utils;

import model.User;
import org.apache.commons.lang3.RandomStringUtils;

public class GenerateUser {

    private static final int FIELD_LENGTH = 8;
    private static final String EMAIL_DOMAIN = "@yandex.ru";

    public static User getRandomUser() {
        String name = RandomStringUtils.randomAlphabetic(FIELD_LENGTH);
        String email = name.toLowerCase() + EMAIL_DOMAIN;
        String password = RandomStringUtils.randomAlphanumeric(FIELD_LENGTH);
        return new User(email, password, name);
    }
}
