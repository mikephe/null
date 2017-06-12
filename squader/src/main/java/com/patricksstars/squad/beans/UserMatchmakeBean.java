
package com.patricksstars.squad.beans;

import com.patricksstars.squad.models.User;
import com.patricksstars.squad.Util;
import com.patricksstars.squad.dao.ProjectDao;
import com.patricksstars.squad.dao.UserDAO;
import com.patricksstars.squad.models.Project;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Casa
 */
@ManagedBean
@RequestScoped
public class UserMatchmakeBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Project> projects;

    public ArrayList<Project> getProjects()
    {
        try {
            projects = ProjectDao.getProjectsToMatch();
        } catch (Exception e) {
            projects = new ArrayList<Project>();
        }
        return projects;
    }

    public void setProjects(ArrayList<Project> projects)
    {
        this.projects = projects;
    }
    
    
    
}
