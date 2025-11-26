/**
 * ChilledCounter.java
 *
 * This class implements a specific type of Display for perishable meat and seafood products.
 * It enforces rules on which product types can be stored and manages product storage
 * in a flat list structure.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a chilled display counter used for storing temperature-sensitive products.
 * This counter uses a simple, flat list structure with a fixed maximum capacity.
 */
public class ChilledCounter extends Display {
    /** The maximum number of products this counter can hold. */
    private static final int MAX_CAPACITY = 3;

    private final List<Product> products;

    /**
     * Initializes a new ChilledCounter instance.
     * * @param position The position of the counter on the map.
     * @param address The string address/location identifier of the counter.
     */
    public ChilledCounter(Point position, String address) {
        super(position, address);
        this.products = new ArrayList<>(MAX_CAPACITY);
    }

    /**
     * Executes the interaction action, typically printing a log message as the GUI handles the pop-up.
     * * @param shopper The shopper initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        System.out.println("Interacting with Chilled Counter at " + getAddress());
    }

    /**
     * Attempts to return a product to the counter.
     * Only chilled meats (CHK) and seafood (BEF, SEA prefixes) are allowed.
     * * @param product The product to return.
     * @return {@code true} if the product was successfully added, {@code false} otherwise (wrong type or full).
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
     * Removes a product from the counter. (Removal logic is primarily handled by the GUI).
     * * @param shopper The shopper initiating the removal.
     * @return {@code null} (removal handled by GUI).
     */
    @Override
    public Product removeProduct(Shopper shopper) { return null; }

    /**
     * Checks if the counter is full.
     * * @return {@code true} if the list is at maximum capacity, {@code false} otherwise.
     */
    @Override
    public boolean isFull() { return this.products.size() >= MAX_CAPACITY; }

    /**
     * Checks if any product matching the provided name is currently in the counter's list.
     * * @param name The name to search for.
     * @return {@code true} if the product is found, {@code false} otherwise.
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
     * Gets the raw list of products currently stored in the counter.
     * * @return The list of products.
     */
    public List<Product> getProducts() { return products; }
}