package AlgorithmTests;

import Algorithm.MatchAlgorithm;
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

class AlgorithmHelperTests {


    private Profile profileOne;
    private Profile profileTwo;

    @BeforeEach
    void init() {
        profileOne = new Profile();
        profileTwo = new Profile();
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

        Collection<Interest> interestCollectionOne = new ArrayDeque<>();
        Collection<Interest> interestCollectionTwo = new ArrayDeque<>();

        interestCollectionOne.add(interestOne);
        interestCollectionOne.add(interestTwo);
        interestCollectionOne.add(interestFour);

        interestCollectionTwo.add(interestTwo);
        interestCollectionTwo.add(interestThree);
        interestCollectionTwo.add(interestFour);
        interestCollectionTwo.add(interestFive);

        int numberOfOccurrences = MatchAlgorithmHelper.getSameOccurrences(interestCollectionOne, interestCollectionTwo);

        Assertions.assertEquals(2, numberOfOccurrences);
    }

    @Test
    void testGetMoreThanSameOccurrencesReturnsThree() {
        Interest interestOne = new Interest(-1, "pils");
        Interest interestTwo = new Interest(-1, "bier");
        Interest interestThree = new Interest(-1, "sos");
        Interest interestFour = new Interest(-1, "moh");
        Interest interestFive = new Interest(-1, "angerfist");

        Collection<Interest> interestCollectionOne = new ArrayDeque<>();
        Collection<Interest> interestCollectionTwo = new ArrayDeque<>();

        interestCollectionOne.add(interestOne);
        interestCollectionOne.add(interestTwo);
        interestCollectionOne.add(interestThree);
        interestCollectionOne.add(interestFour);
        interestCollectionOne.add(interestFive);

        interestCollectionTwo.add(interestOne);
        interestCollectionTwo.add(interestTwo);
        interestCollectionTwo.add(interestThree);
        interestCollectionTwo.add(interestFour);
        interestCollectionTwo.add(interestFive);

        int numberOfOccurrences = MatchAlgorithmHelper.getSameOccurrences(interestCollectionOne, interestCollectionTwo);

        Assertions.assertEquals(3, numberOfOccurrences);
    }

    @Test
    void testCalculateLikedEachOthersInterestsScore() {
        Collection<Interest> interestsPersonOne = new ArrayDeque<>();
        Collection<Interest> likedInterestsPersonOne = new ArrayList<>();
        Collection<Interest> dislikedInterestsPersonOne = new ArrayList<>();

        Collection<Interest> interestsPersonTwo = new ArrayDeque<>();
        Collection<Interest> likedInterestsPersonTwo = new ArrayList<>();
        Collection<Interest> dislikedInterestsPersonTwo = new ArrayList<>();

        final Interest pils = new Interest(-1, "pils");
        final Interest bier = new Interest(-1, "bier");
        final Interest moh = new Interest(-1, "moh");
        final Interest tekenen = new Interest(-1, "tekenen");
        final Interest vanGogh = new Interest(-1, "van Gogh");
        final Interest stappen = new Interest(-1, "stappen");

        // TODO: add the data to the collections

        profileOne.setInterests(interestsPersonOne);
        profileOne.setLikedInterests(likedInterestsPersonOne);
        profileOne.setDislikedInterests(dislikedInterestsPersonOne);

        profileTwo.setInterests(interestsPersonTwo);
        profileTwo.setLikedInterests(likedInterestsPersonTwo);
        profileTwo.setDislikedInterests(dislikedInterestsPersonTwo);

        Collection<Interest> profileOneFavorites = MatchAlgorithmHelper.getFavoriteInterests(profileOne);
        Collection<Interest> profileTwoFavorites = MatchAlgorithmHelper.getFavoriteInterests(profileTwo);

        int score = MatchAlgorithmHelper.calculateLikedEachOthersInterestsScore(profileOneFavorites, profileTwoFavorites, profileOne, profileTwo);
    }

}
