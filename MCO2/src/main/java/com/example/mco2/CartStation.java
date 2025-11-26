/**
 * CartStation.java
 *
 * This class represents a service point where shoppers can pick up or return shopping carts.
 * It manages the Shopper's equipment status and ensures carts are empty upon return.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import javafx.scene.control.Alert; // Required for Popups

/**
 * Represents a station on the map where a Shopper can acquire or return a shopping {@link Cart}.
 * It extends {@link Service} and provides interaction logic specific to cart management.
 */
public class CartStation extends Service {

    /**
     * Constructs a new CartStation at the specified map position.
     *
     * @param position The (x, y) coordinate where the CartStation is located.
     */
    public CartStation(Point position) {
        super(position);
    }

    /**
     * Handles the interaction logic when a Shopper uses the Cart Station.
     * Logic includes:
     * 1. Allowing the Shopper to return an empty cart.
     * 2. Allowing the Shopper to pick up a new cart if hands are empty and they haven't checked out.
     *
     * @param shopper The {@link Shopper} instance initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        // 1. RETURNING A CART
        if (shopper.hasEquipment() && shopper.getEquipment() instanceof Cart) {
            Equipment currentCart = shopper.getEquipment();

            if (currentCart.isEmpty()) {
                shopper.removeEquipment();
                showPopup(Alert.AlertType.INFORMATION, "Success", "Cart returned. Thank you!");
            } else {
                showPopup(Alert.AlertType.ERROR, "Error", "Cannot return cart: It is not empty.");
            }
            return;
        }

        // 2. GETTING A CART
        if (!shopper.hasEquipment() && shopper.getHandCarried().isEmpty() && !shopper.getHasCheckedOut()) {
            shopper.setEquipment(new Cart());
            showPopup(Alert.AlertType.INFORMATION, "Success", "Cart retrieved! Capacity: 30.");
            return;
        }

        // 3. FAILURE MESSAGES (Specific Feedback)
        if (shopper.hasEquipment()) {
            showPopup(Alert.AlertType.WARNING, "Failed", "You already have equipment.");
        } else if (!shopper.getHandCarried().isEmpty()) {
            showPopup(Alert.AlertType.WARNING, "Failed", "Your hands must be empty to grab a cart.");
        } else {
            showPopup(Alert.AlertType.WARNING, "Failed", "Cannot retrieve cart.");
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
}