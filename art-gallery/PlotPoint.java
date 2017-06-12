/*
	PlotPoint class representing the points drawing the polygon
*/

import java.awt.*;

public class PlotPoint {
	private double x;
	private double y;
	private double diameter;

	/**
		Constructor
	*/
	public PlotPoint() {
		this.x = 0;
		this.y = 0;
		this.diameter = 10;
	}

	/**
		Constructor
		@param x the x coordinate
		@param y the y coordinate
		@param diameter the diameter of the point
	*/
	public PlotPoint(double x, double y, double diameter) {
		this.x = x;
		this.y = y;
    	this.diameter = diameter;
	}

	/**
		Sets the x coordinate
	*/
	public void setX(double x) {
		this.x = x;
	}

	/**
		Sets the y coordinate
	*/
	public void setY(double y) {
		this.y = y;
	}

	/**
		Gets the x coordinate
	*/
	public double getX() {
		return x;
	}

	/**
		Gets the y coordinate
	*/
	public double getY() {
		return y;
	}

	/**
		Gets the diameter of the point
	*/
	public double getDiameter() {
		return diameter;
	}
}