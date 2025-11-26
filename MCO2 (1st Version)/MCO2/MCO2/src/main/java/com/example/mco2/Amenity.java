package com.example.mco2;

/**
 * Represents a fixed object on the supermarket map grid, such as a Wall, Shelf, or Service point.
 * Amenities occupy a single point and can be interacted with.
 */
public class Amenity {
    /** The position of this amenity on the map grid. */
    private final Point position;

    /**
     * Constructs an Amenity object at a specific point.
     * @param position The (x, y) coordinates of the amenity.
     */
    public Amenity(Point position) {
        this.position = position;
    }

    /**
     * Checks if a shopper can move onto this amenity's position.
     * Defaults to false, meaning the amenity acts as a barrier (e.g., a wall).
     * @return true if the amenity is passable, false otherwise.
     */
    public boolean isPassable() {
        return false; 
    }
    
    /**
     * Defines the action taken when a shopper interacts with the amenity (e.g., pressing space).
     * @param shopper The shopper initiating the interaction.
     */
    public void interact(Shopper shopper) {
        
    }

    /**
     * Gets the position of this amenity.
     * @return The position Point.
     */
    public Point getPosition() { return position; }
}