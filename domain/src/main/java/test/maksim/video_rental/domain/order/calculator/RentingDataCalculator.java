package test.maksim.video_rental.domain.order.calculator;

import org.springframework.stereotype.Service;
import test.maksim.video_rental.domain.movie.model.Movie;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class RentingDataCalculator {

    public Integer calculatePrice(Movie movie, Integer rentingDays) {
        if (rentingDays <= movie.getType().getRentingDays()) {
            return movie.getPrice().getPrice();
        }

        return movie.getPrice().getPrice() + calculateExtraDaysPrice(movie, rentingDays);
    }

    public Instant calculateExpiresAt(Movie movie, Instant createdAt, Integer rentingDays) {
        var plusDays = Math.max(rentingDays, movie.getType().getRentingDays());

        return createdAt.plus(plusDays, DAYS);
    }

    private int calculateExtraDaysPrice(Movie movie, Integer rentingDays) {
        return (rentingDays - movie.getType().getRentingDays()) * movie.getPrice().getPrice();
    }
}
