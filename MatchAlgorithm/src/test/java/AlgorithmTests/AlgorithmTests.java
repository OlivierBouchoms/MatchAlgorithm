package AlgorithmTests;

import Algorithm.MatchAlgorithm;
import Algorithm.MatchAlgorithmHelper;
import Models.Gender;
import Models.Profile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AlgorithmTests {

    @Test
    void testCalculateMatchConflictingAge() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        profileOne.setAge(18);
        profileTwo.setAge(24);

        Assertions.assertEquals(-1, MatchAlgorithm.calculateMatchScore(profileOne, profileTwo));
        Assertions.assertEquals(-1, MatchAlgorithm.calculateMatchScore(profileTwo, profileOne));
    }

    @Test
    void testCalculateMatchConflictingGender() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        profileOne.setGender(Gender.Female);
        profileOne.setPreference(Gender.Female);
        profileTwo.setGender(Gender.Female);
        profileTwo.setPreference(Gender.Male);

        Assertions.assertEquals(-1, MatchAlgorithm.calculateMatchScore(profileOne, profileTwo));
        Assertions.assertEquals(-1, MatchAlgorithm.calculateMatchScore(profileTwo, profileOne));
    }

}
