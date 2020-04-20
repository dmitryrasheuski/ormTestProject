package rash.testProject.entity.gender;

public interface GenderFactory {
    Gender getGender(String title);
    Gender getGender(int id);
}
