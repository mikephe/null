
package com.patricksstars.squad;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility class for managing HTTP Session and Session attributes ie global "variables"
 * 
 */
public class Util
{
    public static HttpSession getSession() {
       return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }
    
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
    
    public static int getCurrentUser() {
        try {
            return Integer.parseInt(getSession().getAttribute("userId").toString());    
        } catch (Exception e) {
            return -1;
        }
    }
    
}
