/**
 * BasketStation.java
 *
 * This class represents a service point where shoppers can pick up or return shopping baskets.
 * It manages the Shopper's equipment status and capacity checks.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import javafx.scene.control.Alert;

/**
 * Represents a station on the map where a Shopper can acquire or return a shopping {@link Basket}.
 * It extends {@link Service} and provides interaction logic for equipment management.
 */
public class BasketStation extends Service {

    /**
     * Constructs a new BasketStation at the specified map position.
     *
     * @param position The (x, y) coordinate where the BasketStation is located.
     */
    public BasketStation(Point position) {
        super(position);
    }

    /**
     * Handles the interaction logic when a Shopper uses the Basket Station.
     * Logic includes:
     * 1. Allowing the Shopper to return an empty basket.
     * 2. Allowing the Shopper to pick up a new basket if hands are empty and not checked out.
     *
     * @param shopper The {@link Shopper} instance initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        // 1. RETURNING A BASKET
        if (shopper.hasEquipment() && shopper.getEquipment() instanceof Basket) {
            Equipment currentBasket = shopper.getEquipment();

            if (currentBasket.isEmpty()) {
                shopper.removeEquipment();
                showPopup(Alert.AlertType.INFORMATION, "Success", "Basket returned. Thank you!");
            } else {
                showPopup(Alert.AlertType.ERROR, "Error", "Cannot return basket: It is not empty.");
            }
            return;
        }

        // 2. GETTING A BASKET
        if (!shopper.hasEquipment() && shopper.getHandCarried().isEmpty() && !shopper.getHasCheckedOut()) {
            shopper.setEquipment(new Basket());
            showPopup(Alert.AlertType.INFORMATION, "Success", "Basket retrieved! Capacity: 15.");
            return;
        }

        // 3. FAILURE MESSAGES
        if (shopper.hasEquipment()) {
            showPopup(Alert.AlertType.WARNING, "Failed", "You already have equipment.");
        } else if (!shopper.getHandCarried().isEmpty()) {
            showPopup(Alert.AlertType.WARNING, "Failed", "Your hands must be empty to grab a basket.");
        } else {
            showPopup(Alert.AlertType.WARNING, "Failed", "Cannot retrieve basket.");
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