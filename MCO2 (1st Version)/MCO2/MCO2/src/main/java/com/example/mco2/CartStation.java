package com.example.mco2;

/**
 * Represents a station where shoppers can retrieve or return a Cart.
 * Inherits from Service and acts as a barrier.
 */
public class CartStation extends Service {
    
    /**
     * Constructs a CartStation at the specified position.
     * @param position The (x, y) coordinates of the station.
     */
    public CartStation(Point position) {
        super(position);
    }

    /**
     * Handles the interaction logic for retrieving or returning a cart.
     * A cart can only be returned if it is empty. A cart can only be retrieved
     * if the shopper is not carrying equipment, has no hand-carried items, and has not checked out.
     * @param shopper The shopper initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        
        if (shopper.hasEquipment() && shopper.getEquipment() instanceof Cart) {

            Equipment currentCart = shopper.getEquipment();

            if (currentCart.isEmpty()) {
                shopper.removeEquipment();
                System.out.println("Cart returned.");
            } else {
                System.out.println("Cannot return cart: It is not empty.");
            }
            return;
        }

        if (!shopper.hasEquipment() && shopper.getHandCarried().isEmpty() && !shopper.getHasCheckedOut()) {
            shopper.setEquipment(new Cart());
            System.out.println("Cart retrieved! Capacity: 30.");
            return;
        }

        System.out.println("Cannot retrieve cart. (Hands full or already checked out?)");
    }
}