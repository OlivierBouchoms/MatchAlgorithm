package AlgorithmTests;

import Algorithm.MatchAlgorithmHelper;
import Models.Gender;
import Models.Interest;
import Models.Profile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

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

    @Test
    void testCalculateSameInterests3SharedInterestsScoreReturns100() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        Collection<Interest> interests = new ArrayDeque<>();
        interests.add(new Interest(-1, "pils"));
        interests.add(new Interest(-1, "bier"));
        interests.add(new Interest(-1, "sos"));

        profileOne.setInterests(interests);
        profileTwo.setInterests(interests);

        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateSameInterestsScore(profileOne, profileTwo));
    }

    @Test
    void testCalculateSameInterests1or2SharedInterestsScoreReturnsBetween50And100() {
        Profile profileOne = new Profile();
        Profile profileTwo = new Profile();

        Collection<Interest> interests = new ArrayDeque<>();
        interests.add(new Interest(-1, "pils"));

        profileOne.setInterests(interests);
        profileTwo.setInterests(interests);

        int scoreOneSharedInterest = MatchAlgorithmHelper.calculateSameInterestsScore(profileOne, profileTwo);

        interests.add(new Interest(-1, "bier"));

        int scoreTwoSharedInterests = MatchAlgorithmHelper.calculateSameInterestsScore(profileOne, profileTwo);

        Assertions.assertTrue(scoreTwoSharedInterests > scoreOneSharedInterest && scoreTwoSharedInterests < 100 && scoreOneSharedInterest >= 50);
    }

    @Test
    void testGetFavoriteInterestsReturnsTheFavoriteInterests() {
        Profile profile = new Profile();

        Interest interestOne = new Interest(-1, "pils");
        Interest interestTwo = new Interest(-1, "bier");
        Interest interestThree = new Interest(-1, "sos");
        Interest interestFour = new Interest(-1, "moh");

        Collection<Interest> likedInterests = new ArrayList<>();
        Collection<Interest> dislikedInterests = new ArrayList<>();

        likedInterests.add(interestOne);
        likedInterests.add(interestOne);
        likedInterests.add(interestOne);
        likedInterests.add(interestOne);

        likedInterests.add(interestTwo);

        likedInterests.add(interestThree);
        likedInterests.add(interestThree);

        likedInterests.add(interestFour);
        likedInterests.add(interestFour);

        dislikedInterests.add(interestOne);

        dislikedInterests.add(interestThree);

        dislikedInterests.add(interestFour);
        dislikedInterests.add(interestFour);

        profile.setLikedInterests(likedInterests);
        profile.setDislikedInterests(dislikedInterests);

        Collection<Interest> favoriteInterests = MatchAlgorithmHelper.getFavoriteInterests(profile);

        favoriteInterests.forEach((interest -> {
            if (interest.getName().equals(interestTwo.getName()) || interest.getName().equals(interestFour.getName())) {
                Assertions.fail("Interest should not be a favorite.");
            }
        }));

        Assertions.assertEquals(2, favoriteInterests.size());
    }

}
