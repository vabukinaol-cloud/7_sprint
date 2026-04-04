import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pojo.CourierCreate;
import pojo.CourierLogin;
import utils.CourierGenerator;
import pojo.LoginResponse;
import client.CourierClient;

import static org.hamcrest.Matchers.is;

@ExtendWith(AllureJunit5.class)
public class CreateCourierTest {
    private CourierClient courierClient;
    private int courierId;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
    }

    @AfterEach
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @Description("Курьера можно создать. Успешный запрос возвращает ok: true и код 201")
    public void courierCanBeCreated() {
        CourierCreate courier = CourierGenerator.getRandomCourier();

        courierClient.create(courier)
                .statusCode(201)
                .body("ok", is(true));

        // Авторизуемся, чтобы получить ID для удаления
        courierId = courierClient.login(new CourierLogin(courier.getLogin(), courier.getPassword()))
                .extract().as(LoginResponse.class).getId();
    }

    @Test
    @Description("Нельзя создать двух одинаковых курьеров. Если логин уже есть, возвращается ошибка 409")
    public void cannotCreateDuplicateCourier() {
        CourierCreate courier = CourierGenerator.getRandomCourier();
        courierClient.create(courier);

        // Попытка создать такого же
        courierClient.create(courier)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));

        courierId = courierClient.login(new CourierLogin(courier.getLogin(), courier.getPassword()))
                .extract().as(LoginResponse.class).getId();
    }

    @Test
    @Description("Чтобы создать курьера, нужно передать все обязательные поля. Если поля нет — ошибка 400")
    public void shouldReturnErrorWhenMissingPassword() {
        CourierCreate courier = new CourierCreate();
        courier.setLogin(CourierGenerator.getRandomCourier().getLogin());
        courierClient.create(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
}