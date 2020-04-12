package test.maksim.video_rental.infrastructure.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.maksim.video_rental.infrastructure.movie.entity.MovieEntity;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
}
