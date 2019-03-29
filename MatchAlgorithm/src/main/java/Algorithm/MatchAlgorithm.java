package Algorithm;

import Models.Interest;
import Models.Profile;

import java.util.Collection;

public class MatchAlgorithm {

    /**
     * Calculates the match score of two profiles
     *
     * @param profileOne The first profile
     * @param profileTwo The second profile
     * @return Score between 0 and 100
     */
    public static int calculateMatchScore(Profile profileOne, Profile profileTwo) {
        if (MatchAlgorithmHelper.genderConflicts(profileOne, profileTwo)) {
            return -1;
        }

        if (MatchAlgorithmHelper.ageConflicts(profileOne, profileTwo)) {
            return -1;
        }

        int ageScore = MatchAlgorithmHelper.calculateAgeScore(profileOne, profileTwo);

        int sameInterestsScore = MatchAlgorithmHelper.calculateSameInterestsScore(profileOne, profileTwo);

        Collection<Interest> profileOneFavoriteInterests = MatchAlgorithmHelper.getFavoriteInterests(profileOne);
        Collection<Interest> profileTwoFavoriteInterests = MatchAlgorithmHelper.getFavoriteInterests(profileTwo);

        int likedEachOthersInterestsScore = MatchAlgorithmHelper.calculateLikedEachOthersInterestsScore(profileOneFavoriteInterests, profileTwoFavoriteInterests, profileOne, profileTwo);

        return MatchAlgorithmHelper.calculateScore(ageScore, sameInterestsScore, likedEachOthersInterestsScore);
    }

    private MatchAlgorithm() {
    }

}
