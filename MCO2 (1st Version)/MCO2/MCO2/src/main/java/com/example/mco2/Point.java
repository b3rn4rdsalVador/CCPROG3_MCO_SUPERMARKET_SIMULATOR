package com.example.mco2;

/**
 * Represents an immutable (x, y) coordinate pair on the map grid.
 */
public class Point {
    /** The X-coordinate (column). */
    private final int x;
    /** The Y-coordinate (row). */
    private final int y;

    /**
     * Constructs a Point object.
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates and returns the new Point object resulting from moving one step
     * in the specified direction. Does not check for map bounds or barriers.
     * @param direction The direction of movement (NORTH, EAST, etc.).
     * @return A new Point object representing the next position.
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
     * @param obj The object to compare against.
     * @return true if the objects are the same class and have the same x and y coordinates, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    /**
     * Gets the X-coordinate.
     * @return The X-coordinate.
     */
    public int getX() { return x; }
    
    /**
     * Gets the Y-coordinate.
     * @return The Y-coordinate.
     */
    public int getY() { return y; }
}