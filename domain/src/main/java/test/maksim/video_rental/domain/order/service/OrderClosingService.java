package test.maksim.video_rental.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.maksim.video_rental.domain.order.calculator.SurchargeCalculator;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.domain.order.port.OrderRepositoryPort;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderClosingService {

    private final OrderRepositoryPort orderRepository;
    private final SurchargeCalculator surchargeCalculator;
    private final Clock clock;

    List<Order> close(Integer customerId, List<Integer> orderIds) {
        var orders = orderRepository.fetchActiveByIdsForCustomer(orderIds, customerId);

        var closedAt = Instant.now(clock);
        orders.forEach(it -> closeOrder(it, closedAt));

        return orderRepository.save(orders);
    }

    private void closeOrder(Order order, Instant closedAt) {
        order.setClosedAt(closedAt);
        order.setOverduePrice(surchargeCalculator.calculate(order, closedAt));
    }
}
