/**
 * Exit.java
 *
 * This class represents the final exit point of the supermarket. It ensures the Shopper
 * has returned all equipment and paid for any acquired products before successfully exiting.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import javafx.scene.control.Alert;

/**
 * Represents the final exit amenity in the supermarket.
 * The Exit enforces rules to prevent theft and loss of equipment by checking
 * the shopper's inventory and checkout status before allowing them to leave the simulation.
 */
public class Exit extends Service {

    /**
     * Constructs a new Exit amenity.
     *
     * @param position The {@link Point} coordinate where the Exit is located.
     */
    public Exit(Point position) {
        super(position);
    }

    /**
     * Handles the interaction logic when a Shopper attempts to use the Exit.
     * The Shopper is allowed to exit only if:
     * 1. They are not holding any equipment (Cart or Basket).
     * 2. They have either no products or have already checked out and paid.
     *
     * @param shopper The {@link Shopper} instance attempting to leave.
     */
    @Override
    public void interact(Shopper shopper) {
        // 1. CHECK EQUIPMENT
        if (shopper.hasEquipment()) {
            showPopup(Alert.AlertType.WARNING, "Exit Denied", "Please return your Cart/Basket first.");
            return;
        }

        // 2. CHECK PAYMENT
        boolean acquiredProducts = !shopper.getAllProducts().isEmpty();
        if (acquiredProducts && !shopper.getHasCheckedOut()) {
            showPopup(Alert.AlertType.WARNING, "Exit Denied", "You have items! Please pay at the counter first.");
            return;
        }

        // 3. SUCCESS
        // We do NOT show a popup here because SupermarketFX handles the "Game Over" dialog.
        // We just flag the shopper as exited.
        shopper.setHasExited(true);
    }

    /**
     * Helper method to display a JavaFX alert dialog.
     *
     * @param type The type of alert (e.g., WARNING, ERROR).
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
}