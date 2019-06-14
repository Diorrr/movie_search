package bitwise.project.controller.mapper;

import bitwise.project.domain.Movie;
import bitwise.project.dto.MovieRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieMapper ISNTANCE = Mappers.getMapper(MovieMapper.class);

    Movie movieRequestDtoToMovie(MovieRequestDto requestDto);

    MovieRequestDto movieToMovieRequestDto(Movie movie);
}
