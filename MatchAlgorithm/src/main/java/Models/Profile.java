package Models;

import java.util.ArrayDeque;
import java.util.Collection;

public class Profile {

    private int id;
    private String name;
    private Collection<Interest> interests;
    private Collection<Interest> likedInterests;
    private Collection<Interest> dislikedInterest;
    private Gender gender;
    private Gender preference;
    private int age;

    public Profile() {
        interests = new ArrayDeque<>();
    }

    public Profile(int id, String name, Collection<Interest> interests, Gender gender) {
        this.id = id;
        this.name = name;
        this.interests = interests;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Interest> getInterests() {
        return interests;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInterests(Collection<Interest> interests) {
        this.interests = interests;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Collection<Interest> getLikedInterests() {
        return likedInterests;
    }

    public void setLikedInterests(Collection<Interest> likedInterests) {
        this.likedInterests = likedInterests;
    }

    public Collection<Interest> getDislikedInterest() {
        return dislikedInterest;
    }

    public void setDislikedInterest(Collection<Interest> dislikedInterest) {
        this.dislikedInterest = dislikedInterest;
    }

    public Gender getPreference() {
        return preference;
    }

    public void setPreference(Gender preference) {
        this.preference = preference;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
