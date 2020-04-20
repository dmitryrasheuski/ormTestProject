package rash.testProject.entity.technology;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor

@Entity
@Table(name = "technology_item")

public class TechnologyItem {
    @Id
    private Integer id;
    @NonNull
    private String title;
}
