/**
 * ProductSearch.java
 *
 * This class represents a service amenity that allows the Shopper to search
 * for the location of products within the supermarket.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

/**
 * Represents a dedicated terminal on the map used by the Shopper to search for the location
 * of specific products currently stocked in the supermarket displays.
 * Interaction is handled primarily by the GUI opening an input dialog.
 */
public class ProductSearch extends Service {

    /**
     * Constructs a new ProductSearch terminal at the specified map position.
     *
     * @param position The {@link Point} coordinate where the terminal is located.
     */
    public ProductSearch(Point position) {
        super(position);
    }

    /**
     * Handles the interaction logic when a Shopper uses the Product Search Terminal.
     * This method primarily logs the access, as the main functionality (user input and result display)
     * is handled by the {@link SupermarketFX} graphical user interface.
     *
     * @param shopper The {@link Shopper} instance initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        System.out.println("Accessing Product Search Terminal...");
    }
}