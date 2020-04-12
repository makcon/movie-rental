package test.maksim.video_rental.domain.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentingRequestParams {

    private List<Integer> movieIds;
    private Integer rentingDays;
}
