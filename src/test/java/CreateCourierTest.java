import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pojo.CourierCreate;
import pojo.CourierLogin;
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
        CourierCreate courier = new CourierCreate("ninja_lera_2026", "1234", "Lera");

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
        CourierCreate courier = new CourierCreate("duplicate_ninja", "1234", "Ivan");
        courierClient.create(courier);

        // Попытка создать такого же
        courierClient.create(courier)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));

        // Получаем ID первого, чтобы удалить его
        courierId = courierClient.login(new CourierLogin("duplicate_ninja", "1234"))
                .extract().as(LoginResponse.class).getId();
    }

    @Test
    @Description("Чтобы создать курьера, нужно передать все обязательные поля. Если поля нет — ошибка 400")
    public void shouldReturnErrorWhenMissingPassword() {
        CourierCreate courier = new CourierCreate();
        courier.setLogin("ninja_no_pass");
        // Пароль не устанавливаем

        courierClient.create(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Если создать пользователя с логином, который уже есть, возвращается ошибка 409")
    public void shouldReturnErrorWhenLoginExists() {
        CourierCreate courier1 = new CourierCreate("same_login", "111", "First");
        CourierCreate courier2 = new CourierCreate("same_login", "222", "Second");

        courierClient.create(courier1);
        courierClient.create(courier2)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));

        courierId = courierClient.login(new CourierLogin("same_login", "111"))
                .extract().as(LoginResponse.class).getId();
    }
}