package com.example.mco2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a table display for non-refrigerated products, specifically fruit.
 * Inherits from Display and implements product handling logic specific to fruits.
 */
public class Table extends Display {
    /** The maximum number of products the table can hold. */
    private static final int MAX_CAPACITY = 4;
    /** The list holding the products currently on the table. */
    private final List<Product> products;

    /**
     * Constructs a Table amenity.
     * @param position The (x, y) coordinates of the table.
     * @param address The address string (e.g., "GF, R5C10").
     */
    public Table(Point position, String address) {
        super(position, address);
        this.products = new ArrayList<>(MAX_CAPACITY);
    }

    /**
     * Defines the action taken when a shopper interacts with the table.
     * @param shopper The shopper initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        System.out.println("Interacting with Table at " + getAddress());
    }

    /**
     * Attempts to return a product to the table. Checks if the product prefix is "FRU" (Fruit)
     * and checks capacity.
     * @param product The product to be returned.
     * @return true if the product was successfully added, false otherwise.
     */
    @Override
    public boolean returnProduct(Product product) {
        
        if (!product.getSerialPrefix().equals("FRU")) {
            System.out.println("[Denied: Only Fruit can be placed on a Table.]");
            return false;
        }
        if (isFull()) {
            System.out.println("[Denied: This Table is full.]");
            return false;
        }
        this.products.add(product);
        return true;
    }

    /**
     * Removes a product from the table. (Not fully implemented in the model).
     * @param shopper The shopper.
     * @return null.
     */
    @Override
    public Product removeProduct(Shopper shopper) { return null; } 

    /**
     * Checks if the table is full.
     * @return true if capacity is reached, false otherwise.
     */
    @Override
    public boolean isFull() { return this.products.size() >= MAX_CAPACITY; }

    /**
     * Searches the contents of the table for a product matching the given name.
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
     * Gets the list of products currently on the table.
     * @return The list of products.
     */
    public List<Product> getProducts() { return products; }
}