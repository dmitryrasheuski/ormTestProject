package rash.testProject.entity.contact;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HardCodeContactItemFactory implements ContactItemFactory {
    private Map<String, ContactItem> map;

    public HardCodeContactItemFactory () {

        ContactItem phone = new ContactItem(1, "phone");
        ContactItem email = new ContactItem(2, "email");
        ContactItem repository = new ContactItem(3, "repository");
        ContactItem skype = new ContactItem(4, "skype");
        ContactItem other = new ContactItem(5, "other");

        map = Stream.of(phone, email, repository, skype, other)
                .collect(
                        Collectors.toMap(ContactItem::getTitle, (item) -> item)
                );
    }

    @Override
    public ContactItem getContactItem(String title) {
        return map.get(title);
    }
}
