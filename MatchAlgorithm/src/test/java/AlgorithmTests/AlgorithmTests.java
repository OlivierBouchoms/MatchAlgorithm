package AlgorithmTests;

import Algorithm.MatchAlgorithm;
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

class AlgorithmTests {

    private Profile profileOne;
    private Profile profileTwo;
    private Collection<Interest> interestsPersonOne;
    private Collection<Interest> interestsPersonTwo;
    private List<Interest> likedInterestsPersonOne;
    private List<Interest> likedInterestsPersonTwo;
    private List<Interest> dislikedInterestsPersonOne;
    private List<Interest> dislikedInterestsPersonTwo;

    @BeforeEach
    void init() {
        profileOne = new Profile();
        profileTwo = new Profile();
        interestsPersonOne = new ArrayDeque<>();
        interestsPersonTwo = new ArrayDeque<>();
        likedInterestsPersonOne = new ArrayList<>();
        likedInterestsPersonTwo = new ArrayList<>();
        dislikedInterestsPersonOne = new ArrayList<>();
        dislikedInterestsPersonTwo = new ArrayList<>();

        profileOne.setName("Jeffrey");
        profileOne.setName("Joshua");
    }

    @Test
    void testCalculateMatchConflictingAge() {
        profileOne.setAge(18);
        profileTwo.setAge(24);

        Assertions.assertEquals(-1, MatchAlgorithm.calculateMatchScore(profileOne, profileTwo));
        Assertions.assertEquals(-1, MatchAlgorithm.calculateMatchScore(profileTwo, profileOne));
    }

    @Test
    void testCalculateMatchConflictingGender() {
        profileOne.setGender(Gender.FEMALE);
        profileOne.setPreference(Gender.FEMALE);
        profileTwo.setGender(Gender.FEMALE);
        profileTwo.setPreference(Gender.MALE);

        Assertions.assertEquals(-1, MatchAlgorithm.calculateMatchScore(profileOne, profileTwo));
        Assertions.assertEquals(-1, MatchAlgorithm.calculateMatchScore(profileTwo, profileOne));
    }

    @Test
    void testCalculateMatchScore() {
        int expected = 80;

        profileOne.setGender(Gender.MALE);
        profileOne.setPreference(Gender.FEMALE);
        profileTwo.setGender(Gender.FEMALE);
        profileTwo.setPreference(Gender.MALE);

        profileOne.setAge(21);
        profileTwo.setAge(19);

        final Interest interestVvd = new Interest(-1, "vvd");
        final Interest interestOne = new Interest(-1, "geld");
        final Interest interestTwo = new Interest(-1, "eten");
        final Interest interestThree = new Interest(-1, "sporten");
        final Interest interestFour = new Interest(-1, "paupers uitlachen");
        final Interest interestFive = new Interest(-1, "programmeren");
        final Interest interestSix = new Interest(-1, "uitgaan");
        final Interest interestSeven = new Interest(-1, "plaatjes draaien");
        final Interest interestEight = new Interest(-1, "speciaalbier drinken");
        final Interest interestNine = new Interest(-1, "ux development");
        final Interest interestTen = new Interest(-1, "tattoos");
        final Interest interestEleven = new Interest(-1, "gehydrateerd zijn");
        final Interest interestTwelve = new Interest(-1, "ademen");

        interestsPersonOne.add(interestOne);
        interestsPersonOne.add(interestTwo);
        interestsPersonOne.add(interestThree);
        interestsPersonOne.add(interestFour);
        interestsPersonOne.add(interestFive);
        interestsPersonOne.add(interestVvd);

        interestsPersonTwo.add(interestFive);
        interestsPersonTwo.add(interestSix);
        interestsPersonTwo.add(interestSeven);
        interestsPersonTwo.add(interestEight);
        interestsPersonTwo.add(interestNine);
        interestsPersonTwo.add(interestTen);
        interestsPersonTwo.add(interestEleven);
        interestsPersonTwo.add(interestTwelve);

        likedInterestsPersonOne.add(interestEight);
        likedInterestsPersonOne.add(interestEight);
        likedInterestsPersonOne.add(interestEight);

        likedInterestsPersonOne.add(interestNine);
        likedInterestsPersonOne.add(interestNine);
        likedInterestsPersonOne.add(interestNine);

        likedInterestsPersonOne.add(interestTen);
        likedInterestsPersonOne.add(interestTen);
        likedInterestsPersonOne.add(interestTen);

        likedInterestsPersonOne.add(interestEleven);
        likedInterestsPersonOne.add(interestEleven);
        likedInterestsPersonOne.add(interestEleven);

        likedInterestsPersonTwo.add(interestOne);
        likedInterestsPersonTwo.add(interestOne);
        likedInterestsPersonTwo.add(interestOne);
        likedInterestsPersonTwo.add(interestOne);

        likedInterestsPersonTwo.add(interestThree);
        likedInterestsPersonTwo.add(interestThree);
        likedInterestsPersonTwo.add(interestThree);

        likedInterestsPersonTwo.add(interestFour);
        likedInterestsPersonTwo.add(interestFour);
        likedInterestsPersonTwo.add(interestFour);
        likedInterestsPersonTwo.add(interestFour);

        likedInterestsPersonTwo.add(interestFive);
        likedInterestsPersonTwo.add(interestFive);
        likedInterestsPersonTwo.add(interestFive);
        likedInterestsPersonTwo.add(interestFive);
        likedInterestsPersonTwo.add(interestFive);

        dislikedInterestsPersonOne.add(interestSix);
        dislikedInterestsPersonOne.add(interestSeven);
        dislikedInterestsPersonOne.add(interestTwelve);

        dislikedInterestsPersonTwo.add(interestVvd);
        dislikedInterestsPersonTwo.add(interestVvd);
        dislikedInterestsPersonTwo.add(interestVvd);

        dislikedInterestsPersonTwo.add(interestTwo);
        dislikedInterestsPersonTwo.add(interestTwo);

        profileOne.setInterests(interestsPersonOne);
        profileOne.setLikedInterests(likedInterestsPersonOne);
        profileOne.setDislikedInterests(dislikedInterestsPersonOne);

        profileTwo.setInterests(interestsPersonTwo);
        profileTwo.setLikedInterests(likedInterestsPersonTwo);
        profileTwo.setDislikedInterests(dislikedInterestsPersonTwo);

        Assertions.assertTrue(MatchAlgorithm.calculateMatchScore(profileOne, profileTwo) <= expected + 5 && MatchAlgorithm.calculateMatchScore(profileOne, profileTwo) >= expected - 5);
    }

}
