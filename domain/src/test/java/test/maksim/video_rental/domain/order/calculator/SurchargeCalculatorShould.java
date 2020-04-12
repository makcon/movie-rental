package test.maksim.video_rental.domain.order.calculator;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.maksim.video_rental.domain.movie.model.Movie;
import test.maksim.video_rental.domain.movie.model.MoviePrice;
import test.maksim.video_rental.domain.movie.service.MovieService;
import test.maksim.video_rental.domain.order.model.Order;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.HOURS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurchargeCalculatorShould {

    private static final Integer GIVEN_MOVIE_ID = 1;
    private static final Integer GIVEN_MOVIE_PRICE = 2;
    private static final Movie GIVEN_MOVIE = Movie.builder()
            .price(MoviePrice.builder().price(GIVEN_MOVIE_PRICE).build())
            .build();

    private static final Instant GIVEN_EXPIRES_AT = Instant.now();
    private static final Order GIVEN_ORDER = Order.builder()
            .movieId(GIVEN_MOVIE_ID)
            .expiresAt(GIVEN_EXPIRES_AT)
            .build();


    @InjectMocks
    private SurchargeCalculator calculator;

    @Mock
    private MovieService movieService;

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void return_0_if_order_closed_in_time(int minusSeconds) {
        // given
        var givenClosedAt = GIVEN_EXPIRES_AT.minusSeconds(minusSeconds);

        // when
        var subcharge = calculator.calculate(GIVEN_ORDER, givenClosedAt);

        // then
        assertThat(subcharge).isZero();
        verify(movieService, never()).fetchById(anyInt());
    }

    @ParameterizedTest
    @CsvSource(value = {"1:1", "25:2", "49:3"}, delimiter = ':')
    void return_subcharge_if_order_is_not_closed_in_time(int plusHours, int expectedDays) {
        // given
        var givenClosedAt = GIVEN_EXPIRES_AT.plus(plusHours, HOURS);
        when(movieService.fetchById(anyInt())).thenReturn(GIVEN_MOVIE);

        // when
        var subcharge = calculator.calculate(GIVEN_ORDER, givenClosedAt);

        // then
        assertThat(subcharge).isEqualTo(GIVEN_MOVIE_PRICE * expectedDays);
        verify(movieService).fetchById(GIVEN_MOVIE_ID);
    }
}