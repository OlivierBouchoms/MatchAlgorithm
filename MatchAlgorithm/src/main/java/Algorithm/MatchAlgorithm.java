package Algorithm;

import Models.Profile;

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

        return -1;
    }



}
