package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;


public class DataGenerator {

    private DataGenerator() {
    }
    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateValidUser() {
            Faker faker = new Faker(new Locale("en"));
            return new UserInfo(faker.name().fullName(), faker.internet().password(), "active");
        }

        public static UserInfo generateBlockedUser() {
            Faker faker = new Faker(new Locale("en"));
            return new UserInfo(faker.name().fullName(), faker.internet().password(), "blocked");
        }
    }

        public static class  SendQuery {
            private static RequestSpecification requestSpec = new RequestSpecBuilder()
                    .setBaseUri("http://localhost")
                    .setPort(9999)
                    .setAccept(ContentType.JSON)
                    .setContentType(ContentType.JSON)
                    .log(LogDetail.ALL)
                    .build();


            static void setUpAll(UserInfo userInfo) {
                // сам запрос
                given() // "дано"
                        .spec(requestSpec) // указываем, какую спецификацию используем
                        .body(userInfo) // передаём в теле объект, который будет преобразован в JSON
                        .when() // "когда"
                        .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                        .then() // "тогда ожидаем"
                        .statusCode(200); // код 200 OK
            }
        }

    }





