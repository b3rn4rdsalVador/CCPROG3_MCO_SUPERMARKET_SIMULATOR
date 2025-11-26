/**
 * Point.java
 *
 * This class represents a specific coordinate (x, y) on a 2D map grid.
 * It is immutable and provides utility for calculating movement in standard directions.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

/**
 * Represents a specific coordinate (x, y) on a 2D map grid.
 * This class is immutable, meaning its position cannot change after creation.
 */
public class Point {
    private final int x;
    private final int y;

    /**
     * Constructs a new Point object at the specified coordinates.
     *
     * @param x The column index (horizontal position).
     * @param y The row index (vertical position).
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates and returns a new Point object representing the next position
     * if moving from the current point in the given direction.
     *
     * @param direction The direction of movement (NORTH, EAST, SOUTH, or WEST).
     * @return A new Point object representing the position after the move.
     */
    public Point getNextPosition(Direction direction) {
        int nextX = x;
        int nextY = y;
        switch (direction) {
            case NORTH -> nextY--;
            case SOUTH -> nextY++;
            case WEST -> nextX--;
            case EAST -> nextX++;
        }
        return new Point(nextX, nextY);
    }

    /**
     * Compares this Point object to another object for equality.
     * Two Point objects are equal if they have the same x and y coordinates.
     *
     * @param obj The object to compare this Point against.
     * @return true if the given object is a Point with identical coordinates; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    /**
     * Gets the x-coordinate (column index) of this point.
     *
     * @return The x-coordinate.
     */
    public int getX() { return x; }

    /**
     * Gets the y-coordinate (row index) of this point.
     *
     * @return The y-coordinate.
     */
    public int getY() { return y; }
}