/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
    	if(sides<=2) throw new RuntimeException("边数必须大于2");
    	return (sides-2)*180.0/sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
    	if(angle>=180||angle<=0) throw new RuntimeException("角度必须在(0,180)之间");
    	return (int) Math.round(360/(180-angle));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle = TurtleSoup.calculateRegularPolygonAngle(sides);
    	for(int i=0;i<sides;i++) {
    		turtle.forward(sideLength);
            turtle.turn(180-angle);
    	}
        
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, double currentX, double currentY,
    		double targetX, double targetY) {
        double dx = -(currentX-targetX);
        double dy = -currentY+targetY;
    	double at = Math.abs(Math.atan2(Math.abs(dx), Math.abs(dy)));
    	at = ((Math.round(at/Math.PI*180*1000)/1000.0));
    	double k = dx*(double)dy;
        double da = at-currentBearing;
    	if(k>0) {
    		if(dx<0&&dy<0) return doublemod(180+da,360);
        	return doublemod(da,360);
        }else if(k<0) {
        	da = (at+currentBearing);
        	if(dx<0&&dy>0) return doublemod(180-da,360);
        	return doublemod(360-da,360);
        }else {
        	if(dx==0 && dy !=0) {
        		double db = 180-currentBearing;
        		return dy>0?doublemod(db+180,360):(doublemod(db,360));
        	}else if(dy==0 && dx!=0) {
        		double db = dx>0?90-currentBearing:270-currentBearing;
        		return doublemod(db,360);
        	}else {
        		return 0;
        	}
        }
    }
    private static double doublemod(double d,int mod) {
    	if(d>=0)  
    		return d-((int)d/mod)*mod;
    	else
    		return d-((int)d/mod)*mod+360;
    }
    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
    	if(xCoords.size()<2) return null;
    	if(xCoords.size()!=yCoords.size()) return null;
    	int lx = xCoords.get(0), ly = yCoords.get(0);
		double deg = 0;
    	List<Double> list = new ArrayList<Double>();
    	for(int i=1;i<xCoords.size();i++) {
    		int x = xCoords.get(i), y= yCoords.get(i);
    		double turn = calculateBearingToPoint(deg,lx,ly,x,y);
    		list.add(turn);
    		lx=x;ly=y;deg=doublemod(turn+deg,360);
    	}
    	return list;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
    	Set<Point> set = new HashSet<Point>();
    	//System.out.println(points.toString());
    	//System.out.println(points.toArray().toString());
    	Point[] list = points.toArray(new Point[0]);
    	
    	if(list.length==0) return set;
    	Point p = list[0];
    	int len = list.length;
    	double x=p.x(),y=p.y();
    	int lp = 0;
    	for(int i=1;i<len;i++) {
    		p = list[i];
    		if(p.x()<x) {
    			lp=i;
    			x=p.x();
    		}
    	}
    	set.add(list[lp]);
    	p = list[lp];
    	
    	
    	//int minpi=lp;
    	Point np,minp=p,startp=p;
    	double maxdistance=0,distance=0;
    	double deg=0,turn=0,minturn=360;
    	
    	//list[lp] = null;
    	do {
    		double lx = p.x();
    		double ly = p.y();
    		minturn=360;
    		maxdistance=0;
    		for(int j=0;j<len;j++) {
        		np = list[j];
        		//if(np==null) continue;
        		x=np.x();y=np.y();
        		if(np.equals(p)) continue;
        		turn = calculateBearingToPoint(deg,lx,ly,x,y);
        		if(turn<=minturn) {
        			double dx=x-lx,dy=y-ly;
        			distance = Math.sqrt(dx*dx+dy*dy);
        			if(turn==minturn) {
        				if(distance>maxdistance) {
            				maxdistance = distance;
                			//minpi = j;
                			minp = np;
                			minturn = turn;
                		}
        			}else {
    					//minpi = j;
            			minp = np;
            			minturn = turn;
            			maxdistance = distance;
        			}
        			
        		}
        		
        	}
    		if(minp.equals(startp)) break;
    		set.add(minp);
    		deg=doublemod(minturn+deg,360);
    		lx=x;ly=y;
    		//list[minpi] = null;
    		p = minp;
    	}while(!p.equals(startp));
    	return set;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        turtle.color(PenColor.CYAN);
        int h = 40,hh=20,s=10;
        turtle.turn(180);
        //1
    	turtle.forward(h);
    	
    	turtle.turn(270);
    	turtle.forward(s);
    	//2
    	turtle.forward(hh);
    	turtle.turn(180);
    	turtle.forward(hh);
    	turtle.turn(90);
    	turtle.forward(hh);
    	turtle.turn(90);
    	turtle.forward(hh);
    	turtle.turn(270);
    	turtle.forward(hh);
    	turtle.turn(270);
    	turtle.forward(hh);
    	turtle.turn(180);
    	turtle.forward(hh+s);
    	//0
    	turtle.forward(hh);
    	turtle.turn(90);
    	turtle.forward(h);
    	turtle.turn(90);
    	turtle.forward(hh);
    	turtle.turn(90);
    	turtle.forward(h);
    	turtle.turn(90);
    	turtle.forward(hh);
    	
    	turtle.forward(s);
    	//L
    	turtle.turn(90);
    	turtle.forward(h);
    	turtle.turn(270);
    	turtle.forward(hh+s);
    	//0
    	turtle.forward(hh);
    	turtle.turn(270);
    	turtle.forward(h);
    	turtle.turn(270);
    	turtle.forward(hh);
    	turtle.turn(270);
    	turtle.forward(h);
    	turtle.turn(270);
    	turtle.forward(hh+s);
    	//2
    	turtle.forward(hh);
    	turtle.turn(180);
    	turtle.forward(hh);
    	turtle.turn(90);
    	turtle.forward(hh);
    	turtle.turn(90);
    	turtle.forward(hh);
    	turtle.turn(270);
    	turtle.forward(hh);
    	turtle.turn(270);
    	turtle.forward(hh);
    	turtle.turn(180);
    	turtle.forward(hh+s);
    	//2-1
    	turtle.forward(hh);
    	turtle.turn(90);
    	turtle.forward(hh);
    	turtle.turn(90);
    	turtle.forward(hh);
    	turtle.turn(270);
    	turtle.forward(hh);
    	turtle.turn(270);
    	turtle.forward(hh+s);
    	//1
    	turtle.turn(270);
    	turtle.forward(h);
    	//1
    	turtle.turn(90);
    	turtle.forward(s);
    	turtle.turn(90);
    	turtle.forward(h);
    	//5
    	turtle.turn(270);
    	turtle.forward(hh+s);
    	turtle.turn(270);
    	turtle.forward(hh);
    	turtle.turn(270);
    	turtle.forward(hh);
    	turtle.turn(90);
    	turtle.forward(hh);
    	turtle.turn(90);
    	turtle.forward(hh);
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawSquare(turtle, 40);
        drawRegularPolygon(turtle,7,40);
        drawPersonalArt(turtle);
        // draw the window
        turtle.draw();
    }

}
