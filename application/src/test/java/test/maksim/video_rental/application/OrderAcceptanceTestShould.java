package test.maksim.video_rental.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.maksim.video_rental.domain.customer.exception.CustomerNotFoundException;
import test.maksim.video_rental.domain.order.request.RentingRequestParams;
import test.maksim.video_rental.domain.order.request.ReturningRequestParams;
import test.maksim.video_rental.domain.order.service.OrderService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class OrderAcceptanceTestShould {

    private static final Integer GIVEN_CUSTOMER_1_ID = 1;
    private static final Integer GIVEN_CUSTOMER_2_ID = 2;
    private static final Integer GIVEN_UNKNOWN_CUSTOMER_ID = 100;

    private static final Integer PREMIUM_MOVIE_ID = 1;
    private static final Integer BASIC_MOVIE_ID = 2;
    private static final Integer GIVEN_UNKNOWN_MOVIE_ID = 100;
    private static final Integer PREMIUM_MOVIE_PRICE = 3;
    private static final Integer BASIC_MOVIE_PRICE = 1;

    private static final Integer GIVEN_RENTING_1_DAY = 1;

    @Autowired
    private OrderService orderService;

    @Test
    void thrown_not_found_exception_when_customer_not_found_to_rent_movies() {
        // when
        assertThrows(CustomerNotFoundException.class,
                () -> orderService.rentMovies(GIVEN_UNKNOWN_CUSTOMER_ID, mock(RentingRequestParams.class))
        );
    }

    @Test
    void rent_1_movie_for_1_day() {
        // given
        var givenParams = createRentingParams(BASIC_MOVIE_ID);

        // when
        var result = orderService.rentMovies(GIVEN_CUSTOMER_1_ID, givenParams);

        // then
        assertThat(result.getTotalPrice()).isEqualTo(BASIC_MOVIE_PRICE);
        assertThat(result.getCreatedOrders()).hasSize(1);
        assertThat(result.getCreatedOrders().get(0).getOrderId()).isNotNull();
        assertThat(result.getCreatedOrders().get(0).getExpiresAt()).isNotNull();
        assertThat(result.getCreatedOrders().get(0).getMovieId()).isEqualTo(BASIC_MOVIE_ID);
        assertThat(result.getCreatedOrders().get(0).getPrice()).isEqualTo(BASIC_MOVIE_PRICE);

    }

    @Test
    void return_sum_of_premium_and_basic_prices_when_rent_for_1_day() {
        // given
        var givenParams = createRentingParams(PREMIUM_MOVIE_ID, BASIC_MOVIE_ID);

        // when
        var result = orderService.rentMovies(GIVEN_CUSTOMER_1_ID, givenParams);

        // then
        assertThat(result.getCreatedOrders()).hasSize(2);
        assertThat(result.getTotalPrice()).isEqualTo(PREMIUM_MOVIE_PRICE + BASIC_MOVIE_PRICE);
    }

    @Test
    void rent_only_found_movies() {
        // given
        var givenParams = createRentingParams(PREMIUM_MOVIE_ID, GIVEN_UNKNOWN_MOVIE_ID);

        // when
        var result = orderService.rentMovies(GIVEN_CUSTOMER_1_ID, givenParams);

        // then
        assertThat(result.getCreatedOrders()).hasSize(1);
        assertThat(result.getCreatedOrders().get(0).getMovieId()).isEqualTo(PREMIUM_MOVIE_ID);
    }

    @Test
    void thrown_not_found_exception_when_customer_not_found_to_return_movies() {
        // when
        assertThrows(CustomerNotFoundException.class,
                () -> orderService.returnMovies(GIVEN_UNKNOWN_CUSTOMER_ID, mock(ReturningRequestParams.class))
        );
    }

    @Test
    void return_only_orders_belong_to_customer() {
        // given
        var orderId1 = rentMovie(GIVEN_CUSTOMER_1_ID, PREMIUM_MOVIE_ID);
        var orderId2 = rentMovie(GIVEN_CUSTOMER_2_ID, BASIC_MOVIE_ID);
        var givenParams = createReturningParams(orderId1, orderId2);

        // when
        var result = orderService.returnMovies(GIVEN_CUSTOMER_1_ID, givenParams);

        // then
        assertThat(result.getTotalOverduePrice()).isZero();
        assertThat(result.getClosedOrders()).hasSize(1);
        assertThat(result.getClosedOrders().get(0).getOrderId()).isEqualTo(orderId1);
        assertThat(result.getClosedOrders().get(0).getMovieId()).isEqualTo(PREMIUM_MOVIE_ID);
        assertThat(result.getClosedOrders().get(0).getOverduePrice()).isZero();
    }

    @Test
    void not_return_already_closed_order() {
        // given
        var orderId = rentMovie(GIVEN_CUSTOMER_1_ID, PREMIUM_MOVIE_ID);
        var givenParams = createReturningParams(orderId);
        orderService.returnMovies(GIVEN_CUSTOMER_1_ID, givenParams);

        // when
        var result = orderService.returnMovies(GIVEN_CUSTOMER_1_ID, givenParams);

        // then
        assertThat(result.getClosedOrders()).isEmpty();
    }

    private RentingRequestParams createRentingParams(Integer... movieIds) {
        return RentingRequestParams.builder()
                .rentingDays(GIVEN_RENTING_1_DAY)
                .movieIds(List.of(movieIds))
                .build();
    }

    private ReturningRequestParams createReturningParams(Integer... orderIds) {
        return ReturningRequestParams.builder()
                .orderIds(List.of(orderIds))
                .build();
    }

    private Integer rentMovie(Integer customerId, Integer movieId) {
        var result = orderService.rentMovies(customerId, createRentingParams(movieId));

        return result.getCreatedOrders().get(0).getOrderId();
    }
}
