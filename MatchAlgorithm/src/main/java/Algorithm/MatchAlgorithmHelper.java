package Algorithm;

import Models.Profile;

public class MatchAlgorithmHelper {

    private static final int MAX_AGE_DIFF = 5;
    private static final int MAX_AGE_DIFF_BEST_SCORE = 2;

    private static final int MAX_SCORE = 100;

    private static final double CALC_AGE_FACTOR = 2.12;

    public static boolean genderConflicts(Profile profileOne, Profile profileTwo) {
        return profileOne.getPreference() != profileTwo.getGender() || profileTwo.getPreference() != profileOne.getGender();
    }

    public static boolean ageConflicts(Profile profileOne, Profile profileTwo) {
        int difference = profileOne.getAge() - profileTwo.getAge();
        difference = makePositive(difference);

        return difference > MAX_AGE_DIFF;
    }

    /**
     * Calculates the age score of two profiles
     *
     * @param profileOne The first profile
     * @param profileTwo The second profile
     * @return Score between 0 and 100
     */
    public static int calculateAgeScore(Profile profileOne, Profile profileTwo) {
        int difference = profileOne.getAge() - profileTwo.getAge();
        difference = makePositive(difference);

        if (difference <= MAX_AGE_DIFF_BEST_SCORE) {
            return MAX_SCORE;
        }

        return (int) (MAX_SCORE - 5 - (Math.pow(CALC_AGE_FACTOR, difference)));
    }

    private static int makePositive(int val) {
        return val < 0 ? -val : val;
    }
}
