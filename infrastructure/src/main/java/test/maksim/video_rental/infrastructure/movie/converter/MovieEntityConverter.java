package test.maksim.video_rental.infrastructure.movie.converter;

import org.springframework.stereotype.Component;
import test.maksim.video_rental.domain.movie.model.Movie;
import test.maksim.video_rental.domain.movie.model.MoviePrice;
import test.maksim.video_rental.domain.movie.model.MovieType;
import test.maksim.video_rental.infrastructure.movie.entity.MovieEntity;
import test.maksim.video_rental.infrastructure.movie.entity.MoviePriceEntity;
import test.maksim.video_rental.infrastructure.movie.entity.MovieTypeEntity;

@Component
public class MovieEntityConverter {

    public Movie toModel(MovieEntity entity) {
        return Movie.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(toPriceModel(entity.getPrice()))
                .type(toTypeModel(entity.getType()))
                .build();
    }

    private MovieType toTypeModel(MovieTypeEntity entity) {
        return MovieType.builder()
                .name(entity.getName())
                .rentingDays(entity.getRentingDays())
                .bonusPoints(entity.getBonusPoints())
                .build();
    }

    private MoviePrice toPriceModel(MoviePriceEntity entity) {
        return MoviePrice.builder()
                .category(entity.getCategory())
                .price(entity.getPrice())
                .build();
    }
}
