package test.maksim.video_rental.domain.order.builder;

import org.junit.jupiter.api.Test;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.domain.order.result.RentingResult.OrderData;

import java.time.Instant;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class RentingResultBuilderShould {

    private static final Integer GIVEN_ORDER_1_ID = new Random().nextInt();
    private static final Integer GIVEN_ORDER_2_ID = new Random().nextInt();
    private static final Integer GIVEN_MOVIE_1_ID = new Random().nextInt();
    private static final Integer GIVEN_MOVIE_2_ID = new Random().nextInt();
    private static final Integer GIVEN_ORDER_1_PRICE = new Random().nextInt();
    private static final Integer GIVEN_ORDER_2_PRICE = new Random().nextInt();
    private static final Instant GIVEN_ORDER_1_EXPIRES_AT = Instant.ofEpochMilli(new Random().nextInt());
    private static final Instant GIVEN_ORDER_2_EXPIRES_AT = Instant.ofEpochMilli(new Random().nextInt());

    private static final Order GIVEN_ORDER_1 = Order.builder()
            .id(GIVEN_ORDER_1_ID)
            .movieId(GIVEN_MOVIE_1_ID)
            .expiresAt(GIVEN_ORDER_1_EXPIRES_AT)
            .price(GIVEN_ORDER_1_PRICE)
            .build();
    private static final Order GIVEN_ORDER_2 = Order.builder()
            .id(GIVEN_ORDER_2_ID)
            .movieId(GIVEN_MOVIE_2_ID)
            .expiresAt(GIVEN_ORDER_2_EXPIRES_AT)
            .price(GIVEN_ORDER_2_PRICE)
            .build();

    private static final OrderData EXPECTED_ORDER_DATA_1 = OrderData.builder()
            .orderId(GIVEN_ORDER_1_ID)
            .movieId(GIVEN_MOVIE_1_ID)
            .expiresAt(GIVEN_ORDER_1_EXPIRES_AT)
            .price(GIVEN_ORDER_1_PRICE)
            .build();
    private static final OrderData EXPECTED_ORDER_DATA_2 = OrderData.builder()
            .orderId(GIVEN_ORDER_2_ID)
            .movieId(GIVEN_MOVIE_2_ID)
            .expiresAt(GIVEN_ORDER_2_EXPIRES_AT)
            .price(GIVEN_ORDER_2_PRICE)
            .build();

    private final RentingResultBuilder builder = new RentingResultBuilder();

    @Test
    void build_result_from_orders() {
        // when
        var result = builder.build(List.of(GIVEN_ORDER_1, GIVEN_ORDER_2));

        // then
        assertThat(result.getTotalPrice()).isEqualTo(GIVEN_ORDER_1_PRICE + GIVEN_ORDER_2_PRICE);
        assertThat(result.getCreatedOrders()).isEqualTo(List.of(EXPECTED_ORDER_DATA_1, EXPECTED_ORDER_DATA_2));
    }
}