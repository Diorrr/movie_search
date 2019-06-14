package bitwise.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDto {

    private String name;

    private String director;

    private String tagline;
}
