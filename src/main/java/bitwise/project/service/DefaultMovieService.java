package bitwise.project.service;

import bitwise.project.configuration.MongoConfigurationProperties;
import bitwise.project.controller.mapper.MovieMapper;
import bitwise.project.domain.Movie;
import bitwise.project.dto.MovieRequestDto;
import bitwise.project.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class DefaultMovieService implements MovieService {

    private final MovieRepository repository;
    private final MovieMapper mapper;
    private final MongoConfigurationProperties properties;

    public DefaultMovieService(MovieRepository repository, MovieMapper mapper, MongoConfigurationProperties properties) {
        this.repository = repository;
        this.mapper = mapper;
        this.properties = properties;
    }

    @Override
    public Movie saveOrUpdate(MovieRequestDto movieRequestDto) {
        Movie movie = mapper.movieRequestDtoToMovie(movieRequestDto);
        log.info("New MovieRequestDto");
        return repository.save(movie);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Movie> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> moviesList = new ArrayList<>();
        repository.findAll().forEach(moviesList::add);

        return moviesList;
    }

    @Override
    public List<Movie> search(String searchWord) {

        List<Movie> result = new ArrayList<>();
        List<Movie> temp = new ArrayList<>();
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s.]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(searchWord);

        List<String> keywords = Arrays.asList(searchWord.split("\\s* \\s*"));

        if (searchWord.length() < 3 || !matcher.find() || keywords.size() == 0) {
            return result;
        }

        temp = searchMultipleWords(keywords, true);
        result = uniteListWithoutDuplicate(result, temp);

        temp = searchMultipleWords(keywords, false);
        result = uniteListWithoutDuplicate(result, temp);

        temp = searchWithProbableMistake(keywords, 1);
        result = uniteListWithoutDuplicate(result, temp);

        temp = searchWithProbableMistake(keywords, 2);
        result = uniteListWithoutDuplicate(result, temp);

        temp = searchSubString(keywords);
        result = uniteListWithoutDuplicate(result, temp);

        return result;
    }

    private List<Movie> searchWithProbableMistake(List<String> keywords, int concat) {

        String regexp = "";
        for (int i = 0; i < keywords.size(); i++) {
            String word = keywords.get(i);
            regexp = regexp.concat("\\s*").concat(word.substring(0, word.length() - concat)).concat("\\s*");
            if (i != keywords.size() - 1) {
                regexp = regexp.concat("|");
            }
        }

        List<Movie> movieByTaglineRegex = repository.findMovieByTaglineRegex(regexp);
        List<Movie> movieByDirectorRegex = repository.findMovieByDirectorRegex(regexp);
        List<Movie> movieByNameRegex = repository.findMovieByNameRegex(regexp);

        return uniteListWithoutDuplicate(movieByDirectorRegex, movieByNameRegex, movieByTaglineRegex);
    }

    private List<Movie> searchSubString(List<String> keywords) {

        String regexp = "";
        for (int i = 0; i < keywords.size() - 1; i++) {
            regexp = regexp.concat("\\s*").concat(keywords.get(i)).concat("\\s*").concat("|");
        }
        regexp = regexp.concat("\\s*").concat(keywords.get(keywords.size() - 1)).concat("\\s*");

        List<Movie> movieByTaglineRegex = repository.findMovieByTaglineRegex(regexp);
        List<Movie> movieByDirectorRegex = repository.findMovieByDirectorRegex(regexp);
        List<Movie> movieByNameRegex = repository.findMovieByNameRegex(regexp);

        return uniteListWithoutDuplicate(movieByDirectorRegex, movieByNameRegex, movieByTaglineRegex);
    }

    private List<Movie> searchMultipleWords(List<String> keywords, boolean searchPhrase) {
        String concatElement;
        if (searchPhrase) {
            concatElement = "\\s";
        } else {
            concatElement = "|";
        }

        String regexp = "(?i)(\\W|^)(";
        for (int i = 0; i < keywords.size() - 1; i++) {
            regexp = regexp.concat(keywords.get(i)).concat(concatElement);
        }
        regexp = regexp.concat(keywords.get(keywords.size() - 1)).concat(")(\\W|$)");

        List<Movie> movieByTaglineRegex = repository.findMovieByTaglineRegex(regexp);
        List<Movie> movieByDirectorRegex = repository.findMovieByDirectorRegex(regexp);
        List<Movie> movieByNameRegex = repository.findMovieByNameRegex(regexp);

        return uniteListWithoutDuplicate(movieByDirectorRegex, movieByNameRegex, movieByTaglineRegex);
    }

    private List<Movie> uniteListWithoutDuplicate(List<Movie> first, List<Movie> second, List<Movie> third) {
        first = uniteListWithoutDuplicate(first, second);

        return uniteListWithoutDuplicate(first, third);
    }

    private List<Movie> uniteListWithoutDuplicate(List<Movie> first, List<Movie> second) {
        if (first == null || first.size() == 0)
            return second;
        if (second == null || second.size() == 0)
            return first;

        for (Movie movie : first) {
            if (!second.contains(movie)) {
                second.add(movie);
            }
        }

        return second;
    }
}
