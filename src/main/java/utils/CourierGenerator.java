package utils;

import pojo.CourierCreate;
import java.util.UUID;

public class CourierGenerator {

    public static CourierCreate getRandomCourier() {
        String uniquePart = UUID.randomUUID().toString().substring(0, 8);
        return new CourierCreate(
                "ninja_" + uniquePart,
                "pass_" + uniquePart,
                "Lera_" + uniquePart
        );
    }
}