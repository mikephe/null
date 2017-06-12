import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

/**
 * Created by SteveWang on 11/27/15.
 */
public class Events {
    int id;
    String name;
    String locationName;
    String address;
    int ticketsLeft;
    Date date;
    Time time;
    boolean isCanceled;

    public Events(ResultSet rs) {
        try {
            id = rs.getInt("eventID");
            name = rs.getString("name");
            locationName = rs.getString("locname");
            address = rs.getString("address");
            ticketsLeft = rs.getInt("numTicketsLeft");
            date = rs.getDate("date");
            time = rs.getTime("time");
            isCanceled = rs.getBoolean("isCanceled");
        }
        catch(SQLException e) {
            System.out.println("ERROR CREATING EVENT");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Events{" +
                "name='" + name + '\'' +
                ", locationName='" + locationName + '\'' +
                ", address='" + address + '\'' +
                ", ticketsLeft=" + ticketsLeft +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
