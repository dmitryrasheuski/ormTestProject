package rash.testProject.entity.contact;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor

@Entity
@Table(name = "contact_item")

public class ContactItem implements Serializable {
    @Id
    private Integer id;
    @NonNull
    private String title;
}
