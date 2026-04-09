
import client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(AllureJunit5.class)
public class GetOrdersTest {

    private OrderClient orderClient;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что в теле ответа возвращается список (массив) заказов")
    public void shouldReturnOrdersList() {
        orderClient.getOrdersList()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders", isA(List.class));
    }
}