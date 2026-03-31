import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.CourierCreate;
import pojo.CourierLogin;
import pojo.LoginResponse;
import client.CourierClient;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    private CourierClient courierClient;
    private int courierId;
    private final String login = "auth_ninja_lera";
    private final String password = "password123";

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        // Создаем курьера перед тестом авторизации
        courierClient.create(new CourierCreate(login, password, "Lera"));
    }

    @AfterEach
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @Description("Курьер может авторизоваться. Успешный запрос возвращает id")
    public void courierCanLogin() {
        CourierLogin credentials = new CourierLogin(login, password);

        LoginResponse response = courierClient.login(credentials)
                .statusCode(200)
                .body("id", notNullValue())
                .extract().as(LoginResponse.class);

        courierId = response.getId();
    }

    @Test
    @Description("Система вернёт ошибку, если неправильно указать пароль")
    public void shouldReturnErrorWithWrongPassword() {
        CourierLogin credentials = new CourierLogin(login, "wrong_pass");

        courierClient.login(credentials)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @Description("Если какого-то поля (логина) нет, запрос возвращает ошибку 400")
    public void shouldReturnErrorWhenLoginIsNull() {
        CourierLogin credentials = new CourierLogin(null, password);

        courierClient.login(credentials)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @Description("Ошибка при авторизации под несуществующим пользователем")
    public void shouldReturnErrorForNonExistentUser() {
        CourierLogin credentials = new CourierLogin("ghost_ninja_123987", "some_pass");

        courierClient.login(credentials)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }
}