package test.maksim.video_rental.domain.order.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReturningResult {

    private final Integer totalOverduePrice;
    private final List<OrderData> closedOrders;

    @Data
    @Builder
    public static class OrderData {

        private final Integer orderId;
        private final Integer movieId;
        private final Integer overduePrice;
    }
}
