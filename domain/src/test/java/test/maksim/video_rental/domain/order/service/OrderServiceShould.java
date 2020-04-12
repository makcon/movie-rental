package test.maksim.video_rental.domain.order.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.maksim.video_rental.domain.customer.exception.CustomerNotFoundException;
import test.maksim.video_rental.domain.customer.service.CustomerService;
import test.maksim.video_rental.domain.order.builder.RentingResultBuilder;
import test.maksim.video_rental.domain.order.builder.ReturningResultBuilder;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.domain.order.request.RentingRequestParams;
import test.maksim.video_rental.domain.order.request.ReturningRequestParams;
import test.maksim.video_rental.domain.order.result.RentingResult;
import test.maksim.video_rental.domain.order.result.ReturningResult;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceShould {

    private static final Integer GIVEN_CUSTOMER_ID = new Random().nextInt();

    @InjectMocks
    private OrderService service;
    
    @Mock
    private CustomerService customerService;
    @Mock
    private OrderCreationService orderCreationService;
    @Mock
    private OrderClosingService orderClosingService;
    @Mock
    private RentingResultBuilder rentingResultBuilder;
    @Mock
    private ReturningResultBuilder returningResultBuilder;

    @Test
    void thrown_not_found_exception_when_customer_not_found_to_rent_movies() {
        // given
        doThrow(CustomerNotFoundException.class)
                .when(customerService)
                .verifyIfCustomerExists(GIVEN_CUSTOMER_ID);

        // when
        assertThrows(
                CustomerNotFoundException.class,
                () -> service.rentMovies(GIVEN_CUSTOMER_ID, mock(RentingRequestParams.class))
        );
    }

    @Test
    void create_orders_when_customer_found_to_rent_movies() {
        // given
        var givenMovieIds = List.of(new Random().nextInt());
        var givenRentingDays = new Random().nextInt();
        var givenRentingParams = RentingRequestParams.builder()
                .movieIds(givenMovieIds)
                .rentingDays(givenRentingDays)
                .build();
        var expectedResult = mock(RentingResult.class);
        var createdOrders = List.of(mock(Order.class));
        when(rentingResultBuilder.build(anyList())).thenReturn(expectedResult);
        when(orderCreationService.create(anyInt(), anyList(), anyInt())).thenReturn(createdOrders);

        // when
        var result = service.rentMovies(GIVEN_CUSTOMER_ID, givenRentingParams);

        // then
        assertThat(result).isEqualTo(expectedResult);
        verify(orderCreationService).create(GIVEN_CUSTOMER_ID, givenMovieIds, givenRentingDays);
        verify(rentingResultBuilder).build(createdOrders);
    }

    @Test
    void thrown_not_found_exception_when_customer_not_found_to_return_movies() {
        // given
        doThrow(CustomerNotFoundException.class)
                .when(customerService)
                .verifyIfCustomerExists(GIVEN_CUSTOMER_ID);

        // when
        assertThrows(
                CustomerNotFoundException.class,
                () -> service.returnMovies(GIVEN_CUSTOMER_ID, mock(ReturningRequestParams.class))
        );
    }

    @Test
    void close_orders_when_customer_found_to_return_movies() {
        // given
        var givenOrderIds = List.of(new Random().nextInt());
        var givenReturningParams = ReturningRequestParams.builder()
                .orderIds(givenOrderIds)
                .build();
        var expectedResult = mock(ReturningResult.class);
        var closedOrders = List.of(mock(Order.class));
        when(returningResultBuilder.build(anyList())).thenReturn(expectedResult);
        when(orderClosingService.close(anyInt(), anyList())).thenReturn(closedOrders);

        // when
        var result = service.returnMovies(GIVEN_CUSTOMER_ID, givenReturningParams);

        // then
        assertThat(result).isEqualTo(expectedResult);
        verify(orderClosingService).close(GIVEN_CUSTOMER_ID, givenOrderIds);
        verify(returningResultBuilder).build(closedOrders);
    }
}