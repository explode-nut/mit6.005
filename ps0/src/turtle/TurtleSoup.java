/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for (int i = 0; i < 4; i++) {
            turtle.forward(sideLength);
            turtle.turn(90.0);
        }
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
        return (sides * 180.0 - 360.0) / sides;
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
        return Math.round((float) (360 / (180 - angle)));
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
        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(180 - calculateRegularPolygonAngle(sides));
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
     // Calculate the difference in X and Y between the current position and the target position.
        double deltaX = targetX - currentX;
        double deltaY = targetY - currentY;

        // Calculate the angle to the target point using atan2. This will give us the angle in radians.
        double angleToTargetRadians = Math.atan2(deltaY, deltaX);

        // Convert the angle to degrees. Note that Math.atan2 returns values from -π to π.
        double angleToTargetDegrees = Math.toDegrees(angleToTargetRadians);

        // Normalize the angle to be within the range [0, 360).
        if (angleToTargetDegrees > 0) {
            angleToTargetDegrees -= 90;
            angleToTargetDegrees = 360 - angleToTargetDegrees;
        } else {
            angleToTargetDegrees = Math.abs(angleToTargetDegrees) + 90;
        }

        // Calculate the difference between the current heading and the angle to the target.
        double headingDifference = angleToTargetDegrees - currentHeading;

        // Normalize the difference to ensure it's within the range [0, 360).
        while (headingDifference < 0) {
            headingDifference += 360;
        }
        while (headingDifference >= 360) {
            headingDifference -= 360;
        }

        // The result is the right turn amount to face the target point.
        return headingDifference;
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        double currentHeading = 0.0;
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < xCoords.size() - 1; i++) {
            currentHeading = calculateHeadingToPoint(currentHeading, xCoords.get(i), yCoords.get(i), xCoords.get(i + 1), yCoords.get(i + 1));
            list.add(currentHeading);
        }
        return list;
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle, int sideLength) {
        if (sideLength < 10) {
            return;
        }
        PenColor color = rollPenColor();
        turtle.color(color);
        turtle.forward(sideLength);
        turtle.turn(20.0);
        drawPersonalArt(turtle, sideLength - 10);
        turtle.turn(320.0);
        drawPersonalArt(turtle, sideLength - 10);
        turtle.turn(20.0);
        turtle.color(color);
        turtle.forward(-sideLength); 
    }
    
    private static PenColor rollPenColor() {
        Random random = new Random();
        PenColor[] colors = PenColor.values();
        int totalColors = colors.length;
        int index = random.nextInt(totalColors);
        //不要黑色
        while (index == 0) {
            index = random.nextInt(totalColors);
        }
        return colors[index];
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

//        drawRegularPolygon(turtle, 6, 40);
        drawPersonalArt(turtle, 60);

        // draw the window
        turtle.draw();
    }

}
