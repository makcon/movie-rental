package test.maksim.video_rental.infrastructure.customer.converter;

import org.springframework.stereotype.Component;
import test.maksim.video_rental.domain.customer.model.Customer;
import test.maksim.video_rental.infrastructure.customer.entity.CustomerEntity;

@Component
public class CustomerEntityConverter {

    public Customer toModel(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .bonusPoints(entity.getBonusPoints())
                .build();
    }

    public CustomerEntity toEntity(Customer model) {
        return CustomerEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .bonusPoints(model.getBonusPoints())
                .build();
    }
}
