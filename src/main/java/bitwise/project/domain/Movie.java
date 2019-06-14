package bitwise.project.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("movie")
@Data
public class Movie extends BaseEntity {

    private String name;

    private String director;

    private String tagline;
}
