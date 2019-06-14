package bitwise.project.repository;

import bitwise.project.domain.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String>, QuerydslPredicateExecutor<Movie> {

    List<Movie> findByNameLike(String name);

    List<Movie> findByNameContaining(String name);

    List<Movie> findMovieByNameRegex(String regexp);

    List<Movie> findByDirectorLike(String name);

    List<Movie> findByDirectorContaining(String name);

    List<Movie> findMovieByDirectorRegex(String regexp);

    List<Movie> findByTaglineLike(String name);

    List<Movie> findByTaglineContaining(String name);

    List<Movie> findMovieByTaglineRegex(String regexp);

    void deleteById(String id);

    Optional<Movie> findById(String id);
}
