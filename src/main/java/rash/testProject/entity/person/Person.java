package rash.testProject.entity.person;

import lombok.*;
import rash.testProject.entity.gender.Gender;

import javax.persistence.*;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "person")

public class Person {
    @Id
    private Long id;
    private String firstName;
    private String secondName;
    private String patronymic;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gender", referencedColumnName = "id")
    private Gender gender;
    private Date birthday;
}
