package bitwise.project.service;

import bitwise.project.domain.Movie;
import bitwise.project.dto.MovieRequestDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Movie saveOrUpdate(MovieRequestDto movieRequestDto);

    void delete(String id);

    Optional<Movie> getById(String id);

    List<Movie> getAllMovies();

    List<Movie> search(String searchWord);
}
