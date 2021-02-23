package ru.netology;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.Registration.*;


public class AuthTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");

    }

    @Test
    void shouldSendFormValid() {
        UserInfo validUserInfo = DataGenerator.Registration.generateValidUser();
        DataGenerator.SendQuery.setUpAll(validUserInfo);
        $("[data-test-id=login] input").setValue(validUserInfo.getLogin());
        $("[data-test-id=password] input").setValue(validUserInfo.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldSendFormBlockedUser() {
        UserInfo blockedUserInfo = DataGenerator.Registration.generateBlockedUser();
        DataGenerator.SendQuery.setUpAll(blockedUserInfo);
        $("[data-test-id=login] input").setValue(blockedUserInfo.getLogin());
        $("[data-test-id=password] input").setValue(blockedUserInfo.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    void  shouldGetErrorIfWrongLogin() {
        UserInfo validUserInfo = DataGenerator.Registration.generateValidUser();
        DataGenerator.SendQuery.setUpAll(validUserInfo);
        $("[data-test-id=login] input").setValue("125869574");
        $("[data-test-id=password] input").setValue(validUserInfo.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(visible);

    }
    @Test
    void shouldGetErrorIfWrongPassword() {
        UserInfo validUserInfo = DataGenerator.Registration.generateValidUser();
        DataGenerator.SendQuery.setUpAll(validUserInfo);
        $("[data-test-id=login] input").setValue(validUserInfo.getLogin());
        $("[data-test-id=password] input").setValue("asdfvcxz");
        $("button[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(visible);

    }

}


