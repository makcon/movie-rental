package test.maksim.video_rental.infrastructure.order.converter;

import org.springframework.stereotype.Component;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.infrastructure.order.entity.OrderEntity;

@Component
public class OrderEntityConverter {

    public Order toModel(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .customerId(entity.getCustomerId())
                .movieId(entity.getMovieId())
                .createdAt(entity.getCreatedAt())
                .expiresAt(entity.getExpiresAt())
                .closedAt(entity.getClosedAt())
                .price(entity.getPrice())
                .overduePrice(entity.getOverduePrice())
                .build();
    }

    public OrderEntity toEntity(Order model) {
        return OrderEntity.builder()
                .id(model.getId())
                .customerId(model.getCustomerId())
                .movieId(model.getMovieId())
                .createdAt(model.getCreatedAt())
                .expiresAt(model.getExpiresAt())
                .closedAt(model.getClosedAt())
                .price(model.getPrice())
                .overduePrice(model.getOverduePrice())
                .build();
    }
}
