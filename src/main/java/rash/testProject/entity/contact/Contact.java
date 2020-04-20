package rash.testProject.entity.contact;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Data

@Entity
@Table(name = "contact_item")
@SecondaryTable(name = "persons_contacts", pkJoinColumns = @PrimaryKeyJoinColumn(name = "contact_item_id", referencedColumnName = "id"))

public class Contact {

    protected Contact() {
        contactItem = new ContactItem();
    }

    @NonNull
    private ContactItem contactItem;
    @NonNull
    private String value;


    @Id
    @Column(name = "id")
    protected int getContactItemId() {
        return contactItem.getId();
    }
    protected void setContactItemId(int id) {
        contactItem.setId(id);
    }

    @Column(name = "title")
    protected String getContactItemTitle(){
        return contactItem.getTitle();
    }
    protected void setContactItemTitle(String title){
        contactItem.setTitle(title);
    }

    @Transient
    public ContactItem getContactItem() {
        return contactItem;
    }
    public void setContactItem(ContactItem contactItem) {
        this.contactItem = contactItem;
    }

    @Column(name = "value", table = "persons_contacts")
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
