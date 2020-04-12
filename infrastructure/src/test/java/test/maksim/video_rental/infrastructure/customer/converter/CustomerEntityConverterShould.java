package test.maksim.video_rental.infrastructure.customer.converter;

import org.junit.jupiter.api.Test;
import test.maksim.video_rental.domain.customer.model.Customer;
import test.maksim.video_rental.infrastructure.customer.entity.CustomerEntity;

import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerEntityConverterShould {

    private static final Integer GIVEN_ID = new Random().nextInt();
    private static final String GIVEN_NAME = UUID.randomUUID().toString();
    private static final Integer GIVEN_BONUS_POINTS = new Random().nextInt();

    private final CustomerEntityConverter converter = new CustomerEntityConverter();

    @Test
    void convert_to_model() {
        // given
        var givenEntity = CustomerEntity.builder()
                .id(GIVEN_ID)
                .name(GIVEN_NAME)
                .bonusPoints(GIVEN_BONUS_POINTS)
                .build();

        // when
        var model = converter.toModel(givenEntity);

        // then
        assertThat(model.getId()).isEqualTo(GIVEN_ID);
        assertThat(model.getName()).isEqualTo(GIVEN_NAME);
        assertThat(model.getBonusPoints()).isEqualTo(GIVEN_BONUS_POINTS);
    }

    @Test
    void convert_to_entity() {
        // given
        var givenModel = Customer.builder()
                .id(GIVEN_ID)
                .name(GIVEN_NAME)
                .bonusPoints(GIVEN_BONUS_POINTS)
                .build();

        // when
        var entity = converter.toEntity(givenModel);

        // then
        assertThat(entity.getId()).isEqualTo(GIVEN_ID);
        assertThat(entity.getName()).isEqualTo(GIVEN_NAME);
        assertThat(entity.getBonusPoints()).isEqualTo(GIVEN_BONUS_POINTS);
    }
}