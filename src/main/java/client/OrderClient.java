package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.OrderCreate;
import utils.Config;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse create(OrderCreate order) {
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(Config.BASE_URL)
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrdersList() {
        return given().log().all()
                .baseUri(Config.BASE_URL)
                .when()
                .get(ORDERS_PATH)
                .then().log().all();
    }
}