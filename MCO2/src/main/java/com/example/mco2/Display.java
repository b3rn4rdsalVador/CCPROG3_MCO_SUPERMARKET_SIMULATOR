/**
 * Display.java
 *
 * This abstract class serves as the base for all amenities in the supermarket
 * that hold products, such as Shelves, Refrigerators, and Counters.
 * It defines the essential contract for product retrieval, stocking, and status checks.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

/**
 * An abstract class representing a fixed amenity on the map used to display and store products.
 * Displays are generally impassable and provide the main interaction points for a Shopper
 * to acquire or return items.
 */
public abstract class Display extends Amenity {
    private final String address;

    /**
     * Constructs a new Display instance.
     *
     * @param position The {@link Point} coordinate where the Display is located.
     * @param address A descriptive string address or location identifier for the Display (e.g., "GF, R5C10").
     */
    public Display(Point position, String address) {
        super(position);
        this.address = address;
    }

    /**
     * Overrides the default impassable status defined in {@link Amenity}.
     * Displays cannot be passed through.
     *
     * @return always {@code false}.
     */
    @Override
    public boolean isPassable() { return false; }

    // These abstract methods form the contract for all concrete Display implementations.

    /**
     * Handles the removal of a product from the display's inventory by the shopper.
     * <p>
     * NOTE: Actual removal logic is often handled by the GUI after selection,
     * so this method in concrete classes often returns {@code null} or triggers a more complex flow.
     *
     * @param shopper The {@link Shopper} attempting to take the product.
     * @return The {@link Product} removed, or {@code null} if unsuccessful or removal is GUI-driven.
     */
    public abstract Product removeProduct(Shopper shopper);

    /**
     * Attempts to return (stock) a product onto the display.
     * Concrete implementations must contain logic to check if the product type is allowed.
     *
     * @param product The {@link Product} to be placed back onto the display.
     * @return {@code true} if the product was successfully stocked; {@code false} if the display is full or the product type is disallowed.
     */
    public abstract boolean returnProduct(Product product);

    /**
     * Checks if the display has reached its maximum product capacity.
     *
     * @return {@code true} if the display is full; {@code false} otherwise.
     */
    public abstract boolean isFull();

    /**
     * Checks if the display currently holds a product whose name matches the search term.
     * This is used primarily by the {@link ProductSearch} amenity.
     *
     * @param name The name or partial name of the product to search for.
     * @return {@code true} if the product is found; {@code false} otherwise.
     */
    public abstract boolean containsProductByName(String name);

    /**
     * Gets the descriptive address or location identifier of the display.
     *
     * @return The location string (e.g., "GF, R5C10").
     */
    public String getAddress() { return address; }
}