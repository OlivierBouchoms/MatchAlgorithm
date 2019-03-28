package AlgorithmTests;

import Algorithm.MatchAlgorithmHelper;
import Models.Gender;
import Models.Profile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AlgorithmHelperTests {


    @Test
    void testCalculateAgeScoreSameAgeShouldReturn100() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        profileOne.setAge(22);
        profileTwo.setAge(22);

        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileOne, profileTwo));
        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileTwo, profileOne));
    }

    @Test
    void testCalculateAgeScoreAgeDeltaOneShouldReturn100() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        profileOne.setAge(22);
        profileTwo.setAge(21);

        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileOne, profileTwo));
        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileTwo, profileOne));
    }

    @Test
    void testCalculateAgeScoreAgeDeltaTwoShouldReturn100() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        profileOne.setAge(22);
        profileTwo.setAge(20);

        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileOne, profileTwo));
        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileTwo, profileOne));
    }

    @Test
    void testCalculateAgeScoreAgeDeltaHigherThanThreeReturnsBetween50And100() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        profileOne.setAge(22);
        profileTwo.setAge(19);

        int calculatedScoreDelta3 = MatchAlgorithmHelper.calculateAgeScore(profileOne, profileTwo);

        profileTwo.setAge(18);

        int calculatedScoreDelta4 = MatchAlgorithmHelper.calculateAgeScore(profileOne, profileTwo);

        profileTwo.setAge(17);

        int calculatedScoreDelta5 = MatchAlgorithmHelper.calculateAgeScore(profileOne, profileTwo);

        Assertions.assertTrue(calculatedScoreDelta3 > calculatedScoreDelta4 && calculatedScoreDelta4 > calculatedScoreDelta5 && calculatedScoreDelta3 < 100 && calculatedScoreDelta5 > 50);
    }

    @Test
    void testGenderConflictsWithMatchingPreferenceReturnsFalse() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        profileOne.setGender(Gender.Male);
        profileOne.setPreference(Gender.Female);
        profileTwo.setGender(Gender.Female);
        profileTwo.setPreference(Gender.Male);

        Assertions.assertFalse(MatchAlgorithmHelper.genderConflicts(profileOne, profileTwo));
        Assertions.assertFalse(MatchAlgorithmHelper.genderConflicts(profileTwo, profileOne));
    }

    @Test
    void testGenderConflictsWithConflictingPreferenceReturnsTrue() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        profileOne.setGender(Gender.Female);
        profileOne.setPreference(Gender.Female);
        profileTwo.setGender(Gender.Female);
        profileTwo.setPreference(Gender.Male);

        Assertions.assertTrue(MatchAlgorithmHelper.genderConflicts(profileOne, profileTwo));
        Assertions.assertTrue(MatchAlgorithmHelper.genderConflicts(profileTwo, profileOne));
    }

    @Test
    void testAgeConflictsWithCorrectAgeReturnsFalse() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        profileOne.setAge(24);
        profileTwo.setAge(20);


        Assertions.assertFalse(MatchAlgorithmHelper.ageConflicts(profileOne, profileTwo));
        Assertions.assertFalse(MatchAlgorithmHelper.ageConflicts(profileTwo, profileOne));
    }

    @Test
    void testAgeConflictWithTooLargeDifferenceReturnsTrue() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        profileOne.setAge(18);
        profileTwo.setAge(24);

        Assertions.assertTrue(MatchAlgorithmHelper.ageConflicts(profileOne, profileTwo));
        Assertions.assertTrue(MatchAlgorithmHelper.ageConflicts(profileTwo, profileOne));
    }

}
