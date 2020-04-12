package test.maksim.video_rental.infrastructure.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private Integer movieId;
    private Integer customerId;
    private Instant createdAt;
    private Instant expiresAt;
    private Integer price;
    private Integer overduePrice;
    private Instant closedAt;
}
