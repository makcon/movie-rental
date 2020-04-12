package test.maksim.video_rental.infrastructure.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "movie_prices")
public class MoviePriceEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String category;
    private Integer price;
}
