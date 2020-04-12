package test.maksim.video_rental.infrastructure.movie.converter;

import org.junit.jupiter.api.Test;
import test.maksim.video_rental.infrastructure.movie.entity.MovieEntity;
import test.maksim.video_rental.infrastructure.movie.entity.MoviePriceEntity;
import test.maksim.video_rental.infrastructure.movie.entity.MovieTypeEntity;

import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MovieEntityConverterShould {

    private static final Integer GIVEN_ID = new Random().nextInt();
    private static final String GIVEN_NAME = UUID.randomUUID().toString();

    private static final String GIVEN_TYPE_NAME = UUID.randomUUID().toString();
    private static final Integer GIVEN_RENTING_DAYS = new Random().nextInt();
    private static final Integer GIVEN_BONUS_POINTS = new Random().nextInt();

    private static final Integer GIVEN_PRICE = new Random().nextInt();
    private static final String GIVEN_PRICE_CATEGORY = UUID.randomUUID().toString();

    private final MovieEntityConverter converter = new MovieEntityConverter();

    @Test
    void convert_to_model() {
        // given
        var givenEntity = MovieEntity.builder()
                .id(GIVEN_ID)
                .name(GIVEN_NAME)
                .price(
                        MoviePriceEntity.builder()
                                .category(GIVEN_PRICE_CATEGORY)
                                .price(GIVEN_PRICE)
                                .build()
                )
                .type(
                        MovieTypeEntity.builder()
                                .name(GIVEN_TYPE_NAME)
                                .rentingDays(GIVEN_RENTING_DAYS)
                                .bonusPoints(GIVEN_BONUS_POINTS)
                                .build()
                )
                .build();

        // when
        var model = converter.toModel(givenEntity);

        // then
        assertThat(model.getId()).isEqualTo(GIVEN_ID);
        assertThat(model.getName()).isEqualTo(GIVEN_NAME);
        assertThat(model.getType().getName()).isEqualTo(GIVEN_TYPE_NAME);
        assertThat(model.getType().getRentingDays()).isEqualTo(GIVEN_RENTING_DAYS);
        assertThat(model.getType().getBonusPoints()).isEqualTo(GIVEN_BONUS_POINTS);
        assertThat(model.getPrice().getCategory()).isEqualTo(GIVEN_PRICE_CATEGORY);
        assertThat(model.getPrice().getPrice()).isEqualTo(GIVEN_PRICE);
    }
}