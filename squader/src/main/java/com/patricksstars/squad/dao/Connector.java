
package com.patricksstars.squad.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Connector {
    
    public static Connection getConnection () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties props = getDBProperties();
            Connection con = DriverManager.getConnection(props.getProperty("MYSQL_DB_URL"),
                    props.getProperty("MYSQL_DB_USERNAME"), props.getProperty("MYSQL_DB_PASSWORD"));
            return con;
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        }
    }
    
    private static Properties getDBProperties() {
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            //todo @Mike just comment uncomment this lol
            fis = new FileInputStream("/Users/Casa/projects/squader/src/main/resources/db.properties");
//            fis = new FileInputStream("/Users/mikephe/NetBeansProjects/squader/src/main/resources/db.properties");
            props.load(fis);
        } catch (IOException e) {
            System.out.println("db.properties is not found");
        }
        return props;
    }

    static void close(Connection con)
    {
        try {
            con.close();
        } catch (Exception e) {
            
        }
    }
}
