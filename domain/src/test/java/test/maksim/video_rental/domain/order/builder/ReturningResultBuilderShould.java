package test.maksim.video_rental.domain.order.builder;

import org.junit.jupiter.api.Test;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.domain.order.result.ReturningResult.OrderData;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class ReturningResultBuilderShould {

    private static final Integer GIVEN_ORDER_1_ID = new Random().nextInt();
    private static final Integer GIVEN_ORDER_2_ID = new Random().nextInt();
    private static final Integer GIVEN_MOVIE_1_ID = new Random().nextInt();
    private static final Integer GIVEN_MOVIE_2_ID = new Random().nextInt();
    private static final Integer GIVEN_OVERDUE_PRICE_1_ID = new Random().nextInt();
    private static final Integer GIVEN_OVERDUE_PRICE_2_ID = new Random().nextInt();

    private static final Order GIVEN_ORDER_1 = Order.builder()
            .id(GIVEN_ORDER_1_ID)
            .movieId(GIVEN_MOVIE_1_ID)
            .overduePrice(GIVEN_OVERDUE_PRICE_1_ID)
            .build();
    private static final Order GIVEN_ORDER_2 = Order.builder()
            .id(GIVEN_ORDER_2_ID)
            .movieId(GIVEN_MOVIE_2_ID)
            .overduePrice(GIVEN_OVERDUE_PRICE_2_ID)
            .build();

    private static final OrderData EXPECTED_ORDER_DATA_1 = OrderData.builder()
            .orderId(GIVEN_ORDER_1_ID)
            .movieId(GIVEN_MOVIE_1_ID)
            .overduePrice(GIVEN_OVERDUE_PRICE_1_ID)
            .build();
    private static final OrderData EXPECTED_ORDER_DATA_2 = OrderData.builder()
            .orderId(GIVEN_ORDER_2_ID)
            .movieId(GIVEN_MOVIE_2_ID)
            .overduePrice(GIVEN_OVERDUE_PRICE_2_ID)
            .build();

    private final ReturningResultBuilder builder = new ReturningResultBuilder();

    @Test
    void build_result_from_orders() {
        // when
        var result = builder.build(List.of(GIVEN_ORDER_1, GIVEN_ORDER_2));

        // then
        assertThat(result.getTotalOverduePrice()).isEqualTo(GIVEN_OVERDUE_PRICE_1_ID + GIVEN_OVERDUE_PRICE_2_ID);
        assertThat(result.getClosedOrders()).isEqualTo(List.of(EXPECTED_ORDER_DATA_1, EXPECTED_ORDER_DATA_2));
    }
}