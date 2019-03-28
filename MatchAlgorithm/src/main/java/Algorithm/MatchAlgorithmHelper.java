package Algorithm;

import Models.Interest;
import Models.Profile;

public class MatchAlgorithmHelper {

    private static final int MAX_AGE_DIFF = 5;
    private static final int MAX_AGE_DIFF_BEST_SCORE = 2;

    private static final int MAX_SCORE = 100;

    private static final int MIN_SAME_INTEREST_FOR_MAX_SCORE = 3;

    private static final double CALC_AGE_SCORE_FACTOR = 2.12;
    private static final double SAME_INTEREST_SCORE_FACTOR = 2.12;
    private static final double SAME_INTEREST_COUNT_FACTOR = 2.2;

    /**
     * Determines if gender preferences don't conflict
     * @param profileOne The first profile
     * @param profileTwo The second profile
     * @return If the gender preferences conflict
     */
    public static boolean genderConflicts(Profile profileOne, Profile profileTwo) {
        return profileOne.getPreference() != profileTwo.getGender() || profileTwo.getPreference() != profileOne.getGender();
    }

    /**
     * Determines if the age difference isn't too big
     * @param profileOne The first profile
     * @param profileTwo The second profile
     * @return If the age difference is too big
     */
    public static boolean ageConflicts(Profile profileOne, Profile profileTwo) {
        int difference = profileOne.getAge() - profileTwo.getAge();
        difference = makePositive(difference);

        return difference > MAX_AGE_DIFF;
    }

    /**
     * Calculates the age score of two profiles
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

        return (int) (MAX_SCORE - 5 - (Math.pow(CALC_AGE_SCORE_FACTOR, difference)));
    }

    /**
     * Calculates the score for having the same interests
     * @param profileOne The first profile
     * @param profileTwo The second profile
     * @return Score between 0 and 100
     */
    public static int calculateSameInterestsScore(Profile profileOne, Profile profileTwo) {
        int sameInterestsCount = 0;

        for (Interest i : profileOne.getInterests()) {
            for (Interest j : profileTwo.getInterests()) {
                if (i.getName().equals(j.getName())) {
                    sameInterestsCount++;
                    break;
                }
            }
            if (sameInterestsCount == MIN_SAME_INTEREST_FOR_MAX_SCORE) {
                return MAX_SCORE;
            }
        }

        return sameInterestsCount == 0 ? 0 : MAX_SCORE - 20 - (int) Math.pow(SAME_INTEREST_SCORE_FACTOR, (MIN_SAME_INTEREST_FOR_MAX_SCORE - sameInterestsCount) * SAME_INTEREST_COUNT_FACTOR);
    }

    private static int makePositive(int val) {
        return val < 0 ? -val : val;
    }

}
