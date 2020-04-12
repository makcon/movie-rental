package test.maksim.video_rental.infrastructure.order.converter;

import org.junit.jupiter.api.Test;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.infrastructure.order.entity.OrderEntity;

import java.time.Instant;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class OrderEntityConverterShould {

    private static final Integer GIVEN_ID = new Random().nextInt();
    private static final Integer GIVEN_CUSTOMER_ID = new Random().nextInt();
    private static final Integer GIVEN_MOVIE_ID = new Random().nextInt();
    private static final Instant GIVEN_CREATED_AT = Instant.ofEpochMilli(new Random().nextInt());
    private static final Instant GIVEN_EXPIRES_AT = Instant.ofEpochMilli(new Random().nextInt());
    private static final Instant GIVEN_CLOSED_AT = Instant.ofEpochMilli(new Random().nextInt());
    private static final Integer GIVEN_PRICE = new Random().nextInt();
    private static final Integer GIVEN_OVERDUE_PRICE = new Random().nextInt();

    private final OrderEntityConverter converter = new OrderEntityConverter();

    @Test
    void convert_to_model() {
        // given
        var givenEntity = OrderEntity.builder()
                .id(GIVEN_ID)
                .customerId(GIVEN_CUSTOMER_ID)
                .movieId(GIVEN_MOVIE_ID)
                .createdAt(GIVEN_CREATED_AT)
                .expiresAt(GIVEN_EXPIRES_AT)
                .closedAt(GIVEN_CLOSED_AT)
                .price(GIVEN_PRICE)
                .overduePrice(GIVEN_OVERDUE_PRICE)
                .build();

        // when
        var model = converter.toModel(givenEntity);

        // then
        assertThat(model.getId()).isEqualTo(GIVEN_ID);
        assertThat(model.getCustomerId()).isEqualTo(GIVEN_CUSTOMER_ID);
        assertThat(model.getMovieId()).isEqualTo(GIVEN_MOVIE_ID);
        assertThat(model.getCreatedAt()).isEqualTo(GIVEN_CREATED_AT);
        assertThat(model.getExpiresAt()).isEqualTo(GIVEN_EXPIRES_AT);
        assertThat(model.getClosedAt()).isEqualTo(GIVEN_CLOSED_AT);
        assertThat(model.getPrice()).isEqualTo(GIVEN_PRICE);
        assertThat(model.getOverduePrice()).isEqualTo(GIVEN_OVERDUE_PRICE);
    }

    @Test
    void convert_to_entity() {
        // given
        var givenModel = Order.builder()
                .id(GIVEN_ID)
                .customerId(GIVEN_CUSTOMER_ID)
                .movieId(GIVEN_MOVIE_ID)
                .createdAt(GIVEN_CREATED_AT)
                .expiresAt(GIVEN_EXPIRES_AT)
                .closedAt(GIVEN_CLOSED_AT)
                .price(GIVEN_PRICE)
                .overduePrice(GIVEN_OVERDUE_PRICE)
                .build();

        // when
        var entity = converter.toEntity(givenModel);

        // then
        assertThat(entity.getId()).isEqualTo(GIVEN_ID);
        assertThat(entity.getCustomerId()).isEqualTo(GIVEN_CUSTOMER_ID);
        assertThat(entity.getMovieId()).isEqualTo(GIVEN_MOVIE_ID);
        assertThat(entity.getCreatedAt()).isEqualTo(GIVEN_CREATED_AT);
        assertThat(entity.getExpiresAt()).isEqualTo(GIVEN_EXPIRES_AT);
        assertThat(entity.getClosedAt()).isEqualTo(GIVEN_CLOSED_AT);
        assertThat(entity.getPrice()).isEqualTo(GIVEN_PRICE);
        assertThat(entity.getOverduePrice()).isEqualTo(GIVEN_OVERDUE_PRICE);
    }
}