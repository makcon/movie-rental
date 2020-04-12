package test.maksim.video_rental.infrastructure.order.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.domain.order.port.OrderRepositoryPort;
import test.maksim.video_rental.infrastructure.order.converter.OrderEntityConverter;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderRepository repository;
    private final OrderEntityConverter converter;

    @Override
    public List<Order> save(List<Order> orders) {
        var entities = orders.stream()
                .map(converter::toEntity)
                .collect(Collectors.toList());

        return repository.saveAll(entities).stream()
                .map(converter::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> fetchActiveByIdsForCustomer(List<Integer> ids, Integer customerId) {
        return repository.findAllByIdInAndCustomerIdAndClosedAtIsNull(ids, customerId).stream()
                .map(converter::toModel)
                .collect(Collectors.toList());
    }
}
