package test.maksim.video_rental.domain.movie.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoviePrice {

    private final String category;
    private final Integer price;
}
