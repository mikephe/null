/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.patricksstars.squad.dao;
import com.patricksstars.squad.Util;
import com.patricksstars.squad.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author mikephe
 */
public class ProjectDao {
    
    public static ArrayList<Project> getProjectsToMatch()
    {
        ArrayList<Project> projectList = new ArrayList<Project>();
        ArrayList<String> parsedSkills = new ArrayList<String>();
        // todo: no need for parameter binding, just user session id
        String query = "SELECT project.id, project.name, project.description, project.skillsReq FROM project, matches where project.id != matches.pID and ? != matches.uid;";
        Connection con = null;
        PreparedStatement ps = null;
        try
        {
            con = Connector.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //todo Will: I changed skillReq to tags in Project.java(Easier, idk lol)
               // todo: parse skills string if it's separated by commas
               parsedSkills.add(rs.getString("skillReq"));
               projectList.add(new Project(Integer.parseInt(rs.getString("id")),
                                rs.getString("name"), rs.getString("description"),
                                parsedSkills, new Date()));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get data;");
            e.printStackTrace();
        } finally {
            Connector.close(con);
        }
        
        return projectList;
    }
    
    public static ArrayList<User> matchedMyProject() {
        ArrayList<User> users = new ArrayList<User>();
        // todo: need to edit query
        String query = "SELECT id, username, first, last, email, phone FROM projects, squaders,  where projects.pid == matches.pid";
        Connection con = null;
        PreparedStatement ps = null;
        try
        {
            con = Connector.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // todo: add other attributes
               users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("first"),
                                    rs.getString("last"), rs.getString("email"), rs.getString("phone"),
                                    "", "", new ArrayList<String>()));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get data;");
            e.printStackTrace();
        } finally {
            Connector.close(con);
        }
        return users;
    }
    
    public static ArrayList<Project> matchedProjects() {
        ArrayList<Project> projects = new ArrayList<Project>();
        ArrayList<String> parsedSkills = new ArrayList<String>();
        
        // todo: need to edit query
        String query = "select * from projects join teams where projects.uid == uid AND teams.permission == 3";
        Connection con = null;
        PreparedStatement ps = null;
        try
        {
            con = Connector.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // todo: add other attributes
               parsedSkills.add(rs.getString("skillReq"));
               projects.add(new Project(Integer.parseInt(rs.getString("id")),
                                rs.getString("name"), rs.getString("description"),
                                parsedSkills, new Date()));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get data;");
            e.printStackTrace();
        } finally {
            Connector.close(con);
        }
        return projects;
    }
    
    public static ArrayList<Project> partOfProjects() {
        ArrayList<Project> projects = new ArrayList<Project>();
        ArrayList<String> parsedTags = new ArrayList<String>();
        ArrayList<User> users = new ArrayList<User>();
        // todo: need to edit query
        String query = "select * \n" +
                        "from project inner join teams \n" +
                        "on project.id=teams.pID\n" +
                        "where teams.uid=?;";
        
        String userQuery = "select uID\n" +
                            "from project inner join teams\n" +
                            "on project.id=teams.pID\n" +
                            "where teams.pID=?;";
        Connection con = null;
        PreparedStatement ps = null;
        
        try
        {
            con = Connector.getConnection();
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, Util.getCurrentUser());
            ResultSet rs = ps.executeQuery();
            
            System.out.println("current u "+Util.getCurrentUser());
 
            while (rs.next()) {
                
                //parse tags (comma delimted)
               String tags = rs.getString("tags");
               parsedTags = new ArrayList<String>(Arrays.asList( tags.split("\\s*,\\s*")));
               
               //Construct project object
               Project p = new Project(Integer.parseInt(rs.getString("id")),
                                        rs.getString("name"),
                                        rs.getString("description"),
                                        parsedTags,
                                        new Date());
               
//               ps = con.prepareStatement(userQuery);
//               ps.setInt(1, Integer.parseInt(rs.getString("id")));
               
//               ResultSet userResults = ps.executeQuery();
//               
//               while (userResults.next()) {
//                   int uId = userResults.getInt("uID");
//                   User u = UserDAO.getUser(uId);
//                   users.add(u);
//               }
//               
//               p.setMembers(users);
               projects.add(p);
                System.out.println("Project "+p.getId());
            }
            
        } catch (SQLException e) {
            System.out.println("Failed to get data;");
            e.printStackTrace();
        } finally {
            Connector.close(con);
        }
        System.out.println(projects.size());
        return projects;
    }
}
