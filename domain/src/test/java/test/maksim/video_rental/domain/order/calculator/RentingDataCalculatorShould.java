package test.maksim.video_rental.domain.order.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import test.maksim.video_rental.domain.movie.model.Movie;
import test.maksim.video_rental.domain.movie.model.MoviePrice;
import test.maksim.video_rental.domain.movie.model.MovieType;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;

class RentingDataCalculatorShould {

    private static final Integer GIVEN_MOVIE_PRICE = 1;
    private static final Integer GIVEN_MOVIE_RENTING_DAYS = 3;
    private static final Movie GIVEN_MOVIE = Movie.builder()
            .price(MoviePrice.builder().price(GIVEN_MOVIE_PRICE).build())
            .type(MovieType.builder().rentingDays(GIVEN_MOVIE_RENTING_DAYS).build())
            .build();


    private static final Instant GIVEN_CREATED_AT = Instant.now();

    private final RentingDataCalculator calculator = new RentingDataCalculator();

    @ParameterizedTest
    @ValueSource(ints = {1, 3})
    void return_movie_price_if_renting_days_in_range(int rentingDays) {
        // when
        var price = calculator.calculatePrice(GIVEN_MOVIE, rentingDays);

        // then
        assertThat(price).isEqualTo(GIVEN_MOVIE_PRICE);
    }

    @ParameterizedTest
    @CsvSource(value = {"4:1", "6:3"}, delimiter = ':')
    void return_movie_price_and_surcharge_if_renting_days_are_more(int rentingDays, int surchargeMultiplier) {
        // when
        var price = calculator.calculatePrice(GIVEN_MOVIE, rentingDays);

        // then
        assertThat(price).isEqualTo(GIVEN_MOVIE_PRICE + GIVEN_MOVIE_PRICE * surchargeMultiplier);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3})
    void return_movie_expiresAt_if_requested_renting_days_are_less(int rentingDays) {
        // when
        var expiresAt = calculator.calculateExpiresAt(GIVEN_MOVIE, GIVEN_CREATED_AT, rentingDays);

        // then
        assertThat(expiresAt).isEqualTo(GIVEN_CREATED_AT.plus(GIVEN_MOVIE_RENTING_DAYS, DAYS));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4})
    void return_requested_expiresAt_if_requested_renting_days_are_less(int rentingDays) {
        // when
        var expiresAt = calculator.calculateExpiresAt(GIVEN_MOVIE, GIVEN_CREATED_AT, rentingDays);

        // then
        assertThat(expiresAt).isEqualTo(GIVEN_CREATED_AT.plus(rentingDays, DAYS));
    }
}