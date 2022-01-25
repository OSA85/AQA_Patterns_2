import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.UserInfo;

import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.Registration.generateUser;
import static ru.netology.DataGenerator.Registration.registeredUser;
import static ru.netology.DataGenerator.getLoginFaker;
import static ru.netology.DataGenerator.getPasswordFaker;


public class AuthTest {

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true; //открытое окно браузера
        open("http://localhost:9999");
    }


    @Test
    public void shouldSendFullFormWithActiveUser() {
        UserInfo registeredUser = registeredUser ("active"); // передача статуса пользователя
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(byText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    public void shouldSendFullFormWithBlockedUser() {
        UserInfo blockedUser = registeredUser ("blocked"); // передача статуса пользователя
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible).shouldHave(exactText("Ошибка" +
                " Ошибка! Пользователь заблокирован")).click();
    }

    @Test
    public void shouldSendFullFormWithUnregisteredUser() {
        UserInfo unregisteredUser = generateUser ("active"); // передача статуса пользователя
        $("[data-test-id='login'] input").setValue(unregisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(unregisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible).shouldHave(exactText("Ошибка" +
                " Ошибка! Неверно указан логин или пароль")).click();
    }

    @Test
    public void shouldSendFullFormWithRegisteredUserLoginError() {
        UserInfo registeredUser =  registeredUser ("active"); // передача статуса пользователя
        String loginError = getLoginFaker();
        $("[data-test-id='login'] input").setValue(loginError);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible).shouldHave(exactText("Ошибка" +
                " Ошибка! Неверно указан логин или пароль")).click();
    }

    @Test
    public void shouldSendFullFormWithRegisteredUserPasswordError() {
        UserInfo registeredUser =  registeredUser ("active"); // передача статуса пользователя
        String passwordFaker = getPasswordFaker();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(passwordFaker);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible).shouldHave(exactText("Ошибка" +
                " Ошибка! Неверно указан логин или пароль")).click();
    }

}
