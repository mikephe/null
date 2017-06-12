/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patricksstars.squad.beans;

import com.patricksstars.squad.Util;
import com.patricksstars.squad.dao.UserDAO;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Casa
 */
@ManagedBean
@RequestScoped
public class CreateAccountBean
{
    private String username;
    private String password;
    private String first;
    private String last;
    private String email;
    private String phone;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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
    
    public String createAccount() {
        System.out.format("%s %s %s %s %s", username, password, first, last, email, phone);
        if(!username.isEmpty() && !password.isEmpty() && !first.isEmpty() 
                && !last.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
            System.out.println("create account");
            int userId = UserDAO.createAccount(username, password, first, last, email, phone);
            if (userId >= 0) {
                HttpSession session = Util.getSession();
                session.setAttribute("userId", userId);
                return "home?faces-redirect=true";
            } else {
                return "";
            }
            
        } else {
            System.out.println("don't create account, field empty");
            return "";
        }
    }
    
}
