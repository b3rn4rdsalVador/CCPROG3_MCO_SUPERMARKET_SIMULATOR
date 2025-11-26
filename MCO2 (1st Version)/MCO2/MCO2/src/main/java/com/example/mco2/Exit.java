package com.example.mco2;

/**
 * Represents the final exit point of the supermarket.
 * Contains logic to check if a shopper is allowed to exit (i.e., returned equipment and paid).
 */
public class Exit extends Service {
    
    /**
     * Constructs an Exit amenity.
     * @param position The (x, y) coordinates of the amenity.
     */
    public Exit(Point position) {
        super(position);
    }

    /**
     * Handles the interaction logic for exiting the store.
     * Exiting is denied if the shopper has equipment or products but has not checked out.
     * @param shopper The shopper initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        if (shopper.hasEquipment()) {
            System.out.println("Exit denied: Return your equipment first.");
            return;
        }
        boolean acquiredProducts = !shopper.getAllProducts().isEmpty();
        if (acquiredProducts && !shopper.getHasCheckedOut()) {
            System.out.println("Exit denied: You must pay first.");
            return;
        }

        System.out.println("Thank you for shopping! Simulation ending...");
        shopper.setHasExited(true);
    }
}