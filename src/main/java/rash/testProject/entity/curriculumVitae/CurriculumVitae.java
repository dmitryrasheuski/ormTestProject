package rash.testProject.entity.curriculumVitae;

import lombok.*;
import rash.testProject.entity.contact.Contact;
import rash.testProject.entity.gender.Gender;
import rash.testProject.entity.person.Person;
import rash.testProject.entity.technology.TechnologyItem;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Data
@AllArgsConstructor

@Entity
@Table(name = "person")

public class CurriculumVitae {

    protected CurriculumVitae() {
        person = new Person();
    }

    private Person person;
    private Set<TechnologyItem> technologies;
    private Set<Contact> contacts;

    @Id
    @Column(name = "id")
    protected Long getPersonId() {
        return person.getId();
    }
    protected void setPersonId(long id) {
        person.setId(id);
    }

    @Column(name = "first_name")
    protected String getFirstName() {
        return person.getFirstName();
    }
    protected void setFirstName(String firstName) {
        person.setFirstName(firstName);
    }

    @Column(name = "second_name")
    protected String getSecondName() {
        return person.getSecondName();
    }
    protected void setSecondName(String secondName) {
        person.setSecondName(secondName);
    }

    @Column(name = "patronymic")
    protected String getPersonPatronymic() {
        return person.getPatronymic();
    }
    protected void setPersonPatronymic(String patronymic) {
        person.setPatronymic(patronymic);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gender", referencedColumnName = "id")
    protected Gender getPersonGender() {
        return person.getGender();
    }
    protected void setPersonGender(Gender gender) {
        person.setGender(gender);
    }

    @Column(name = "birthday")
    protected Date getPersonBirthday() {
        return person.getBirthday();
    }
    protected void setPersonBirthday(Date birthday) {
        person.setBirthday(birthday);
    }

    @Transient
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "persons_technologies",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "technology_item_id", referencedColumnName = "id"))
    public Set<TechnologyItem> getTechnologies() {
        return technologies;
    }
    public void setTechnologies(Set<TechnologyItem> technologies) {
        this.technologies = technologies;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "persons_contacts",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "contact_item_id", referencedColumnName = "id"))
    public Set<Contact> getContacts() {
        return contacts;
    }
    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

}