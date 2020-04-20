package rash.testProject.entity.gender;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HardcodeGenderFactory implements GenderFactory{
    private Map<String, Gender> map;

    public HardcodeGenderFactory() {

        Gender male = new Gender(1, "male");
        Gender female = new Gender(2, "female");

        map = Stream.of(male, female)
                .collect(
                        Collectors.toMap(Gender::getTitle, (item) -> item)
                );
    }

    @Override
    public Gender getGender(String title) {
        return map.get(title);
    }

    @Override
    public Gender getGender(int id) {
        return map.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter((item) -> item.getId() == id)
                .findFirst()
                .get();
    }
}
