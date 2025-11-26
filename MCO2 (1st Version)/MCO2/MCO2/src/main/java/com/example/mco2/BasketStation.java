package com.example.mco2;

/**
 * Represents a station where shoppers can retrieve or return a Basket.
 * Inherits from Service and acts as a barrier (isPassable() is false by default from Service).
 */
public class BasketStation extends Service {
    
    /**
     * Constructs a BasketStation at the specified position.
     * @param position The (x, y) coordinates of the station.
     */
    public BasketStation(Point position) {
        super(position);
    }

    /**
     * Handles the interaction logic for retrieving or returning a basket.
     * A basket can only be returned if it is empty. A basket can only be retrieved
     * if the shopper is not carrying equipment, has no hand-carried items, and has not checked out.
     * @param shopper The shopper initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
       
        if (shopper.hasEquipment() && shopper.getEquipment() instanceof Basket) {

            Basket basket = (Basket) shopper.getEquipment();

            if (basket.isEmpty()) {
                shopper.removeEquipment();
                System.out.println("Basket returned.");
            } else {
                System.out.println("Cannot return basket: It is not empty.");
            }
            return;
        }

        if (!shopper.hasEquipment() && shopper.getHandCarried().isEmpty() && !shopper.getHasCheckedOut()) {
            shopper.setEquipment(new Basket());
            System.out.println("Basket retrieved! Capacity: 15.");
            return;
        }

        System.out.println("Cannot retrieve basket. (Hands full or already checked out?)");
    }
}