package test.maksim.video_rental.domain.movie.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.maksim.video_rental.domain.movie.exception.MovieNotFoundException;
import test.maksim.video_rental.domain.movie.model.Movie;
import test.maksim.video_rental.domain.movie.port.MovieRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceShould {

    private static final Integer GIVEN_MOVIE_ID = new Random().nextInt() ;
    private static final Movie STORED_MOVIE = mock(Movie.class);

    @InjectMocks
    private MovieService service;

    @Mock
    private MovieRepositoryPort repository;

    @Test
    void return_all_stored_movies() {
        // given
        when(repository.fetchAll()).thenReturn(List.of(STORED_MOVIE));

        // when
        var movie = service.fetchAll();

        // then
        assertThat(movie).isEqualTo(List.of(STORED_MOVIE));
    }

    @Test
    void return_movies_by_requested_ids() {
        // given
        when(repository.fetchByIds(anyList())).thenReturn(List.of(STORED_MOVIE));

        // when
        var movie = service.fetchByIds(List.of(GIVEN_MOVIE_ID));

        // then
        assertThat(movie).isEqualTo(List.of(STORED_MOVIE));
        verify(repository).fetchByIds(List.of(GIVEN_MOVIE_ID));
    }

    @Test
    void return_movie_if_found() {
        // given
        when(repository.fetchById(anyInt())).thenReturn(Optional.of(STORED_MOVIE));

        // when
        var customer = service.fetchById(GIVEN_MOVIE_ID);

        // then
        assertThat(customer).isEqualTo(STORED_MOVIE);
    }

    @Test
    void throw_not_found_exception_if_requested_movie_not_found() {
        // given
        when(repository.fetchById(anyInt())).thenReturn(Optional.empty());

        // when
        assertThrows(
                MovieNotFoundException.class,
                () -> service.fetchById(GIVEN_MOVIE_ID)
        );
    }
}