package test.maksim.video_rental.application.movie.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.maksim.video_rental.domain.movie.model.Movie;
import test.maksim.video_rental.domain.movie.service.MovieService;

import java.util.List;

@Api
@Slf4j
@RestController
@RequestMapping("/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping
    public List<Movie> fetchAll() {
        log.info("Fetching all movies");
        return service.fetchAll();
    }
}
