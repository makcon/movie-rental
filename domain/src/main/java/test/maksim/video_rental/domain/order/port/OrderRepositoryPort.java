package test.maksim.video_rental.domain.order.port;

import test.maksim.video_rental.domain.order.model.Order;

import java.util.List;

public interface OrderRepositoryPort {

    List<Order> save(List<Order> orders);

    List<Order> fetchActiveByIdsForCustomer(List<Integer> ids, Integer customerId);
}
