import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(AllureJunit5.class)
public class GetOrdersTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что в теле ответа возвращается список (массив) заказов")
    public void shouldReturnOrdersList() {
        checkOrdersListExistence();
    }

    @Step("Запрос списка заказов и проверка структуры ответа")
    private void checkOrdersListExistence() {
        RestAssured.given().log().all()
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .when()
                .get("/api/v1/orders")
                .then().log().all()
                .statusCode(200)
                .body("orders", notNullValue()) // Проверяем, что поле есть
                .body("orders", isA(List.class)); // Проверяем, что это именно список (массив JSON)
    }
}