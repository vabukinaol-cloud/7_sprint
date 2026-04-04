
import client.OrderClient;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pojo.OrderCreate;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(AllureJunit5.class)
public class CreateOrderParametrizedTest {

    private OrderClient orderClient;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();
    }

    static Stream<List<String>> colorData() {
        return Stream.of(
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "GREY"),
                List.of()
        );
    }

    @ParameterizedTest
    @MethodSource("colorData")
    public void checkOrderCreationWithDifferentColors(List<String> colors) {
        OrderCreate order = new OrderCreate(colors);

        orderClient.create(order)
                .statusCode(201)
                .body("track", notNullValue());
    }
}