/**
 * Table.java
 *
 * This class represents a simple display table typically used for fresh produce
 * and baked goods that do not require specialized temperature control.
 * It manages product storage in a single, flat list structure.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a basic display table amenity in the supermarket.
 * Tables are used for non-refrigerated produce and bakery items and store products
 * in a single list with a fixed maximum capacity.
 */
public class Table extends Display {
    /** The maximum number of products the table can hold. */
    private static final int MAX_CAPACITY = 4;
    private final List<Product> products;

    /**
     * Constructs a new Table instance.
     *
     * @param position The {@link Point} coordinate where the Table is located.
     * @param address The string address/location identifier of the Table.
     */
    public Table(Point position, String address) {
        super(position, address);
        this.products = new ArrayList<>(MAX_CAPACITY);
    }

    /**
     * Executes the interaction action, typically printing a log message as the GUI handles the pop-up.
     *
     * @param shopper The {@link Shopper} initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        System.out.println("Interacting with Table at " + getAddress());
    }

    /**
     * Attempts to return a product to the table.
     * Only products with serial prefixes "FRU" (Fruits), "BRD" (Bread), "EGG" (Eggs), or "VEG" (Vegetables) are allowed.
     *
     * @param product The {@link Product} to return.
     * @return {@code true} if the product was successfully added; {@code false} otherwise (wrong type or full).
     */
    @Override
    public boolean returnProduct(Product product) {
        String prefix = product.getSerialPrefix();

        // UPDATE: Added Bread (BRD), Eggs (EGG), and Vegetables (VEG)
        boolean isAllowed = prefix.equals("FRU") ||
                prefix.equals("BRD") ||
                prefix.equals("EGG") ||
                prefix.equals("VEG");

        if (!isAllowed) {
            System.out.println("[Denied: This item cannot be placed on a Table.]");
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
     * Removes a product from the table. (Removal logic is primarily handled by the GUI).
     *
     * @param shopper The {@link Shopper} initiating the removal.
     * @return {@code null} (removal is handled by the GUI dialog).
     */
    @Override
    public Product removeProduct(Shopper shopper) { return null; }

    /**
     * Checks if the table is full.
     *
     * @return {@code true} if the list is at maximum capacity, {@code false} otherwise.
     */
    @Override
    public boolean isFull() { return this.products.size() >= MAX_CAPACITY; }

    /**
     * Checks if any product matching the provided name is currently on the table (case-insensitive, partial match).
     *
     * @param name The name to search for.
     * @return {@code true} if the product is found; {@code false} otherwise.
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
     * Gets the raw list of products currently stored on the table.
     *
     * @return The list of products.
     */
    public List<Product> getProducts() { return products; }
}