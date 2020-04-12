package test.maksim.video_rental.application.order.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import test.maksim.video_rental.domain.order.request.RentingRequestParams;
import test.maksim.video_rental.domain.order.request.ReturningRequestParams;
import test.maksim.video_rental.domain.order.result.RentingResult;
import test.maksim.video_rental.domain.order.result.ReturningResult;
import test.maksim.video_rental.domain.order.service.OrderService;

@Api
@Slf4j
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/customers/{customerId}/renting")
    public RentingResult rentMovies(@PathVariable Integer customerId,
                                    @RequestBody RentingRequestParams params) {
        log.info("Customer {} is renting: {}", customerId, params);
        return service.rentMovies(customerId, params);
    }

    @PutMapping("/customers/{customerId}/returning")
    public ReturningResult returnMovies(@PathVariable Integer customerId,
                                        @RequestBody ReturningRequestParams params) {
        log.info("Customer {} is returning: {}", customerId, params);
        return service.returnMovies(customerId, params);
    }
}
