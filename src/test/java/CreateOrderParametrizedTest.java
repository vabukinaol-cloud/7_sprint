

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pojo.OrderCreate;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderParametrizedTest {

    // Источник данных для цветов
    static Stream<List<String>> colorData() {
        return Stream.of(
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "GREY"),
                List.of() // Без цвета
        );
    }

    @ParameterizedTest
    @MethodSource("colorData")
    @Step("Отправка POST запроса на создание заказа")
    public void checkOrderCreationWithDifferentColors(List<String> colors) {
        OrderCreate order = new OrderCreate(colors);

        RestAssured.given().log().all()
                .header("Content-type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().log().all()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
