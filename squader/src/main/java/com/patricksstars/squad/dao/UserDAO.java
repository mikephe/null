
package com.patricksstars.squad.dao;
import com.patricksstars.squad.Util;
import com.patricksstars.squad.models.User;
import java.sql.*;
import java.util.ArrayList;
import javax.sql.DataSource;

/**
 * 
 * @author Patricks Stars
 */

//Implementing this: http://www.javaknowledge.info/authentication-based-secure-login-logout-using-jsf-2-0-and-primefaces-3-4-1/
public class UserDAO
{
    
    public static int login(String username, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        try
        {
            con = Connector.getConnection();
            ps = con.prepareStatement("SELECT id, first, last, username, password FROM squaders WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                System.out.println("Logged in " + rs.getString("first") + " " + rs.getString("last"));
                return rs.getInt("id");
            } else {
                System.out.println("Login failed");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            Connector.close(con);
        }
    }
    
    public static int createAccount(String username, String password,
                              String first, String last,
                              String email, String phone) {
        Connection con = null;
        PreparedStatement ps = null;
        try
        {
            con = Connector.getConnection();
            ps = con.prepareStatement("INSERT INTO squaders(username, password, first, last, email, phone) " +
                        "VALUES(?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, first);
            ps.setString(4, last);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs.next()) {
                //gets the "id" value, which is the first column in the squaders table
                return rs.getInt(1);
            } else {
                System.out.println("Login failed");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            Connector.close(con);
        }
    }

    public static void getUsers()
    {
        String query = "SELECT * FROM squaders;";
        Connection con = null;
        PreparedStatement ps = null;
        try
        {
            con = Connector.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                System.out.println(rs.getString("username") + "\n"+
                            rs.getString("password") + "\n" + rs.getString("first") +
                            "\n"  + rs.getString("last") + "\n" + rs.getString("email") +
                            "\n" + rs.getString("phone") + "\n\n");
            } else {
                System.out.println("Login failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connector.close(con);
        }
    }
    
    public static User getUser(int uid) {
        System.out.println("getcurrentuser");
        Connection con = null;
        PreparedStatement ps = null;
        try {
            
            con = Connector.getConnection();
            ps = con.prepareStatement("SELECT * FROM squaders WHERE id="+uid+";");
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                System.out.println(rs.getString("username"));
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String first = rs.getString("first");
                String last = rs.getString("last");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String tagline = "This is a tagluine";
                String bio = "This is a bio";
                ArrayList<String> skills = new ArrayList<String> ();
                skills.add("java");
                skills.add("cats");
                skills.add("coffee");
                return new User(id, username, first, last, email, phone, tagline, bio, skills); 
            } else {
                System.out.println("Couldn't find user");
                return new User(); 
            }
        } catch (Exception e) {
            System.out.println("Error, couldn't find user");
            return new User();
        }
    }

    public static User getCurrentUser()
    {
        System.out.println("getcurrentuser");
        Connection con = null;
        PreparedStatement ps = null;
        try {
            int userId = Integer.parseInt( Util.getSession().getAttribute("userId").toString());
            con = Connector.getConnection();
            ps = con.prepareStatement("SELECT * FROM squaders WHERE id="+userId+";");
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                System.out.println(rs.getString("username"));
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String first = rs.getString("first");
                String last = rs.getString("last");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String tagline = "This is a tagluine";
                String bio = "This is a bio";
                ArrayList<String> skills = new ArrayList<String> ();
                skills.add("java");
                skills.add("cats");
                skills.add("coffee");
                return new User(id, username, first, last, email, phone, tagline, bio, skills); 
            } else {
                System.out.println("Couldn't find user");
                return new User(); 
            }
        } catch (Exception e) {
            System.out.println("Error, couldn't find user");
            return new User();
        }
    }

}
