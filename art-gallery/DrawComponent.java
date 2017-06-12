/*
    DrawComponent class that perform draw actions/events
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class DrawComponent extends JPanel{
	private Draw draw;
	private ArtFrame frame;
	private ArrayList<PlotPoint> points;
	private ArrayList<PlotPoint> guards;
	private boolean plotting, guarding, color;

    /**
        Constructor
        @param frame the frame to be drawn
        @param draw the Draw object drawing on the frame
    */
	public DrawComponent(ArtFrame frame, Draw draw) {
		this.draw = draw;
		this.frame = frame;
		addListeners();
		points = new ArrayList<PlotPoint>();
		guards = new ArrayList<PlotPoint>();
		plotting = true;
		guarding = false;
		color = false;
	}

    /**
        Add listeners to get mouse position being clicked on canvas
    */
	private void addListeners() {
		frame.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PlotPoint p = new PlotPoint();
				p.setX(e.getX());
				p.setY(e.getY());
				if(plotting)
					points.add(p);
				if(guarding)
					guards.add(p);
			}
		});
	}

    /**
        This is where all the painting or drawing happens
        @param g the Graphics object used to draw on canvas
    */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(color)
        	draw.drawGuardView(g2, points, guards);
        draw.drawPoints(g2, points);
        draw.drawGuards(g2, guards);
        frame.setCounter(guards.size());
        frame.updateCounterLabel();
    }

    /**
        Gets the list of points
    */
    public ArrayList<PlotPoint> getPoints() {
    	return points;
    }

    /**
        Gets the list of guards
    */
    public ArrayList<PlotPoint> getGuards() {
    	return guards;
    }

    /**
        Sets the point flag if user selects plotting option
        @param p boolean flag
    */
    public void setPlotting(boolean p) {
    	this.plotting = p;
    }

    /**
        Sets the guard flag if user selects guarding option
        @param g boolean flag
    */
    public void setGuarding(boolean g) {
    	this.guarding = g;
    }

    /**
        Sets the color flag if user selects coloring option
        @param c boolean flag
    */
    public void setColoring(boolean c) {
    	this.color = c;
    }

    /**
        Removes all points and guards
    */
    public void clear() {
    	points.clear();
    	guards.clear();
    }

    /**
        Remove all gaurds
    */
    public void clearGuards() {
    	guards.clear();
    }

    /**
        Undo last operation depending on if user is plotting or guarding
    */
    public void undo() {
    	if(plotting && points.size() > 0)
    		points.remove(points.size()-1);
    	if(guarding && guards.size() > 0)
    		guards.remove(guards.size()-1);
    }
}