/**
 * Stairs.java
 *
 * This class represents a transit point between the two floors of the supermarket.
 * It is a specialized Service amenity that must be passable to trigger the floor transition logic.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import javafx.scene.control.Alert;

/**
 * Represents the stairs amenity on the map, allowing the Shopper to move between the ground floor (0) and the second floor (1).
 * Unlike most amenities, the Stairs tile is passable to allow the {@link Shopper#move(Direction, SupermarketMap)} logic
 * to detect the floor change event when the shopper steps on the tile.
 */
public class Stairs extends Service {

    /**
     * Constructs a new Stairs amenity.
     *
     * @param position The {@link Point} coordinate where the Stairs are located.
     */
    public Stairs(Point position) {
        super(position);
    }

    /**
     * Overrides the default impassable status. The Stairs amenity must be passable
     * so that the {@link Shopper} can step onto the tile and trigger the floor transition logic.
     *
     * @return always {@code true}.
     */
    @Override
    public boolean isPassable() {
        return true; // CRITICAL: Must be true so Shopper.move() can detect the step
    }

    /**
     * Handles the interaction logic when a Shopper explicitly uses the Stairs (e.g., presses the interaction key).
     * This method displays an informational popup but does not trigger the floor change itself,
     * as floor movement happens automatically on entry (in {@link Shopper#move(Direction, SupermarketMap)}).
     *
     * @param shopper The {@link Shopper} instance initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        // This popup only shows if you inspect it (press SPACE).
        // Actual movement happens automatically when you step on it.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stairs");
        alert.setHeaderText(null);
        alert.setContentText("Walk onto this tile to travel between floors.");
        alert.show();
    }
}