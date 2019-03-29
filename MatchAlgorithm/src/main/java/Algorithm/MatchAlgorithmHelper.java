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
    private static final int MIN_FAV_INTEREST_OCCURS_IN_OTHER_PROFILE_FOR_MAX_SCORE = 3;

    private static final double CALC_AGE_SCORE_FACTOR = 2.12;
    private static final double SAME_INTEREST_SCORE_FACTOR = 2.12;
    private static final double SAME_INTEREST_COUNT_FACTOR = 2.2;

    private static final int MIN_INTEREST_LIKES = 2;

    private static final double AGE_SCORE_RATIO = 0.20;
    private static final double SAME_INTERESTS_SCORE_RATIO = 0.40;
    private static final double LIKED_EACH_OTHERS_INTERESTS_SCORE_RATIO = 0.40;

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
                break;
            }
        }

        return convertCountToScore(sameInterestsCount);
    }

    /**
     * Calculates the score for often liking each others interests
     *
     * @param profileOneFavorites The favorite interests of the first profile
     * @param profileTwoFavorites The favorite interests of the second profile
     * @return Score between 0 and 100
     */
    public static int calculateLikedEachOthersInterestsScore(Collection<Interest> profileOneFavorites, Collection<Interest> profileTwoFavorites, Profile profileOne, Profile profileTwo) {
        int sameInterestOccurrencesOne = getSameOccurrences(profileOneFavorites, profileTwo.getInterests());
        int sameInterestOccurrencesTwo = getSameOccurrences(profileTwoFavorites, profileOne.getInterests());

        int scoreOne = convertCountToScore(sameInterestOccurrencesOne);
        int scoreTwo = convertCountToScore(sameInterestOccurrencesTwo);
        return (scoreOne + scoreTwo) / 2;
    }

    /**
     * Converts a count (0 - 3) to a score
     *
     * @param count The count the covert
     * @return Score between 0 and 100
     */
    private static int convertCountToScore(int count) {
        if (count == 3) {
            return MAX_SCORE;
        }
        if (count == 0) {
            return 0;
        }
        return MAX_SCORE - 20 - (int) Math.pow(SAME_INTEREST_SCORE_FACTOR, (MIN_SAME_INTEREST_FOR_MAX_SCORE - count) * SAME_INTEREST_COUNT_FACTOR);
    }

    /**
     * Returns the number of objects that both occur in the Collections
     *
     * @param collectionOne The first collection
     * @param collectionTwo The second collection
     * @return
     */
    public static int getSameOccurrences(Collection<Interest> collectionOne, Collection<Interest> collectionTwo) {
        int occurrences = 0;
        for (Interest i : collectionOne) {
            for (Interest j : collectionTwo) {
                if (i.equals(j)) {
                    occurrences++;
                    break;
                }
            }
            if (occurrences == MIN_FAV_INTEREST_OCCURS_IN_OTHER_PROFILE_FOR_MAX_SCORE) {
                break;
            }
        }
        return occurrences;
    }

    /**
     * Determines the interests that are most often liked by the user
     *
     * @param profile The profile to determine the favorite interests of
     * @return The interests that are most often liked by the user
     */
    public static Collection<Interest> getFavoriteInterests(Profile profile) {
        Collection<Interest> favoriteInterests = new ArrayDeque<>();

        Map<Interest, Integer> likes = new HashMap<>();
        Map<Interest, Integer> dislikes = new HashMap<>();

        for (Interest i : profile.getLikedInterests()) {
            if (!likes.containsKey(i)) {
                likes.put(i, 1);
            } else {
                likes.replace(i, likes.get(i) + 1);
            }
        }

        for (Interest i : profile.getDislikedInterests()) {
            if (!dislikes.containsKey(i)) {
                dislikes.put(i, 1);
            } else {
                dislikes.replace(i, dislikes.get(i) + 1);
            }
        }

        likes.forEach((interest, numberOfLikes) -> {
            // Check if the interest has enough likes
            if (numberOfLikes >= MIN_INTEREST_LIKES) {
                int numberOfDislikes = dislikes.getOrDefault(interest, 0);
                // If the interest was liked at least two third of the time, add it to the favorites
                int totalActions = numberOfLikes + numberOfDislikes;
                if (numberOfDislikes == 0 || (double) numberOfLikes / totalActions >= ((double) 2 / 3)) {
                    favoriteInterests.add(interest);
                }
            }
        });

        return favoriteInterests;
    }

    /**
     * Calculates the total score given the other scores and ratios
     *
     * @param ageScore                      Score for age
     * @param sameInterestsScore            Score for same interest
     * @param likedEachOthersInterestsScore Score for liking each others interests
     * @return Score
     */
    public static int calculateScore(int ageScore, int sameInterestsScore, int likedEachOthersInterestsScore) {
        return (int) ((ageScore * AGE_SCORE_RATIO) + (sameInterestsScore * SAME_INTERESTS_SCORE_RATIO) + (likedEachOthersInterestsScore * LIKED_EACH_OTHERS_INTERESTS_SCORE_RATIO));
    }

    /**
     * Makes a number positive if it is negative
     *
     * @param val value to make positive
     * @return Positive value
     */
    private static int makePositive(int val) {
        return val < 0 ? -val : val;
    }

    private MatchAlgorithmHelper() {
    }

}
