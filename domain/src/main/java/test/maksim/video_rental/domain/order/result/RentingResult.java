package test.maksim.video_rental.domain.order.result;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class RentingResult {

    private final Integer totalPrice;
    private final List<OrderData> createdOrders;

    @Data
    @Builder
    public static class OrderData {

        private final Integer orderId;
        private final Integer movieId;
        private final Instant expiresAt;
        private final Integer price;
    }
}
