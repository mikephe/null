/**
 * Course: CS 157A
 * Assignment: Final Project
 * Team Name: String Cheese
 * Members: Mike Phe, Mathew Pronge, Steve Wang
 */

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class DataSourceFactory {
    public static DataSource getMySQLDataSource() {

        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource mysqlDS = null;
        try {
            fis = new FileInputStream("/Users/mikephe/IdeaProjects/cs157A-project/src/main/resources/db.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            System.out.println("db.properties is not found");
            e.printStackTrace();
        }
        return mysqlDS;

    }
}
public class Main {
    public static void main(String[] args) {
        DataSource ds = DataSourceFactory.getMySQLDataSource();
        new LandingPageView(ds);
    }
}
