package bitwise.project.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class BaseEntity {
    @Id
    private String id;

//    @CreatedDate
//    private LocalDateTime createdDate;
//
//    @LastModifiedDate
//    private LocalDateTime updatedDate;
}
