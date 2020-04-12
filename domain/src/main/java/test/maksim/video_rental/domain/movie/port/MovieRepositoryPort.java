package test.maksim.video_rental.domain.movie.port;

import test.maksim.video_rental.domain.movie.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepositoryPort {

    List<Movie> fetchAll();

    List<Movie> fetchByIds(List<Integer> ids);

    Optional<Movie> fetchById(Integer id);
}
