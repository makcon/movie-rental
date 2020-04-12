package test.maksim.video_rental.domain.order.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class Order {

    private final Integer id;
    private final Integer customerId;
    private final Integer movieId;
    private final Instant createdAt;
    private final Instant expiresAt;
    private final Integer price;
    private Integer overduePrice;
    private Instant closedAt;
}
