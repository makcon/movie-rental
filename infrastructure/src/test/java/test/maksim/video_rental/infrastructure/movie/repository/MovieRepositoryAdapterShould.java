package test.maksim.video_rental.infrastructure.movie.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.maksim.video_rental.domain.movie.model.Movie;
import test.maksim.video_rental.infrastructure.movie.converter.MovieEntityConverter;
import test.maksim.video_rental.infrastructure.movie.entity.MovieEntity;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieRepositoryAdapterShould {

    private static final Integer GIVEN_MOVIE_ID = new Random().nextInt();
    private static final MovieEntity MOVIE_ENTITY = mock(MovieEntity.class);
    private static final Movie MOVIE_MODEL = mock(Movie.class);
    
    @InjectMocks
    private MovieRepositoryAdapter adapter;
    
    @Mock
    private MovieRepository repository;
    @Mock
    private MovieEntityConverter converter;

    @Test
    void return_all_found_movies() {
        // given
        when(repository.findAll()).thenReturn(List.of(MOVIE_ENTITY));
        when(converter.toModel(any())).thenReturn(MOVIE_MODEL);

        // when
        var movies = adapter.fetchAll();

        // then
        assertThat(movies).isEqualTo(List.of(MOVIE_MODEL));
        verify(converter).toModel(MOVIE_ENTITY);
    }

    @Test
    void return_all_found_by_id_movies() {
        // given
        when(repository.findAllById(anyCollection())).thenReturn(List.of(MOVIE_ENTITY));
        when(converter.toModel(any())).thenReturn(MOVIE_MODEL);

        // when
        var movies = adapter.fetchByIds(List.of(GIVEN_MOVIE_ID));

        // then
        assertThat(movies).isEqualTo(List.of(MOVIE_MODEL));
        verify(repository).findAllById(List.of(GIVEN_MOVIE_ID));
        verify(converter).toModel(MOVIE_ENTITY);
    }

    @Test
    void return_movie_by_id_if_found() {
        // given
        when(repository.findById(anyInt())).thenReturn(Optional.of(MOVIE_ENTITY));
        when(converter.toModel(any())).thenReturn(MOVIE_MODEL);

        // when
        var customer = adapter.fetchById(GIVEN_MOVIE_ID);

        // then
        assertThat(customer).isEqualTo(Optional.of(MOVIE_MODEL));
        verify(repository).findById(GIVEN_MOVIE_ID);
        verify(converter).toModel(MOVIE_ENTITY);
    }

    @Test
    void return_empty_if_movie_not_found_by_id() {
        // given
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        // when
        var customer = adapter.fetchById(GIVEN_MOVIE_ID);

        // then
        assertThat(customer).isEmpty();
        verify(repository).findById(GIVEN_MOVIE_ID);
        verify(converter, never()).toModel(any());
    }
}