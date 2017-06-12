import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by SteveWang on 11/27/15.
 */
public class LandingPageView {
    private CardLayout cardPanels;
    private JPanel panels;
    private JPanel home;
    private JPanel userHome;
    private JPanel userLogin;
    private AdminView adminLogin;
    private JPanel register;
    private JPanel browse;
    private JFrame frame;
    private DataSource ds;
    private JTextField username;
    private JPasswordField password;
    private JTextField firstReg;
    private JTextField lastReg;
    private JTextField userReg;
    private JPasswordField passReg;
    private JLabel nameLabel;
    private JTextArea eventInfo;
    private Connection connection;

    public LandingPageView(DataSource ds) {
        this.ds = ds;
        connect();
        initialize();
    }

    public void initialize() {
        //Home
        JButton user = new JButton("User");
        JButton admin = new JButton("Admin");
        JButton create = new JButton("Create Account");
        cardPanels = new CardLayout();
        panels = new JPanel();
        home = new JPanel();
        frame = new JFrame("Ticket Reservation");

        panels.setLayout(cardPanels);

        home.add(user);
        home.add(admin);
        home.add(create);

        panels.add(home, "Home");
        cardPanels.show(panels, "Home");

        frame.add(panels);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserView uv = new UserView(ds, frame, panels, cardPanels, connection);
            }
        });

        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminView av = new AdminView(ds, frame, panels, cardPanels, connection);
            }
        });


        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterView rv = new RegisterView(ds, frame, panels, cardPanels, connection);
            }
        });
    }

    public void connect() {
        connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
    }
}
