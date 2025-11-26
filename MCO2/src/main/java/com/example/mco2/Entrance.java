/**
 * Entrance.java
 *
 * This class represents the entry point to the supermarket. It functions as a specialized
 * Service amenity that locks the Shopper's movement once they step off it, preventing them
 * from returning until they exit.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import javafx.scene.control.Alert; // <--- Needed for popups

/**
 * Represents the main entrance to the supermarket.
 * The Entrance acts as a gateway, becoming impassable after the shopper steps off it once,
 * enforcing a one-way flow into the store.
 */
public class Entrance extends Service {
    private boolean hasBeenUsed;

    /**
     * Constructs a new Entrance amenity.
     *
     * @param position The {@link Point} coordinate where the Entrance is located.
     */
    public Entrance(Point position) {
        super(position);
        this.hasBeenUsed = false;
    }

    /**
     * Determines if the Entrance tile is currently passable.
     * It is only passable if the shopper is currently standing on it (i.e., it hasn't been used yet).
     *
     * @return {@code true} if the entrance has not been used; {@code false} otherwise.
     */
    @Override
    public boolean isPassable() {
        // Passable only if we haven't stepped off it yet
        return !this.hasBeenUsed;
    }

    /**
     * Handles the interaction logic when a Shopper uses the Entrance.
     * Displays a welcome message if the shopper is starting on the tile, or a locked message
     * if they try to return.
     *
     * @param shopper The {@link Shopper} instance initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        if (!this.hasBeenUsed) {
            // User is standing on it at the start
            showPopup(Alert.AlertType.INFORMATION, "Welcome", "You are at the Entrance.\nMove (W/A/S/D) to enter the supermarket.");
        } else {
            // User tries to go back
            showPopup(Alert.AlertType.WARNING, "Locked", "You must proceed to the Checkout/Exit.");
        }
    }

    /**
     * Helper method to display a JavaFX alert dialog.
     *
     * @param type The type of alert (e.g., INFORMATION, ERROR).
     * @param title The title of the dialog window.
     * @param message The content text displayed inside the dialog.
     */
    private void showPopup(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets the state of the Entrance to 'used'.
     * This is called when the shopper moves off the Entrance tile, making it impassable thereafter.
     */
    public void setHasBeenUsed() {
        this.hasBeenUsed = true;
    }
}