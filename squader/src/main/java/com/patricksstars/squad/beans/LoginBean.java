package com.patricksstars.squad.beans;

import com.patricksstars.squad.Util;
import com.patricksstars.squad.dao.Connector;
import com.patricksstars.squad.dao.UserDAO;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.faces.bean.SessionScoped;
 
@ManagedBean
@SessionScoped
public class LoginBean {
    
    private String username, password;
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
   
    public String login() {
        int userId = UserDAO.login(username, password);
        if(username != null && password != null && userId >=0) {
            HttpSession session = Util.getSession();
            session.setAttribute("userId", userId);
            System.out.println("login");
            return "home?faces-redirect=true";            
        } else {
            System.out.println("Couldn't login");
            return "";
        }
    }
    
    public String logout() {
        HttpSession session = Util.getSession();
        session.invalidate();
        return "hello?faces-redirect=true";
    }

}