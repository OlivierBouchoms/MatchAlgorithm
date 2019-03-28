package Algorithm;

import Models.Interest;
import Models.Profile;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MatchAlgorithmHelper {

    private static final int MAX_AGE_DIFF = 5;
    private static final int MAX_AGE_DIFF_BEST_SCORE = 2;

    private static final int MAX_SCORE = 100;

    private static final int MIN_SAME_INTEREST_FOR_MAX_SCORE = 3;

    private static final double CALC_AGE_SCORE_FACTOR = 2.12;
    private static final double SAME_INTEREST_SCORE_FACTOR = 2.12;
    private static final double SAME_INTEREST_COUNT_FACTOR = 2.2;

    private static final int MIN_INTEREST_LIKES = 2;

    /**
     * Determines if gender preferences don't conflict
     *
     * @param profileOne The first profile
     * @param profileTwo The second profile
     * @return If the gender preferences conflict
     */
    public static boolean genderConflicts(Profile profileOne, Profile profileTwo) {
        return profileOne.getPreference() != profileTwo.getGender() || profileTwo.getPreference() != profileOne.getGender();
    }

    /**
     * Determines if the age difference isn't too big
     *
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

        return (int) (MAX_SCORE - 5 - (Math.pow(CALC_AGE_SCORE_FACTOR, difference)));
    }

    /**
     * Calculates the score for having the same interests
     *
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

    /**
     * Calculates the score for often liking each others interests
     *
     * @param profileOne The first profile
     * @param profileTwo The second profile
     * @return Score between 0 and 100
     */
    public static int calculateLikedEachOthersInterestsScore(Profile profileOne, Profile profileTwo) {
        return -1;
    }

    /**
     * Determines the interests that are most often liked by the user
     *
     * @param profile The profile to determine the favorite interests of
     * @return The interests that are most often liked by the user
     */
    public static Collection<Interest> getFavoriteInterests(Profile profile) {
        // Per interest aantal occurences bijhouden
        Collection<Interest> favoriteInterests = new ArrayDeque<>();

        Map<String, Integer> likes = new HashMap<>();
        Map<String, Integer> dislikes = new HashMap<>();

        for (Interest i : profile.getLikedInterests()) {
            if (!likes.containsKey(i.getName())) {
                likes.put(i.getName(), 1);
            } else {
                likes.replace(i.getName(), likes.get(i.getName()) + 1);
            }
        }

        for (Interest i : profile.getDislikedInterests()) {
            if (!dislikes.containsKey(i.getName())) {
                dislikes.put(i.getName(), 1);
            } else {
                dislikes.replace(i.getName(), dislikes.get(i.getName()) + 1);
            }
        }

        likes.forEach((key, numberOfLikes) -> {
            // Check if the interest has enough likes
            if (numberOfLikes >= MIN_INTEREST_LIKES) {
                // Check if it was ever disliked
                if (!dislikes.containsKey(key)) {
                    // Never dislikes, add to favorites
                    favoriteInterests.add(new Interest(-1, key));
                } else {
                    int numberOfDislikes = dislikes.get(key);
                    // If the interest was likes at least two third of the time, add it to the favorites
                    int totalActions = numberOfLikes + numberOfDislikes;
                    if ((double) numberOfLikes / totalActions >= ((double)2/3)) {
                        favoriteInterests.add(new Interest(-1, key));
                    }
                }
            }
        });

        return favoriteInterests;
    }

    private static int makePositive(int val) {
        return val < 0 ? -val : val;
    }

}
