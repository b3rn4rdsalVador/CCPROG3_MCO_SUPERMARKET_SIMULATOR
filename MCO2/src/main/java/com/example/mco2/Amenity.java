/**
 * Amenity.java
 *
 * This is the abstract base class for all fixed, static elements on the map,
 * such as shelves, walls, counters, and stairs.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

/**
 * Represents a fixed element or feature on the supermarket map grid.
 * Amenities are generally impassable by default and provide interaction points for the Shopper.
 */
public class Amenity {
    private final Point position;

    /**
     * Constructs a new Amenity at the specified map position.
     *
     * @param position The (x, y) coordinate where the Amenity is located.
     */
    public Amenity(Point position) {
        this.position = position;
    }

    /**
     * Determines if the Shopper can move onto or pass through this Amenity's tile.
     *
     * @return {@code true} if the Amenity is passable (e.g., floor, stairs); 
     * {@code false} otherwise (e.g., wall, display). Default is {@code false}.
     */
    public boolean isPassable() {
        return false; // Default: Impassable
    }

    /**
     * Executes the interaction action when the Shopper attempts to use the Amenity
     * (usually by pressing the interaction key while facing it).
     * <p>
     * For GUI-driven elements like Displays, this method typically performs simple
     * logging, as the GUI handles the visual popup window.
     *
     * @param shopper The Shopper instance initiating the interaction.
     */
    public void interact(Shopper shopper) {
        // Default: Do nothing
    }

    /**
     * Gets the current position of the Amenity on the map.
     *
     * @return The {@link Point} object representing the Amenity's coordinates.
     */
    public Point getPosition() { return position; }
}