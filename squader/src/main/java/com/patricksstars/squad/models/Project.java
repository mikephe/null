/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.patricksstars.squad.models;

import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author mikephe
 */
public class Project {
    private int id;
    private String name;
    private String desc;
    private ArrayList<String> tags;
    private Date dateStarted;
    
    private ArrayList<User> members;

    public Project(int id, String name, String desc, ArrayList<String> tags, Date date) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.tags = tags;
        this.dateStarted = date;
    }
    
    
    public ArrayList<User> getMembers()
    {
        return members;
    }

    public void setMembers(ArrayList<User> members)
    {
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

        public ArrayList<String> getTags()
    {
        return tags;
    }

    public void setTags(ArrayList<String> tags)
    {
        this.tags = tags;
    }

    public Date getDateStarted()
    {
        return dateStarted;
    }

    public void setDateStarted(Date dateStarted)
    {
        this.dateStarted = dateStarted;
    }

}
