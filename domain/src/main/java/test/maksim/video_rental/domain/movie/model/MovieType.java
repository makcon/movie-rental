package test.maksim.video_rental.domain.movie.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieType {

    private final String name;
    private final Integer rentingDays;
    private final Integer bonusPoints;
}
