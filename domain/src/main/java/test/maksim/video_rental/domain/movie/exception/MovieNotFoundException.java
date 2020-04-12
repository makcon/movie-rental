package test.maksim.video_rental.domain.movie.exception;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(Integer movieId) {
        super("Movie not found, id: " + movieId);
    }
}
