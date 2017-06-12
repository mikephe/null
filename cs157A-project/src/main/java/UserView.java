/**
 * Created by SteveWang on 11/27/15.
 */

import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;

public class UserView {
    private CardLayout cardPanels;
    private JPanel panels;
    private JPanel userLogin;
    private JFrame frame;
    private DataSource ds;
    private Connection connection;
    private int custID;

    private String userName;
    private ArrayList<Events> events = new ArrayList<>();
    private ArrayList<Events> userEvents = new ArrayList<>();

    public UserView(DataSource ds, JFrame frame, JPanel panels,  CardLayout cardPanels, Connection connection) {
        this.ds = ds;
        this.frame = frame;
        this.panels = panels;
        this.cardPanels = cardPanels;
        this.connection = connection;

        launchUserView();
    }

    public void launchUserView() {
        userLogin= new JPanel();
        JTextField username = new JTextField(10);
        JPasswordField password = new JPasswordField(10);

        JButton backButton = new JButton("Back");
        JButton buttonLogin = new JButton("Login");

        userLogin.add(new JLabel("Username: "));
        userLogin.add(username);
        userLogin.add(new JLabel("Password: "));
        userLogin.add(password);
        userLogin.add(buttonLogin);
        userLogin.add(backButton);

        panels.add(userLogin, "UserLogin");
        cardPanels.show(panels, "UserLogin");

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(authenticate(username.getText(), new String(password.getPassword()))) {
                    JOptionPane.showMessageDialog(frame,
                            "Success!");
                    for (Component C : userLogin.getComponents())
                    {
                        if (C instanceof JTextField){
                            ((JTextField) C).setText("");
                        }
                    }
                    launchUserHome();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Invalid username or password!");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "Home");
            }
        });
    }

    public void launchUserHome() {
        JPanel userHome = new JPanel();
        JButton browseEvents = new JButton("Browse Events");
        JButton browseByEvents = new JButton("Browse by Event: ");
        JButton browseByLoc = new JButton("Browse by Venue/Location: ");
        JButton myReservations = new JButton("My Reservations");
        JTextField eventName = new JTextField(10);
        JTextField locName = new JTextField(10);
        JButton logout = new JButton("Logout");

        userHome.add(new JLabel("Welcome User: " + userName + "!")); //Insert name
        userHome.add(myReservations);
        userHome.add(browseEvents);
        userHome.add(browseByEvents);
        userHome.add(eventName);
        userHome.add(browseByLoc);
        userHome.add(locName);
        userHome.add(logout);

        panels.add(userHome, "UserHome");
        cardPanels.show(panels, "UserHome");

        browseEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadEvents();
                launchBrowseEvents();
            }
        });

        browseByEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadEventsByEvent(eventName.getText());
                launchBrowseEvents();
            }
        });

        browseByLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadEventsByLoc(locName.getText());
                launchBrowseEvents();
            }
        });

        myReservations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMyEvents();
                launchMyReservations();
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "Home");
            }
        });
    }

    public void launchBrowseEvents() {
        JPanel browseEvents = new JPanel();
        JPanel eventList = new JPanel();
        JButton back = new JButton("Back");

        for(Events event : events) {
            JTextArea text = new JTextArea(event.name + "\n---------------\n" + "Number of tickets Left: " +
                    event.ticketsLeft + "\n" + "Time: " + event.time +
                    "\n" + "Date: " + event.date + "\n" + "Venue: " + event.locationName +
                    "\n" + "Address: " + event.address + "\n\n");

            text.setEditable(false);
            eventList.add(text);

            text.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    launchReserveTicket(event);
                }
            });
        }

        JScrollPane pane = new JScrollPane(eventList);
        pane.setPreferredSize(new Dimension(250, 200));
        browseEvents.add(pane);
        browseEvents.add(back);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panels.add(browseEvents, "BrowseEvents");
        cardPanels.show(panels, "BrowseEvents");

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "UserHome");
            }
        });
    }

    public void launchMyReservations() {
        JPanel myReservations = new JPanel();
        JPanel eventList = new JPanel();
        JButton back = new JButton("Back");

        for(Events event : userEvents) {
            JTextArea text = new JTextArea(event.name + "\n---------------\n" +
                    "\n" + "Time: " + event.time +
                    "\n" + "Date: " + event.date + "\n" + "Venue: " + event.locationName +
                    "\n" + "Address: " + event.address + "\n\n");

            text.setEditable(false);
            eventList.add(text);

            text.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    launchMyTicketManager(event);
                }
            });
        }

        JScrollPane pane = new JScrollPane(eventList);
        pane.setPreferredSize(new Dimension(250, 200));
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        myReservations.add(pane);
        myReservations.add(back);

        panels.add(myReservations, "MyReservations");
        cardPanels.show(panels, "MyReservations");

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "UserHome");
            }
        });
    }

    public void launchMyTicketManager(Events event) {
        JPanel ticketManager = new JPanel();
        JTextArea text = new JTextArea(event.name + "\n---------------\n" +
                "\n" + "Time: " + event.time +
                "\n" + "Date: " + event.date + "\n" + "Venue: " + event.locationName +
                "\n" + "Address: " + event.address + "\n\n");
        JButton delete = new JButton("Delete Ticket");
        JButton back = new JButton("Back");

        ticketManager.add(text);
        ticketManager.add(delete);
        ticketManager.add(back);

        panels.add(ticketManager, "TicketManager");
        cardPanels.show(panels, "TicketManager");

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRes(custID, event.id);
                cardPanels.show(panels, "UserHome");
            }
        });


        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "MyReservations");
            }
        });
    }

    public void deleteRes(int cID, int eID) {
        CallableStatement call = null;

        try {
            call = connection.prepareCall("{call deleteTicketProcedure(?, ?)}");
            call.setInt(1, cID);
            call.setInt(2, eID);
            call.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void launchReserveTicket(Events event) {
        JPanel reserve = new JPanel();
        JButton confirm = new JButton("Confirm Reservation");
        JButton back = new JButton("Back");

        JTextArea text = new JTextArea(event.name + "\n---------------\n" + "Number of tickets Left: " +
                event.ticketsLeft + "\n" + "Time: " + event.time +
                "\n" + "Date: " + event.date + "\n" + "Venue: " + event.locationName +
                "\n" + "Address: " + event.address + "\n\n");

        reserve.add(text);
        reserve.add(confirm);
        reserve.add(back);

        panels.add(reserve, "ReserveTicket");
        cardPanels.show(panels, "ReserveTicket");

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reserveProc(custID, event.id);
                cardPanels.show(panels, "UserHome");
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "BrowseEvents");
            }
        });
    }

    public void reserveProc(int cID, int eID) {
        CallableStatement call = null;

        try {
            call = connection.prepareCall("{call reserveTicketProcedure(?, ?)}");
            call.setInt(1, cID);
            call.setInt(2, eID);
            call.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadMyEvents() {
        userEvents.clear();
        String query = "SELECT EVENT_TABLE.eventID, EVENT_TABLE.name, numTicketsLeft, time, date, isCanceled, LOCATION.name AS locName, address " +
                "FROM EVENT_TABLE, LOCATION, TICKET, RESERVATION, CUSTOMER " +
                "WHERE CUSTOMER.custID = RESERVATION.custID AND TICKET.ticketID = RESERVATION.tickID " +
                "AND Location.locID = EVENT_TABLE.locID AND TICKET.eventID = EVENT_TABLE.eventID " +
                "AND CUSTOMER.custID = " + custID + ";";

        if(connection != null) {
            System.out.println("You made it, take control your database now!");
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Events event = new Events(rs);
                    userEvents.add(event);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    public void loadEvents() {
        events.clear();
        String query = "SELECT EVENT_TABLE.eventID, EVENT_TABLE.name, numTicketsLeft, time, date, isCanceled, LOCATION.name AS locName, address " +
                "FROM EVENT_TABLE, LOCATION " +
                "WHERE isCanceled = FALSE AND numTicketsLeft > 0 AND EVENT_TABLE.locID =  " +
                "LOCATION.locID;";

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Events event = new Events(rs);
                    events.add(event);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    public void loadEventsByEvent(String name) {
        events.clear();
        String query = "SELECT EVENT_TABLE.eventID, EVENT_TABLE.name, numTicketsLeft, time, date, isCanceled, LOCATION.name AS locName, address " +
                "FROM EVENT_TABLE, LOCATION " +
                "WHERE isCanceled = FALSE AND numTicketsLeft > 0 AND EVENT_TABLE.locID =  " +
                "LOCATION.locID AND EVENT_TABLE.name LIKE '%" + name + "%';";

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Events event = new Events(rs);
                    events.add(event);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    public void loadEventsByLoc(String name) {
        events.clear();
        String query = "SELECT EVENT_TABLE.eventID, EVENT_TABLE.name, numTicketsLeft, time, date, isCanceled, LOCATION.name AS locName, address " +
                "FROM EVENT_TABLE, LOCATION " +
                "WHERE isCanceled = FALSE AND numTicketsLeft > 0 AND EVENT_TABLE.locID =  " +
                "LOCATION.locID AND LOCATION.name LIKE '%" + name + "%';";

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Events event = new Events(rs);
                    events.add(event);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    public boolean authenticate(String username, String password) {
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                stmt = connection.prepareStatement("SELECT custID, first, last, username, password FROM CUSTOMER WHERE username=? AND password=?");
                stmt.setString(1, username);
                stmt.setString(2, password);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Passed!");
                    this.userName = rs.getString("first") + " " + rs.getString("last");
                    this.custID = Integer.parseInt(rs.getString("custID"));
                    return true;
                } else {
                    System.out.println("Failed!");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection!");
        }
        return false;
    }
}
