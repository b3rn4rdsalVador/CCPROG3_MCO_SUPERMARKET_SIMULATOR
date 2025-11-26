package com.example.mco2;

/**
 * Represents a solid, impassable wall on the map.
 * Used as a boundary or obstacle.
 */
public class Wall extends Amenity {
    
    /**
     * Constructs a Wall amenity.
     * @param position The (x, y) coordinates of the amenity.
     */
    public Wall(Point position) {
        super(position);
    }

    /**
     * Walls are non-passable barriers.
     * @return false.
     */
    @Override
    public boolean isPassable() { return false; }

    /**
     * Defines the interaction message when a shopper faces the wall.
     * @param shopper The shopper initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        System.out.println("You are facing a wall. Nothing here.");
    }
}