package test.maksim.video_rental.infrastructure.movie.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;
    @OneToOne
    @JoinColumn(name = "movie_type_id", referencedColumnName = "id")
    private MovieTypeEntity type;
    @OneToOne
    @JoinColumn(name = "movie_price_id", referencedColumnName = "id")
    private MoviePriceEntity price;
}
