/**
 * Wall.java
 *
 * This class represents an impassable structural element on the map that serves
 * to define the boundaries and distinct areas within the supermarket layout.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

/**
 * Represents a solid, permanent structure on the map that prevents movement.
 * Walls are a simple amenity whose primary purpose is to define the boundaries
 * of floors and rooms.
 */
public class Wall extends Amenity {

    /**
     * Constructs a new Wall amenity.
     *
     * @param position The {@link Point} coordinate where the Wall is located.
     */
    public Wall(Point position) {
        super(position);
    }

    /**
     * Overrides the default impassable status, explicitly stating that a Wall cannot be passed through.
     *
     * @return always {@code false}.
     */
    @Override
    public boolean isPassable() { return false; }

    /**
     * Handles the interaction logic when a Shopper attempts to interact with the Wall.
     * The method provides a simple informational message to the console.
     *
     * @param shopper The {@link Shopper} instance initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        System.out.println("You are facing a wall. Nothing here.");
    }
}