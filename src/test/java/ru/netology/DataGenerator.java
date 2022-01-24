package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static void sendRequest (UserInfo info) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new UserInfo( info.getLogin(),
                        info.getPassword(),
                        info.getStatus())) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String getLoginFaker () {
        return faker.name().firstName();
    }

    public static String getPasswordFaker () {
        return faker.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String status) {
            return new UserInfo(getLoginFaker(), getPasswordFaker(), status);
        }

        public static UserInfo registeredUser(String status) {
            UserInfo registeredUser = generateUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }

    }


}
