package test.maksim.video_rental.domain.order.calculator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.maksim.video_rental.domain.order.model.Order;
import test.maksim.video_rental.domain.movie.service.MovieService;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class SurchargeCalculator {

    private final MovieService movieService;

    public Integer calculate(Order order, Instant closedAt) {
        if (closedAt.isAfter(order.getExpiresAt())) {
            var movie = movieService.fetchById(order.getMovieId());
            var overdueDays = DAYS.between(order.getExpiresAt(), closedAt) + 1;

            return Math.toIntExact(overdueDays * movie.getPrice().getPrice());
        }

        return 0;
    }
}
