package com.example.mco2;

/**
 * Represents a static terminal for searching the location of products on the map.
 * Inherits from Service and acts as a barrier.
 */
public class ProductSearch extends Service {
    
    /**
     * Constructs a ProductSearch station at the specified position.
     * @param position The (x, y) coordinates of the station.
     */
    public ProductSearch(Point position) {
        super(position);
    }

    /**
     * Handles the interaction logic, prompting the user to access the search terminal.
     * @param shopper The shopper initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        System.out.println("Accessing Product Search Terminal...");
    }
}