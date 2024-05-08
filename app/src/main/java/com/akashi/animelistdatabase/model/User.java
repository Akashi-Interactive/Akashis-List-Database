package com.akashi.animelistdatabase.model;

import java.util.Date;
import java.util.List;

/// <summary> <c>User</c> represents an user data in the app. </summary>
public class User {
    /// <summary> The user's id. </summary>
    private String id;
    /// <summary> The user's nickname. </summary>
    private String nickname;
    /// <summary> The user's name. </summary>
    private String name;
    /// <summary> The user's last name. </summary>
    private String lastName;
    /// <summary> The user's password. </summary>
    private String password;
    /// <summary> The user's register date. </summary>
    private Date registerDate;
    /// <summary> The user's last connection date. </summary>
    private Date lastConnection;
    /// <summary> The user's birthday. </summary>
    private Date birthday;
    /// <summary> The user's friends. </summary>
    private List<String> friends;
    /// <summary> The user's favorite animes. </summary>
    private List<String> favoriteAnimes;
    /// <summary> The user's favorite mangas. </summary>
    private List<String> favoriteMangas;
    /// <summary> The user's favorite characters. </summary>
    private List<String> favoriteCharacters;
    /// <summary> The user's profile picture. </summary>
    private String profilePicture;

    // Constructor
    public User() {
    }

    public User ( String nickname, String password, Date registerDate) {
        this.nickname = nickname;
        this.password = password;
        this.registerDate = registerDate;
    }

    public User(
            String id,
            String nickname,
            String name,
            String lastName,
            String password,
            Date registerDate,
            Date lastConnection,
            Date birthday,
            List<String> friends,
            List<String> favoriteAnimes,
            List<String> favoriteMangas,
            List<String> favoriteCharacters,
            String profilePicture
    ) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.registerDate = registerDate;
        this.lastConnection = lastConnection;
        this.birthday = birthday;
        this.friends = friends;
        this.favoriteAnimes = favoriteAnimes;
        this.favoriteMangas = favoriteMangas;
        this.favoriteCharacters = favoriteCharacters;
        this.profilePicture = profilePicture;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFavoriteAnimes() {
        return favoriteAnimes;
    }

    public void setFavoriteAnimes(List<String> favoriteAnimes) {
        this.favoriteAnimes = favoriteAnimes;
    }

    public List<String> getFavoriteMangas() {
        return favoriteMangas;
    }

    public void setFavoriteMangas(List<String> favoriteMangas) {
        this.favoriteMangas = favoriteMangas;
    }

    public List<String> getFavoriteCharacters() {
        return favoriteCharacters;
    }

    public void setFavoriteCharacters(List<String> favoriteCharacters) {
        this.favoriteCharacters = favoriteCharacters;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
