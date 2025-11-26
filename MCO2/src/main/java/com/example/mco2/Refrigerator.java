/**
 * Refrigerator.java
 *
 * This class represents a multi-tiered cold storage display for specific perishable items
 * like frozen food, milk, and cheese. It manages product storage in three distinct tiers.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a multi-tiered refrigerator display used for cold storage of specific product categories.
 * The refrigerator structure is divided into {@value #NUM_TIERS} tiers, each having a
 * fixed capacity of {@value #TIER_CAPACITY} products.
 */
public class Refrigerator extends Display {
    /** The fixed number of storage tiers in the refrigerator. */
    private static final int NUM_TIERS = 3;

    /** The maximum number of products each individual tier can hold. */
    private static final int TIER_CAPACITY = 3;

    private final List<List<Product>> tiers;

    /**
     * Constructs a new Refrigerator instance.
     * Initializes the internal list structure with the predefined number of empty tiers.
     *
     * @param position The {@link Point} coordinate where the Refrigerator is located.
     * @param address The string address/location identifier of the Refrigerator.
     */
    public Refrigerator(Point position, String address) {
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
        System.out.println("Interacting with Refrigerator at " + getAddress());
    }

    /**
     * Attempts to return a product to the first available non-full tier.
     * Only products with serial prefixes "FRZ" (Frozen), "CHS" (Cheese), or "MLK" (Milk) are allowed.
     *
     * @param product The {@link Product} to return.
     * @return {@code true} if the product was successfully added; {@code false} otherwise (wrong type or full).
     */
    @Override
    public boolean returnProduct(Product product) {
        String prefix = product.getSerialPrefix();

        if (!(prefix.equals("FRZ") || prefix.equals("CHS") || prefix.equals("MLK"))) {
            System.out.println("[Denied: Only Frozen Food, Cheese, or Milk allowed here.]");
            return false;
        }

        // Find first non-full tier
        for (List<Product> tier : tiers) {
            if (tier.size() < TIER_CAPACITY) {
                tier.add(product);
                return true;
            }
        }
        System.out.println("[Denied: This Refrigerator is full.]");
        return false;
    }

    /**
     * Removes a product from the refrigerator. (Removal logic is primarily handled by the GUI).
     *
     * @param shopper The {@link Shopper} initiating the removal.
     * @return {@code null} (removal is handled by the GUI dialog).
     */
    @Override
    public Product removeProduct(Shopper shopper) { return null; }

    /**
     * Checks if the refrigerator is completely full (i.e., all tiers are at capacity).
     *
     * @return {@code true} if every tier is full; {@code false} otherwise.
     */
    @Override
    public boolean isFull() {
        for (List<Product> tier : tiers) {
            if (tier.size() < TIER_CAPACITY) return false;
        }
        return true;
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