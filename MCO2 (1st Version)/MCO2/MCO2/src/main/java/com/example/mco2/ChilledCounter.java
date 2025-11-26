package com.example.mco2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a counter designed to hold chilled products (meats and seafood).
 * Inherits from Display and implements product handling logic specific to chilled goods.
 */
public class ChilledCounter extends Display {
    /** The maximum number of products the counter can hold. */
    private static final int MAX_CAPACITY = 3;
    /** The list holding the products currently in the counter. */
    private final List<Product> products;

    /**
     * Constructs a ChilledCounter amenity.
     * @param position The (x, y) coordinates of the counter.
     * @param address The address string (e.g., "GF, R5C10").
     */
    public ChilledCounter(Point position, String address) {
        super(position, address);
        this.products = new ArrayList<>(MAX_CAPACITY);
    }

    /**
     * Defines the action taken when a shopper interacts with the counter.
     * @param shopper The shopper initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        System.out.println("Interacting with Chilled Counter at " + getAddress());
    }

    /**
     * Attempts to return a product to the counter. Checks for allowed prefixes
     * (Chicken, Beef, Seafood) and capacity.
     * @param product The product to be returned.
     * @return true if the product was successfully added, false otherwise.
     */
    @Override
    public boolean returnProduct(Product product) {
        String prefix = product.getSerialPrefix();
        if (!(prefix.equals("CHK") || prefix.equals("BEF") || prefix.equals("SEA"))) {
            System.out.println("[Denied: Only chilled meats/seafood can be placed here.]");
            return false;
        }
        if (isFull()) {
            System.out.println("[Denied: This Chilled Counter is full.]");
            return false;
        }
        this.products.add(product);
        return true;
    }

    /**
     * Removes a product from the counter. (Not fully implemented in the model).
     * @param shopper The shopper.
     * @return null.
     */
    @Override
    public Product removeProduct(Shopper shopper) { return null; }
    
    /**
     * Checks if the counter is full.
     * @return true if full, false otherwise.
     */
    @Override
    public boolean isFull() { return this.products.size() >= MAX_CAPACITY; }

    /**
     * Searches the contents of the counter for a product matching the given name.
     * @param name The name fragment to search for.
     * @return true if a matching product is found, false otherwise.
     */
    @Override
    public boolean containsProductByName(String name) {
        String lowerName = name.toLowerCase();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(lowerName)) return true;
        }
        return false;
    }

    /**
     * Gets the list of products currently in the counter.
     * @return The list of products.
     */
    public List<Product> getProducts() { return products; }
}