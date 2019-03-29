package AlgorithmTests;

import Algorithm.MatchAlgorithmHelper;
import Models.Gender;
import Models.Interest;
import Models.Profile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class AlgorithmHelperTests {

    private Profile profileOne;
    private Profile profileTwo;
    private Collection<Interest> interestsPersonOne;
    private Collection<Interest> interestsPersonTwo;
    private Collection<Interest> interests;
    private List<Interest> likedInterests;
    private List<Interest> dislikedInterests;

    @BeforeEach
    void init() {
        profileOne = new Profile();
        profileTwo = new Profile();
        interestsPersonOne = new ArrayDeque<>();
        interestsPersonTwo = new ArrayDeque<>();
        interests = new ArrayDeque<>();
        likedInterests = new ArrayList<>();
        dislikedInterests = new ArrayList<>();
    }

    @Test
    void testCalculateAgeScoreSameAgeShouldReturn100() {
        profileOne.setAge(22);
        profileTwo.setAge(22);

        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileOne, profileTwo));
        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileTwo, profileOne));
    }

    @Test
    void testCalculateAgeScoreAgeDeltaOneShouldReturn100() {
        profileOne.setAge(22);
        profileTwo.setAge(21);

        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileOne, profileTwo));
        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileTwo, profileOne));
    }

    @Test
    void testCalculateAgeScoreAgeDeltaTwoShouldReturn100() {
        profileOne.setAge(22);
        profileTwo.setAge(20);

        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileOne, profileTwo));
        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateAgeScore(profileTwo, profileOne));
    }

    @Test
    void testCalculateAgeScoreAgeDeltaHigherThanThreeReturnsBetween50And100() {
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
        profileOne.setGender(Gender.MALE);
        profileOne.setPreference(Gender.FEMALE);
        profileTwo.setGender(Gender.FEMALE);
        profileTwo.setPreference(Gender.MALE);

        Assertions.assertFalse(MatchAlgorithmHelper.genderConflicts(profileOne, profileTwo));
        Assertions.assertFalse(MatchAlgorithmHelper.genderConflicts(profileTwo, profileOne));
    }

    @Test
    void testGenderConflictsWithConflictingPreferenceReturnsTrue() {
        profileOne.setGender(Gender.FEMALE);
        profileOne.setPreference(Gender.FEMALE);
        profileTwo.setGender(Gender.FEMALE);
        profileTwo.setPreference(Gender.MALE);

        Assertions.assertTrue(MatchAlgorithmHelper.genderConflicts(profileOne, profileTwo));
        Assertions.assertTrue(MatchAlgorithmHelper.genderConflicts(profileTwo, profileOne));
    }

    @Test
    void testAgeConflictsWithCorrectAgeReturnsFalse() {
        profileOne.setAge(24);
        profileTwo.setAge(20);


        Assertions.assertFalse(MatchAlgorithmHelper.ageConflicts(profileOne, profileTwo));
        Assertions.assertFalse(MatchAlgorithmHelper.ageConflicts(profileTwo, profileOne));
    }

    @Test
    void testAgeConflictWithTooLargeDifferenceReturnsTrue() {
        profileOne.setAge(18);
        profileTwo.setAge(24);

        Assertions.assertTrue(MatchAlgorithmHelper.ageConflicts(profileOne, profileTwo));
        Assertions.assertTrue(MatchAlgorithmHelper.ageConflicts(profileTwo, profileOne));
    }

    @Test
    void testCalculateSameInterests3SharedInterestsScoreReturns100() {
        interests.add(new Interest(-1, "pils"));
        interests.add(new Interest(-1, "bier"));
        interests.add(new Interest(-1, "sos"));

        profileOne.setInterests(interests);
        profileTwo.setInterests(interests);

        Assertions.assertEquals(100, MatchAlgorithmHelper.calculateSameInterestsScore(profileOne, profileTwo));
    }

    @Test
    void testCalculateSameInterests1or2SharedInterestsScoreReturnsBetween50And100() {
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
        Interest interestOne = new Interest(-1, "pils");
        Interest interestTwo = new Interest(-1, "bier");
        Interest interestThree = new Interest(-1, "sos");
        Interest interestFour = new Interest(-1, "moh");

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

        profileOne.setLikedInterests(likedInterests);
        profileOne.setDislikedInterests(dislikedInterests);

        Collection<Interest> favoriteInterests = MatchAlgorithmHelper.getFavoriteInterests(profileOne);

        favoriteInterests.forEach((interest -> {
            if (interest.getName().equals(interestTwo.getName()) || interest.getName().equals(interestFour.getName())) {
                Assertions.fail("Interest should not be a favorite.");
            }
        }));

        Assertions.assertEquals(2, favoriteInterests.size());
    }

    @Test
    void testGetSameOccurrences() {
        Interest interestOne = new Interest(-1, "pils");
        Interest interestTwo = new Interest(-1, "bier");
        Interest interestThree = new Interest(-1, "sos");
        Interest interestFour = new Interest(-1, "moh");
        Interest interestFive = new Interest(-1, "angerfist");

        interestsPersonOne.add(interestOne);
        interestsPersonOne.add(interestTwo);
        interestsPersonOne.add(interestFour);

        interestsPersonTwo.add(interestTwo);
        interestsPersonTwo.add(interestThree);
        interestsPersonTwo.add(interestFour);
        interestsPersonTwo.add(interestFive);

        Assertions.assertEquals(2, MatchAlgorithmHelper.getSameOccurrences(interestsPersonOne, interestsPersonTwo));
        Assertions.assertEquals(2, MatchAlgorithmHelper.getSameOccurrences(interestsPersonTwo, interestsPersonOne));
    }

    @Test
    void testGetMoreThanSameOccurrencesReturnsThree() {
        Interest interestOne = new Interest(-1, "pils");
        Interest interestTwo = new Interest(-1, "bier");
        Interest interestThree = new Interest(-1, "sos");
        Interest interestFour = new Interest(-1, "moh");
        Interest interestFive = new Interest(-1, "angerfist");

        interestsPersonOne.add(interestOne);
        interestsPersonOne.add(interestTwo);
        interestsPersonOne.add(interestThree);
        interestsPersonOne.add(interestFour);
        interestsPersonOne.add(interestFive);

        interestsPersonTwo.add(interestOne);
        interestsPersonTwo.add(interestTwo);
        interestsPersonTwo.add(interestThree);
        interestsPersonTwo.add(interestFour);
        interestsPersonTwo.add(interestFive);

        int numberOfOccurrences = MatchAlgorithmHelper.getSameOccurrences(interestsPersonOne, interestsPersonTwo);

        Assertions.assertEquals(3, numberOfOccurrences);
    }

    @Test
    void testCalculateScore() {
        int expected = 60;

        int ageScore = 50;
        int sameInterestScore = 70;
        int likedEachOthersInterestsScore = 55;

        Assertions.assertEquals(expected, MatchAlgorithmHelper.calculateScore(ageScore, sameInterestScore, likedEachOthersInterestsScore));
    }

    @Test
    void testCalculateScorePerfectMatchReturns100() {
        int expected = 100;

        int ageScore = 100;
        int sameInterestScore = 100;
        int likedEachOthersInterestsScore = 100;

        Assertions.assertEquals(expected, MatchAlgorithmHelper.calculateScore(ageScore, sameInterestScore, likedEachOthersInterestsScore));
    }

    @Test
    void testCalculateLikedEachOthersInterestsScore() {
        List<Interest> likedInterestsPersonOne = new ArrayList<>();
        List<Interest> dislikedInterestsPersonOne = new ArrayList<>();

        List<Interest> likedInterestsPersonTwo = new ArrayList<>();
        List<Interest> dislikedInterestsPersonTwo = new ArrayList<>();

        final Interest interestOne = new Interest(-1, "pils");
        final Interest interestTwo = new Interest(-1, "bier");
        final Interest interestThree = new Interest(-1, "moh");
        final Interest interestFour = new Interest(-1, "tekenen");
        final Interest interestFive = new Interest(-1, "honden");
        final Interest interestSix = new Interest(-1, "stappen");
        final Interest interestSeven = new Interest(-1, "horeca");
        final Interest interestEight = new Interest(-1, "voetbal");
        final Interest interestNine = new Interest(-1, "sport");
        final Interest interestTen = new Interest(-1, "gamen");

        interestsPersonOne.add(interestOne);
        interestsPersonOne.add(interestTwo);
        interestsPersonOne.add(interestThree);
        interestsPersonOne.add(interestSix);

        interestsPersonTwo.add(interestFour);
        interestsPersonTwo.add(interestFive);
        interestsPersonTwo.add(interestSeven);
        interestsPersonTwo.add(interestNine);

        likedInterestsPersonOne.add(interestOne);
        likedInterestsPersonOne.add(interestOne);
        likedInterestsPersonOne.add(interestTwo);
        likedInterestsPersonOne.add(interestTwo);
        likedInterestsPersonOne.add(interestTwo);
        likedInterestsPersonOne.add(interestFour);
        likedInterestsPersonOne.add(interestSix);
        likedInterestsPersonOne.add(interestNine);
        likedInterestsPersonOne.add(interestNine);
        likedInterestsPersonOne.add(interestNine);
        likedInterestsPersonOne.add(interestNine);

        dislikedInterestsPersonOne.add(interestOne);
        dislikedInterestsPersonOne.add(interestOne);
        dislikedInterestsPersonOne.add(interestTwo);
        dislikedInterestsPersonOne.add(interestEight);
        dislikedInterestsPersonOne.add(interestNine);
        dislikedInterestsPersonOne.add(interestTen);

        likedInterestsPersonTwo.add(interestTwo);
        likedInterestsPersonTwo.add(interestTwo);
        likedInterestsPersonTwo.add(interestTwo);
        likedInterestsPersonTwo.add(interestThree);
        likedInterestsPersonTwo.add(interestThree);
        likedInterestsPersonTwo.add(interestThree);
        likedInterestsPersonTwo.add(interestSix);

        dislikedInterestsPersonTwo.add(interestOne);
        dislikedInterestsPersonTwo.add(interestFour);
        dislikedInterestsPersonTwo.add(interestFive);
        dislikedInterestsPersonTwo.add(interestSix);
        dislikedInterestsPersonTwo.add(interestSix);
        dislikedInterestsPersonTwo.add(interestEight);

        profileOne.setInterests(interestsPersonOne);
        profileOne.setLikedInterests(likedInterestsPersonOne);
        profileOne.setDislikedInterests(dislikedInterestsPersonOne);

        profileTwo.setInterests(interestsPersonTwo);
        profileTwo.setLikedInterests(likedInterestsPersonTwo);
        profileTwo.setDislikedInterests(dislikedInterestsPersonTwo);

        Collection<Interest> profileOneFavorites = MatchAlgorithmHelper.getFavoriteInterests(profileOne);
        Collection<Interest> profileTwoFavorites = MatchAlgorithmHelper.getFavoriteInterests(profileTwo);

        int score = MatchAlgorithmHelper.calculateLikedEachOthersInterestsScore(profileOneFavorites, profileTwoFavorites, profileOne, profileTwo);

        Assertions.assertTrue(score > 50 && score < 65);
    }

    @Test
    void testCalculateLikedEachOthersInterestsScorePerfectMatchReturns100() {
        List<Interest> likedInterestsPersonOne = new ArrayList<>();
        List<Interest> dislikedInterestsPersonOne = new ArrayList<>();

        List<Interest> likedInterestsPersonTwo = new ArrayList<>();
        List<Interest> dislikedInterestsPersonTwo = new ArrayList<>();

        final Interest interestOne = new Interest(-1, "pils");
        final Interest interestTwo = new Interest(-1, "bier");
        final Interest interestThree = new Interest(-1, "moh");

        interestsPersonOne.add(interestOne);
        interestsPersonOne.add(interestTwo);
        interestsPersonOne.add(interestThree);

        interestsPersonTwo.add(interestOne);
        interestsPersonTwo.add(interestTwo);
        interestsPersonTwo.add(interestThree);

        likedInterestsPersonOne.add(interestOne);
        likedInterestsPersonOne.add(interestOne);
        likedInterestsPersonOne.add(interestOne);
        likedInterestsPersonOne.add(interestTwo);
        likedInterestsPersonOne.add(interestTwo);
        likedInterestsPersonOne.add(interestTwo);
        likedInterestsPersonOne.add(interestThree);
        likedInterestsPersonOne.add(interestThree);
        likedInterestsPersonOne.add(interestThree);

        likedInterestsPersonTwo.add(interestOne);
        likedInterestsPersonTwo.add(interestOne);
        likedInterestsPersonTwo.add(interestOne);
        likedInterestsPersonTwo.add(interestTwo);
        likedInterestsPersonTwo.add(interestTwo);
        likedInterestsPersonTwo.add(interestTwo);
        likedInterestsPersonTwo.add(interestThree);
        likedInterestsPersonTwo.add(interestThree);
        likedInterestsPersonTwo.add(interestThree);

        profileOne.setInterests(interestsPersonOne);
        profileOne.setLikedInterests(likedInterestsPersonOne);
        profileOne.setDislikedInterests(dislikedInterestsPersonOne);

        profileTwo.setInterests(interestsPersonTwo);
        profileTwo.setLikedInterests(likedInterestsPersonTwo);
        profileTwo.setDislikedInterests(dislikedInterestsPersonTwo);

        Collection<Interest> profileOneFavorites = MatchAlgorithmHelper.getFavoriteInterests(profileOne);
        Collection<Interest> profileTwoFavorites = MatchAlgorithmHelper.getFavoriteInterests(profileTwo);

        int score = MatchAlgorithmHelper.calculateLikedEachOthersInterestsScore(profileOneFavorites, profileTwoFavorites, profileOne, profileTwo);

        Assertions.assertEquals(100, score);
    }

}
