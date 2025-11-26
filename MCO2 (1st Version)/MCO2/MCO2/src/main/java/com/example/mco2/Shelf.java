package com.example.mco2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a non-refrigerated shelf display with a two-tiered structure.
 * Holds dry goods and other non-chilled products.
 */
public class Shelf extends Display {
    /** The number of tiers (rows) in the shelf. */
    private static final int NUM_TIERS = 2;
    /** The maximum capacity of products per tier. */
    private static final int TIER_CAPACITY = 4;
    /** The 2D structure storing products, divided into tiers. */
    private final List<List<Product>> tiers;

    /**
     * Constructs a Shelf object.
     * @param position The (x, y) coordinates of the shelf.
     * @param address The address string (e.g., "GF, R5C10").
     */
    public Shelf(Point position, String address) {
        super(position, address);
        this.tiers = new ArrayList<>(NUM_TIERS);
        for (int i = 0; i < NUM_TIERS; i++) {
            this.tiers.add(new ArrayList<>(TIER_CAPACITY));
        }
    }

    /**
     * Defines the action taken when a shopper interacts with the shelf.
     * @param shopper The shopper initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        System.out.println("Interacting with Shelf at " + getAddress());
    }

    /**
     * Attempts to return a product to the shelf. Checks for allowed prefixes
     * (Cereal, Noodles, Snacks, Canned, Condiments, Drinks, Alcohol).
     * @param product The product to be returned.
     * @return true if the product was successfully added, false otherwise.
     */
    @Override
    public boolean returnProduct(Product product) {
        String prefix = product.getSerialPrefix();
        if (!(prefix.equals("CER") || prefix.equals("NDL") || prefix.equals("SNK") ||
                prefix.equals("CAN") || prefix.equals("CON") || prefix.equals("SFT") ||
                prefix.equals("JUC") || prefix.equals("ALC"))) {
            System.out.println("[Denied: This product type is not allowed on a Shelf.]");
            return false;
        }
        
        for (List<Product> tier : tiers) {
            if (tier.size() < TIER_CAPACITY) {
                tier.add(product);
                return true;
            }
        }
        System.out.println("[Denied: This Shelf is full.]");
        return false;
    }

    /**
     * Removes a product from the shelf. (Not fully implemented in the model).
     * @param shopper The shopper.
     * @return null.
     */
    @Override
    public Product removeProduct(Shopper shopper) { return null; }

    /**
     * Checks if the shelf is completely full (all tiers at max capacity).
     * @return true if full, false otherwise.
     */
    @Override
    public boolean isFull() {
        return tiers.get(0).size() == TIER_CAPACITY && tiers.get(1).size() == TIER_CAPACITY;
    }

    /**
     * Searches the contents of all shelf tiers for a product matching the given name.
     * @param name The name fragment to search for.
     * @return true if a matching product is found, false otherwise.
     */
    @Override
    public boolean containsProductByName(String name) {
        String lowerName = name.toLowerCase();
        for (List<Product> tier : tiers) {
            for (Product p : tier) {
                if (p.getName().toLowerCase().contains(lowerName)) return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of tiers (the 2D product structure).
     * @return The list of tiers.
     */
    public List<List<Product>> getTiers() { return tiers; }
}
