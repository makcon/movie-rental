package test.maksim.video_rental.infrastructure.movie.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import test.maksim.video_rental.domain.movie.model.Movie;
import test.maksim.video_rental.domain.movie.port.MovieRepositoryPort;
import test.maksim.video_rental.infrastructure.movie.converter.MovieEntityConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MovieRepositoryAdapter implements MovieRepositoryPort {

    private final MovieRepository repository;
    private final MovieEntityConverter converter;

    @Override
    public List<Movie> fetchAll() {
        return repository.findAll().stream()
                .map(converter::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> fetchByIds(List<Integer> ids) {
        return repository.findAllById(ids).stream()
                .map(converter::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Movie> fetchById(Integer id) {
        return repository.findById(id)
                .map(converter::toModel);
    }
}
