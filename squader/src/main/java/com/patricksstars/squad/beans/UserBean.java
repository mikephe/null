
package com.patricksstars.squad.beans;

import com.patricksstars.squad.models.User;
import com.patricksstars.squad.Util;
import com.patricksstars.squad.dao.UserDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;

/**
 *
 * @author Casa
 */
@ManagedBean
@SessionScoped
public class UserBean implements Serializable
{
    
    private static final long serialVersionUID = 1L;
	
    private User user;
    private String username;

    public String getUsername()
    {
        this.username = Integer.toString(Util.getCurrentUser());
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public User getUser() {
        this.user = UserDAO.getCurrentUser();
        return this.user;
    }
        
    public User setUser() {
        return this.user;
    }
    

}
