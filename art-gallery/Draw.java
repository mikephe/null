/*
    Draw class that draws the points, guards, and shaded (guarded) regions.
*/
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Draw {
    private final double OFFSETX = 2; // aligns drawn point with mouse
    private final double OFFSETY = 45; // aligns drawn point with mouse

    /**
        Draws the points
        @param g2 the Graphics2D object to draw points on canvas
        @param points the points to be drawn
    */
    public void drawPoints(Graphics2D g2, ArrayList<PlotPoint> points) {
        g2.setPaint(Color.BLACK);
        for (PlotPoint p : points)
            g2.fill(new Ellipse2D.Double(p.getX() - OFFSETX, p.getY() - OFFSETY, p.getDiameter(), p.getDiameter()));

        for (int i = 0; i <= points.size()-1; i++) {
            double radius = points.get(i).getDiameter()/2;
            if(i < points.size()-1)
                g2.draw(new Line2D.Double(points.get(i).getX()-OFFSETX+radius, points.get(i).getY()-OFFSETY+radius, 
                                         points.get(i+1).getX()-OFFSETX+radius, points.get(i+1).getY()-OFFSETY+radius));
            else 
                g2.draw(new Line2D.Double(points.get(i).getX()-OFFSETX+radius, points.get(i).getY()-OFFSETY+radius, 
                                         points.get(0).getX()-OFFSETX+radius, points.get(0).getY()-OFFSETY+radius));
        }
    }

    /**
        Draws the guards
        @param g2 the Graphics2D object to draw guards on canvas
        @param guards the guards to be drawn
    */
    public void drawGuards(Graphics2D g2, ArrayList<PlotPoint> guards) {
        g2.setPaint(Color.RED);
        for (PlotPoint p : guards)
            g2.fill(new Ellipse2D.Double(p.getX() - OFFSETX, p.getY() - OFFSETY, p.getDiameter()*1.5, p.getDiameter()*1.5));
    }

    /**
        Draws the guarded regions
        @param g2 the Graphics2D object to draw guarded regions on canvas
        @param points the points with vertices to determine where guards line of sight is
        @param guards the guards with 360 view
    */
    public void drawGuardView(Graphics2D g2, ArrayList<PlotPoint> points, ArrayList<PlotPoint> guards) {
        if(points.size() >= 3) {
            Polygon poly = new Polygon();

            for(PlotPoint p : points)
                poly.addPoint((int)(p.getX()-OFFSETX+(p.getDiameter()/2)), (int)(p.getY()-OFFSETY+(p.getDiameter()/2)));

            g2.draw(poly);
            g2.setPaint(Color.BLUE);
            g2.fillPolygon(poly);
        }
    }
}