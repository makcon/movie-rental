package test.maksim.video_rental.domain.movie.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movie {

    private final Integer id;
    private final String name;
    private final MovieType type;
    private final MoviePrice price;
}
