package bitwise.project.domain;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathInits;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.querydsl.codegen.EntitySerializer")
public class QMovie extends EntityPathBase<Movie> {

    private static final long serialVersionUID = 257370107L;

    public static final QMovie movie = new QMovie("movie");

    public QMovie(String variable) {
        super(Movie.class, variable);
    }

    public QMovie(Class<? extends Movie> type, PathMetadata metadata) {
        super(type, metadata);
    }

    public QMovie(Class<? extends Movie> type, PathMetadata metadata, @Nullable PathInits inits) {
        super(type, metadata, inits);
    }
}
