package test.maksim.video_rental.domain.order.service;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import test.maksim.video_rental.domain.movie.model.Movie;
import test.maksim.video_rental.domain.movie.service.MovieService;
import test.maksim.video_rental.domain.order.calculator.RentingDataCalculator;
import test.maksim.video_rental.domain.order.event.OrdersCreatedEvent;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.domain.order.port.OrderRepositoryPort;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderCreationService {

    private final MovieService movieService;
    private final RentingDataCalculator rentingDataCalculator;
    private final OrderRepositoryPort orderRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final Clock clock;

    List<Order> create(Integer customerId, List<Integer> movieIds, Integer rentingDays) {
        var orders = movieService.fetchByIds(movieIds).stream()
                .map(it -> createOne(it, rentingDays, customerId))
                .collect(Collectors.toList());

        return saveAndNotify(customerId, orders);
    }

    private Order createOne(Movie movie, Integer rentingDays, Integer customerId) {
        var createdAt = Instant.now(clock);

        return Order.builder()
                .customerId(customerId)
                .movieId(movie.getId())
                .createdAt(createdAt)
                .price(rentingDataCalculator.calculatePrice(movie, rentingDays))
                .expiresAt(rentingDataCalculator.calculateExpiresAt(movie, createdAt, rentingDays))
                .overduePrice(0)
                .build();
    }

    private List<Order> saveAndNotify(Integer customerId, List<Order> orders) {
        orders = orderRepository.save(orders);

        eventPublisher.publishEvent(
                OrdersCreatedEvent.builder()
                        .customerId(customerId)
                        .orders(orders)
                        .build()
        );

        return orders;
    }
}
