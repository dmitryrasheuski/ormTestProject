package rash.testProject.entity.gender;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor

@Entity
@Table(name = "gender")

public class Gender {
    @Id
    private Integer id;
    @NonNull
    private String title;
}
