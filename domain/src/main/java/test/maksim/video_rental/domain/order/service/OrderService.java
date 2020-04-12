package test.maksim.video_rental.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.maksim.video_rental.domain.customer.service.CustomerService;
import test.maksim.video_rental.domain.order.builder.RentingResultBuilder;
import test.maksim.video_rental.domain.order.builder.ReturningResultBuilder;
import test.maksim.video_rental.domain.order.request.RentingRequestParams;
import test.maksim.video_rental.domain.order.request.ReturningRequestParams;
import test.maksim.video_rental.domain.order.result.RentingResult;
import test.maksim.video_rental.domain.order.result.ReturningResult;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerService customerService;
    private final OrderCreationService orderCreationService;
    private final OrderClosingService orderClosingService;
    private final RentingResultBuilder rentingResultBuilder;
    private final ReturningResultBuilder returningResultBuilder;

    public RentingResult rentMovies(Integer customerId, RentingRequestParams params) {
        customerService.verifyIfCustomerExists(customerId);

        var orders = orderCreationService.create(
                customerId,
                params.getMovieIds(),
                params.getRentingDays()
        );

        return rentingResultBuilder.build(orders);
    }

    public ReturningResult returnMovies(Integer customerId, ReturningRequestParams params) {
        customerService.verifyIfCustomerExists(customerId);

        var orders = orderClosingService.close(customerId, params.getOrderIds());

        return returningResultBuilder.build(orders);
    }
}
