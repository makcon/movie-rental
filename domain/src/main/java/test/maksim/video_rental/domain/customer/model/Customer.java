package test.maksim.video_rental.domain.customer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

    private final Integer id;
    private final String name;
    private Integer bonusPoints;
}
