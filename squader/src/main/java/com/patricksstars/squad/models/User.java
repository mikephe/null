
package com.patricksstars.squad.models;

import java.util.ArrayList;

/**
 * A class that describes a user of the app.
 * 
 * @author Will Simons
 */
public class User
{
    private int userId;
    private String username;
    private String first;
    private String last;
    private String email;
    private String phone;
    private String tagline;
    private String bio;
    private ArrayList<String> skills;

    public User()
    {
    }

    public User(int userId, String username, String first, String last, String email, String phone, String tagline, String bio, ArrayList<String> skills)
    {
        this.userId = userId;
        this.username = username;
        this.first = first;
        this.last = last;
        this.email = email;
        this.phone = phone;
        this.tagline = tagline;
        this.bio = bio;
        this.skills = skills;
    }
    
    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    
    
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getFirst()
    {
        return first;
    }

    public void setFirst(String first)
    {
        this.first = first;
    }

    public String getLast()
    {
        return last;
    }

    public void setLast(String last)
    {
        this.last = last;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getTagline()
    {
        return tagline;
    }

    public void setTagline(String tagline)
    {
        this.tagline = tagline;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public ArrayList<String> getSkills()
    {
        return skills;
    }

    public void setSkills(ArrayList<String> skills)
    {
        this.skills = skills;
    }
    
}
