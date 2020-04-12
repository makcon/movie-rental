package test.maksim.video_rental.infrastructure.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.maksim.video_rental.infrastructure.order.entity.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findAllByIdInAndCustomerIdAndClosedAtIsNull(List<Integer> ids, Integer customerId);
}
