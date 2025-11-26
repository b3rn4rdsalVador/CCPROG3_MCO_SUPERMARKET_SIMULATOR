/**
 * Shelf.java
 *
 * This class represents a general, multi-tiered retail shelf used to display and store
 * a wide range of non-perishable and general goods. It uses two tiers with fixed capacity.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a standard, multi-tiered display shelf in the supermarket.
 * The shelf is divided into {@value #NUM_TIERS} tiers, each holding up to {@value #TIER_CAPACITY} products.
 * It is designated for products not requiring temperature control, such as cereal, snacks, and toiletries.
 */
public class Shelf extends Display {
    /** The fixed number of storage tiers on the shelf. */
    private static final int NUM_TIERS = 2;

    /** The maximum number of products each individual tier can hold. */
    private static final int TIER_CAPACITY = 4;

    private final List<List<Product>> tiers;

    /**
     * Constructs a new Shelf instance.
     * Initializes the internal list structure with the predefined number of empty tiers.
     *
     * @param position The {@link Point} coordinate where the Shelf is located.
     * @param address The string address/location identifier of the Shelf.
     */
    public Shelf(Point position, String address) {
        super(position, address);
        this.tiers = new ArrayList<>(NUM_TIERS);
        for (int i = 0; i < NUM_TIERS; i++) {
            this.tiers.add(new ArrayList<>(TIER_CAPACITY));
        }
    }

    /**
     * Executes the interaction action, typically printing a log message as the GUI handles the pop-up.
     *
     * @param shopper The {@link Shopper} initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        System.out.println("Interacting with Shelf at " + getAddress());
    }

    /**
     * Attempts to return a product to the first available non-full tier.
     * The method checks against a comprehensive list of allowed product serial prefixes
     * (including food staples and various non-food goods).
     *
     * @param product The {@link Product} to return.
     * @return {@code true} if the product was successfully added; {@code false} otherwise (wrong type or full).
     */
    @Override
    public boolean returnProduct(Product product) {
        String prefix = product.getSerialPrefix();

        // Check against old (food/drink) and new (non-food) categories
        boolean isOldCategory = prefix.equals("CER") || prefix.equals("NDL") || prefix.equals("SNK") ||
                prefix.equals("CAN") || prefix.equals("CON") || prefix.equals("SFT") ||
                prefix.equals("JUC") || prefix.equals("ALC");

        boolean isNewCategory = prefix.equals("CLE") || prefix.equals("HOM") || prefix.equals("HAR") ||
                prefix.equals("BOD") || prefix.equals("DEN") || prefix.equals("CLO") ||
                prefix.equals("STN") || prefix.equals("PET");

        if (!isOldCategory && !isNewCategory) {
            System.out.println("[Denied: This product type is not allowed on a Shelf.]");
            return false;
        }

        // Find first non-full tier
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
     * Removes a product from the shelf. (Removal logic is primarily handled by the GUI).
     *
     * @param shopper The {@link Shopper} initiating the removal.
     * @return {@code null} (removal is handled by the GUI dialog).
     */
    @Override
    public Product removeProduct(Shopper shopper) { return null; }

    /**
     * Checks if the shelf is completely full (i.e., both tiers are at maximum capacity).
     *
     * @return {@code true} if every tier is full; {@code false} otherwise.
     */
    @Override
    public boolean isFull() {
        return tiers.get(0).size() == TIER_CAPACITY && tiers.get(1).size() == TIER_CAPACITY;
    }

    /**
     * Checks if any product in any tier matches the provided search name (case-insensitive, partial match).
     *
     * @param name The name or partial name of the product to search for.
     * @return {@code true} if the product is found in any tier; {@code false} otherwise.
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
     * Gets the raw list structure representing all tiers and their contents.
     *
     * @return A list of lists, where each inner list represents a tier of products.
     */
    public List<List<Product>> getTiers() { return tiers; }
}