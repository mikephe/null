/*
	The Frame that holds everything to be drawn
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ArtFrame extends JFrame {
	private JFrame frame;
	private Draw draw;
	private DrawComponent dc;
	private JPanel controlPanel;
	private JPanel statusPanel;
	private JLabel statusLabel, guardLabel;
	private JButton plot, guards, clearScreen, removeGuards, color, screencap, undo;
	private int guardCounter;
	private final int WIDTH = 800;
	private final int HEIGHT = 600;

	/**
		Constructor initializing everything
	*/
	public ArtFrame() {
		super("Art Gallery");
		setup();
		start();
		guardCounter = 0;
	}

	/**
		Updates the label that shows the number of guards
	*/
	public void updateCounterLabel() {
		guardLabel.setText("Guards: " + guardCounter);
	}

	/**
		Sets the guard counter
		@param count the number of guards on screen
	*/
	public void setCounter(int count) {
		guardCounter = count;
	}

	/**
		Starts time to repaint and draw on screen
	*/
	private void start() {
		final int DELAY = 5;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Timer t = new Timer(DELAY, e -> dc.repaint());
        t.start();
	}

	/**
		Initializes labels, panels, layouts, buttons, and components
	*/
	private void setup() {
		guardLabel = new JLabel("Guards: 0");
		statusLabel = new JLabel("Status: Plotting Points");
		draw = new Draw();
		statusPanel = new JPanel();
		controlPanel = new JPanel();
		dc = new DrawComponent(this, draw);
		dc.setBorder(BorderFactory.createRaisedBevelBorder());
		controlPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		statusPanel.setBorder(BorderFactory.createRaisedBevelBorder());

		controlPanel.setLayout(new GridLayout(1, 6));
		addControlButtons();
		addButtonListeners();

		statusPanel.setLayout(new GridLayout(1, 2));
		statusPanel.add(guardLabel);
		statusPanel.add(statusLabel);

		setSize(WIDTH, HEIGHT);
		setLayout(new BorderLayout());
		add(statusPanel, BorderLayout.PAGE_START);
		add(dc, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.PAGE_END);
	}

	private void addControlButtons() {
		plot = new JButton("Plot");
		guards = new JButton("Guards");
		clearScreen = new JButton("Clear Screen");
		removeGuards = new JButton("Clear Guards");
		color = new JButton("Color");
		undo = new JButton("Undo");
		screencap = new JButton("Save");

		controlPanel.add(plot);
		controlPanel.add(guards);
		controlPanel.add(clearScreen);
		controlPanel.add(removeGuards);
		controlPanel.add(color);
		controlPanel.add(undo);
		controlPanel.add(screencap);
	}

	/**
		Sets flag if user is either plotting points or guards
		@param p flag for points
		@param g flag for guards
	*/
	private void setFlags(boolean p, boolean g) {
		dc.setPlotting(p);
		dc.setGuarding(g);
	}

	/**
		Add listeners to all buttons
	*/
	private void addButtonListeners() {
		plot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				statusLabel.setText("Status: Plotting Points");
				setFlags(true, false);
			}
		});

		guards.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				statusLabel.setText("Status: Placing guards");
				setFlags(false, true);
			}
		});

		clearScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				statusLabel.setText("Status: Screen Cleared");
				setFlags(false, false);
				dc.setColoring(false);
				dc.clear();
			}
		});

		removeGuards.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				statusLabel.setText("Status: Guards Removed");
				setFlags(false, false);
				dc.clearGuards();
			}
		});

		screencap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				statusLabel.setText("Status: Saving image as PNG");
				setFlags(false, false);
				try {
					java.awt.Point p = getLocationOnScreen();
					int x = (int)p.getX();
					int y = (int)p.getY();
					BufferedImage image = new Robot().createScreenCapture(new Rectangle(x, y, 
                  															WIDTH, HEIGHT));
					ImageIO.write(image, "png", new File("screenshot.png"));
				} catch(AWTException ex) {
					ex.printStackTrace();
				} catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		color.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				statusLabel.setText("Status: Coloring guard's view");
				setFlags(false, false);
				dc.setColoring(true);
			}
		});

		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				statusLabel.setText("Status: Undo");
				dc.undo();
			}
		});
	}
}