/**
 * Created by SteveWang on 11/27/15.
 */

import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Calendar;

public class AdminView {
    private CardLayout cardPanels;
    private JPanel panels;
    private JPanel adminLogin;
    private JFrame frame;
    private DataSource ds;
    private Connection connection;

    private String adminName;
    private ArrayList<Events> events = new ArrayList<>();
    private ArrayList<String> locNames = new ArrayList<>();

    public AdminView(DataSource ds, JFrame frame, JPanel panels,  CardLayout cardPanels, Connection connection) {
        this.ds = ds;
        this.frame = frame;
        this.panels = panels;
        this.cardPanels = cardPanels;
        this.connection = connection;

        launchAdminView();
    }

    public void launchAdminView() {
        adminLogin = new JPanel();

        System.out.println("Admin View");

        JTextField username = new JTextField(10);
        JPasswordField password = new JPasswordField(10);

        JButton backButton = new JButton("Back");
        JButton buttonLogin = new JButton("Login");

        adminLogin.add(new JLabel("Username: "));
        adminLogin.add(username);
        adminLogin.add(new JLabel("Password: "));
        adminLogin.add(password);
        adminLogin.add(buttonLogin);
        adminLogin.add(backButton);

        panels.add(adminLogin, "AdminLogin");
        cardPanels.show(panels, "AdminLogin");

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(authenticate(username.getText(), new String(password.getPassword()))) {
                    JOptionPane.showMessageDialog(frame,
                            "Success!");
                    for (Component C : adminLogin.getComponents())
                    {
                        if (C instanceof JTextField){
                            ((JTextField) C).setText("");
                        }
                    }
                    launchAdminHome();
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

    public void launchAdminHome() {
        JPanel adminHome = new JPanel();

        JLabel greeting = new JLabel("Welcome Admin: " + adminName);
        JButton managedEvents = new JButton("Managed Events");
        JButton createEvent = new JButton("Create Event");
        JButton addAdmin = new JButton("Add Admin User");
        JButton archive = new JButton("Archive Events");
        JButton logout = new JButton("Logout");

        adminHome.add(greeting);
        adminHome.add(managedEvents);
        adminHome.add(createEvent);
        adminHome.add(addAdmin);
        adminHome.add(archive);
        adminHome.add(logout);

        panels.add(adminHome, "AdminHome");
        cardPanels.show(panels, "AdminHome");

        archive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to archive all events?",
                        "Warning", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) {
                    CallableStatement stmt = null;
                    try {
                        stmt = connection.prepareCall("{call archiveEventProcedure(?)}");
                        stmt.setTimestamp(1, getCurrentTimestamp());
                        System.out.println(getCurrentTimestamp());
                        stmt.execute();
                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, "Events archived!");
                }
            }
        });

        managedEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadEvents();
                launchManagedEvents();
            }
        });

        addAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchAddAdmin();
            }
        });

        createEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchCreateEvent();
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "Home");
            }
        });
    }

    public java.sql.Timestamp getCurrentTimestamp() {
        //java.util.Date today = new java.util.Date();
        //return new java.sql.Timestamp(today.getTime());
        Calendar calendar = Calendar.getInstance();
        return new java.sql.Timestamp(calendar.getTime().getTime());
    }

    public void launchAddAdmin() {
        JPanel addAdmin = new JPanel();
        JTextField fName = new JTextField(10);
        JTextField lName = new JTextField(10);
        JTextField uName = new JTextField(10);
        JTextField pass = new JTextField(10);
        JButton submit = new JButton("Submit");
        JButton back = new JButton("Back");

        addAdmin.add(new JLabel("First Name: "));
        addAdmin.add(fName);
        addAdmin.add(new JLabel("Last Name:"));
        addAdmin.add(lName);
        addAdmin.add(new JLabel("Username: "));
        addAdmin.add(uName);
        addAdmin.add(new JLabel("Password: "));
        addAdmin.add(pass);
        addAdmin.add(submit);
        addAdmin.add(back);

        panels.add(addAdmin, "AddAdmin");
        cardPanels.show(panels, "AddAdmin");

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connection != null) {
                    System.out.println("You made it, take control your database now!");
                    PreparedStatement stmt = null;
                    try {
                        stmt = connection.prepareStatement("INSERT INTO Administrator(first, last, username, password) " +
                                "VALUES(?, ?, ?, ?);");
                        stmt.setString(1, fName.getText());
                        stmt.setString(2, lName.getText());
                        stmt.setString(3, uName.getText());
                        stmt.setString(4, pass.getText());
                        stmt.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Failed to make connection!");
                }

                cardPanels.show(panels, "AdminHome");
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "AdminHome");
            }
        });
    }

    public void launchManagedEvents() {
        JPanel managedEvents = new JPanel();
        JPanel eventList = new JPanel();
        JButton back = new JButton("Back");

        for(Events event : events) {
            JTextArea text = new JTextArea(event.name + "\n---------------\n" + "Number of tickets Left: " +
                    event.ticketsLeft + "\n" + "Time: " + event.time +
                    "\n" + "Date: " + event.date + "\n" + "Venue: " + event.locationName +
                    "\n" + "Address: " + event.address + "" +
                    "\n" + "Canceled? "+ event.isCanceled +  "\n\n");

            text.setEditable(false);
            eventList.add(text);

            System.out.println(text.getWidth() + " " + text.getHeight());

            text.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    launchEventEditor(event);
                }

            });
        }

        JScrollPane pane = new JScrollPane(eventList);
        pane.setPreferredSize(new Dimension(350, 200));
        managedEvents.add(pane);
        managedEvents.add(back);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panels.add(managedEvents, "ManageEvents");
        cardPanels.show(panels, "ManageEvents");

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "AdminHome");
            }
        });
    }

    public void launchCreateEvent() {
        JPanel createEvent = new JPanel();
        JTextField eventName = new JTextField("Event Name           ");
        JTextField ticketsLeft = new JTextField("Number of tickets left ");
        JTextField time = new JTextField("Time format: HH:MM:SS     ");
        JTextField date = new JTextField("Date format: MM/DD/YYYY   ");

        loadLocations();
        String[] locations = new String[locNames.size()];
        locations = locNames.toArray(locations);
        JComboBox availableLocations = new JComboBox(locations);     //TEMPORARY LOCATIONS ONLY FOR TESTING
        JButton create = new JButton("Create");

        JButton back = new JButton("Back");

        createEvent.add(eventName);
        createEvent.add(ticketsLeft);
        createEvent.add(time);
        createEvent.add(date);
        createEvent.add(availableLocations);
        createEvent.add(create);
        createEvent.add(back);

        panels.add(createEvent, "CreateEvent");
        cardPanels.show(panels, "CreateEvent");

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(createEvent(availableLocations.getSelectedIndex() + 1, eventName.getText(), ticketsLeft.getText(), time.getText(), date.getText())) {
                    JOptionPane.showMessageDialog(frame, "Event Created!");
                    cardPanels.show(panels, "AdminHome");
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Invalid Event Format! Creation Failed!");
                }
            }
        });

        time.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                time.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        eventName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                eventName.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        ticketsLeft.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ticketsLeft.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        date.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                date.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "AdminHome");
            }
        });
    }

    public void launchEventEditor(Events event) {
        JPanel eventEditor = new JPanel();
        JButton changeTickets = new JButton("Change number of tickets");
        JButton changeDateTime = new JButton("Change the date or time");
        JButton cancelEvent = new JButton("Cancel event");
        JButton back = new JButton("Back");

        JTextArea text = new JTextArea(event.name + "\n---------------\n" + "Number of tickets Left: " +
                event.ticketsLeft + "\n" + "Time: " + event.time +
                "\n" + "Date: " + event.date + "\n" + "Venue: " + event.locationName +
                "\n" + "Address: " + event.address +
                "\n" + "Canceled? "+ event.isCanceled +  "\n\n");

        eventEditor.add(text);
        eventEditor.add(changeTickets);
        eventEditor.add(changeDateTime);
        eventEditor.add(cancelEvent);
        eventEditor.add(back);
        panels.add(eventEditor, "EventEditor");
        cardPanels.show(panels, "EventEditor");

        changeTickets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchEditTickets(event);
            }
        });

        changeDateTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchChangeTimeDate(event);
            }
        });

        cancelEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this event?",
                        "Warning", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) {
                    if (connection != null) {
                        PreparedStatement stmt = null;
                        ResultSet rs = null;
                        try {
                            stmt = connection.prepareStatement("UPDATE EVENT_TABLE " +
                                    "SET isCanceled = TRUE " +
                                    "WHERE ? = eventID;");
                            stmt.setInt(1, event.id);

                            System.out.println(event.id);

                            if(stmt.executeUpdate() < 1) {

                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("Failed to make connection!");
                    }

                    cardPanels.show(panels, "AdminHome");
                }
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "ManageEvents");
            }
        });
    }

    public void launchChangeTimeDate(Events event) {
        JPanel changeTimeDate = new JPanel();
        JTextField newTime = new JTextField("Time format: HH:MM:SS     ");
        JTextField newDate = new JTextField("Date format: MM/DD/YYYY   ");
        JButton confirm = new JButton("Confirm");
        JButton cancel = new JButton("Cancel");

        changeTimeDate.add(new JLabel("Current Date: " + event.date));
        changeTimeDate.add(newDate);
        changeTimeDate.add(new JLabel("Current Time: " + event.time));
        changeTimeDate.add(newTime);
        changeTimeDate.add(confirm);
        changeTimeDate.add(cancel);

        panels.add(changeTimeDate, "ChangeTimeDate");
        cardPanels.show(panels, "ChangeTimeDate");

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this event?",
                        "Warning", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) {
                    if (connection != null) {
                        PreparedStatement stmt = null;
                        ResultSet rs = null;
                        try {
                            stmt = connection.prepareStatement("UPDATE EVENT_TABLE " +
                                    "SET date = ?, time = ?" +
                                    "WHERE ? = eventID;");
                            if(newDate.getText() != "" || newDate.getText() != "Date format: MM/DD/YYYY   ") {
                               try {
                                   DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                                   java.util.Date jDate = formatter.parse(newDate.getText());
                                   java.sql.Date sqlDate = new java.sql.Date(jDate.getTime());

                                   System.out.println(sqlDate);
                                   stmt.setDate(1, sqlDate);
                                }
                                catch (ParseException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            if(newTime.getText() != "" || newTime.getText() != "Time format: HH:MM:SS     ") {
                                try {
                                    DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                                    java.util.Date jTime = formatter.parse(newTime.getText());
                                    java.sql.Time sqlTime = new java.sql.Time(jTime.getTime());

                                    System.out.println(sqlTime);
                                    stmt.setTime(2, sqlTime);
                                }
                                catch (ParseException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            stmt.setInt(3, event.id);

                            if(stmt.executeUpdate() < 1) {

                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("Failed to make connection!");
                    }

                    cardPanels.show(panels, "AdminHome");
                }
            }
        });

        newDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                newDate.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        newTime.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                newTime.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "EventEditor");
            }
        });
    }

    public void launchEditTickets(Events event) {
        JPanel editTickets = new JPanel();
        JTextField newValue = new JTextField(10);
        JButton confirm = new JButton("Confirm");
        JButton cancel = new JButton("Cancel");

        editTickets.add(new JLabel("Current tickets available: " + event.ticketsLeft));
        editTickets.add(newValue);
        editTickets.add(confirm);
        editTickets.add(cancel);

        panels.add(editTickets, "EditTickets");
        cardPanels.show(panels, "EditTickets");

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this event?",
                        "Warning", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) {
                    if (connection != null) {
                        PreparedStatement stmt = null;
                        ResultSet rs = null;
                        try {
                            stmt = connection.prepareStatement("UPDATE EVENT_TABLE " +
                                    "SET numTicketsLeft = ? " +
                                    "WHERE ? = eventID;");
                            stmt.setInt(1, Integer.parseInt(newValue.getText()));
                            stmt.setInt(2, event.id);

                            if(stmt.executeUpdate() < 1) {

                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("Failed to make connection!");
                    }

                    cardPanels.show(panels, "AdminHome");
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanels.show(panels, "EventEditor");
            }
        });
    }

    //STILL NEED TO UPDATE SQL DATABASE
    public boolean createEvent(int locid, String name, String tickets, String timeInput, String dateInput) {
        try {
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            java.util.Date jDate = formatter.parse(dateInput);
            java.sql.Date sqlDate = new java.sql.Date(jDate.getTime());

            System.out.println(sqlDate);

            formatter = new SimpleDateFormat("HH:mm:ss");
            java.util.Date jTime = formatter.parse(timeInput);
            java.sql.Time sqlTime = new java.sql.Time(jTime.getTime());

            if (connection != null) {
                System.out.println("You made it, take control your database now!");
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {
                    stmt = connection.prepareStatement("INSERT INTO EVENT_TABLE(locID, name, numTicketsLeft, time, date)" +
                            "VALUES(?, ?, ?, ?, ?);");
                    stmt.setInt(1, locid);
                    stmt.setString(2, name);
                    stmt.setString(3, tickets);
                    stmt.setTime(4, sqlTime);
                    stmt.setDate(5, sqlDate);

                    if(stmt.executeUpdate() < 1) {
                        return false;
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to make connection!");
            }
        }
        catch (ParseException pe) {
            System.out.println(pe.getMessage());
            return false;
        }
        return true;
    }

    public void loadEvents() {
        events.clear();
        String query = "SELECT eventID, EVENT_TABLE.name, numTicketsLeft, time, date, isCanceled, LOCATION.name AS locName, address " +
                "FROM EVENT_TABLE, LOCATION " +
                "WHERE EVENT_TABLE.locID =  " +
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

    public void loadLocations() {
        locNames.clear();
        String query = "SELECT name FROM LOCATION";

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    locNames.add(rs.getString("name"));
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
                stmt = connection.prepareStatement("SELECT first, last, username, password FROM ADMINISTRATOR WHERE username=? AND password=?");
                stmt.setString(1, username);
                stmt.setString(2, password);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Passed!");
                    this.adminName = rs.getString("first") + " " + rs.getString("last");
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
