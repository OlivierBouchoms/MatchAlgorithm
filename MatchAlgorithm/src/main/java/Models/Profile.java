package Models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Profile {

    private int id;
    private String name;
    private Collection<Interest> interests;
    private List<Interest> likedInterests;
    private List<Interest> dislikedInterests;
    private Gender gender;
    private Gender preference;
    private int age;

    public Profile() {
        interests = new ArrayDeque<>();
        likedInterests = new ArrayList<>();
        dislikedInterests = new ArrayList<>();
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

    public List<Interest> getLikedInterests() {
        return likedInterests;
    }

    public void setLikedInterests(List<Interest> likedInterests) {
        this.likedInterests = likedInterests;
    }

    public List<Interest> getDislikedInterests() {
        return dislikedInterests;
    }

    public void setDislikedInterests(List<Interest> dislikedInterest) {
        this.dislikedInterests = dislikedInterest;
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
