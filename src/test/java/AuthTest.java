import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.UserInfo;

import java.util.Locale;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.Registration.registeredUser;


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


}
