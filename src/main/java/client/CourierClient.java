package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.CourierCreate;
import pojo.CourierLogin;

import static io.restassured.RestAssured.given;

public class CourierClient {

    // Базовый URL системы
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    // Путь к ручке курьера
    private static final String COURIER_PATH = "/api/v1/courier";

    @Step("Создание курьера {courier.login}")
    public ValidatableResponse create(CourierCreate courier) {
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Логин курьера {credentials.login}")
    public ValidatableResponse login(CourierLogin credentials) {
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(credentials)
                .when()
                .post(COURIER_PATH + "/login")
                .then().log().all();
    }

    @Step("Удаление курьера с id {id}")
    public ValidatableResponse delete(int id) {
        return given().log().all()
                .baseUri(BASE_URL)
                .when()
                .delete(COURIER_PATH + "/" + id)
                .then().log().all();
    }
}