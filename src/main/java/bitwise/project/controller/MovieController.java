package bitwise.project.controller;

import bitwise.project.configuration.MongoConfigurationProperties;
import bitwise.project.controller.mapper.MovieMapper;
import bitwise.project.domain.Movie;
import bitwise.project.dto.MovieRequestDto;
import bitwise.project.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/movie")
@RestController
@Slf4j
public class MovieController {

    private final MovieService movieService;
    private final MongoConfigurationProperties properties;
    private final MovieMapper mapper;

    public MovieController(MovieService movieService, MongoConfigurationProperties properties, MovieMapper mapper) {
        this.movieService = movieService;
        this.properties = properties;
        this.mapper = mapper;
    }

    @PutMapping("/save")
    public ResponseEntity save(@Valid @RequestBody MovieRequestDto movieRequestDto) {
        Movie response = movieService.saveOrUpdate(movieRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get")
    public ResponseEntity get(@Valid @RequestBody String id) {
        Optional<Movie> response = movieService.getById(id);
        if (response.isPresent()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/search")
    public ResponseEntity search(@Valid @RequestBody String searchWord) {
        List<Movie> result = movieService.search(searchWord);
        List<MovieRequestDto> response = new ArrayList<>();
        result.forEach(movie -> {
            response.add(mapper.movieToMovieRequestDto(movie));
        });

        return ResponseEntity.ok(response);
    }

}
