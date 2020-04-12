package test.maksim.video_rental.domain.movie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.maksim.video_rental.domain.movie.exception.MovieNotFoundException;
import test.maksim.video_rental.domain.movie.model.Movie;
import test.maksim.video_rental.domain.movie.port.MovieRepositoryPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepositoryPort repository;

    public List<Movie> fetchAll() {
        return repository.fetchAll();
    }

    public List<Movie> fetchByIds(List<Integer> ids) {
        return repository.fetchByIds(ids);
    }

    public Movie fetchById(Integer id) {
        return repository.fetchById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }
}
