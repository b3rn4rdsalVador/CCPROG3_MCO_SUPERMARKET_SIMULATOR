package com.example.mco2;

/**
 * An abstract base class for all amenities that hold products (e.g., Shelf, Refrigerator).
 * Defines abstract methods for product handling and implements basic attributes.
 */
public abstract class Display extends Amenity {
    /** The descriptive address or location of the display. */
    private final String address;

    /**
     * Constructs a Display amenity.
     * @param position The (x, y) coordinates of the display.
     * @param address The address string (e.g., "GF, R5C10").
     */
    public Display(Point position, String address) {
        super(position);
        this.address = address;
    }

    /**
     * Displays are non-passable barriers.
     * @return false.
     */
    @Override
    public boolean isPassable() { return false; }

    /**
     * Attempts to remove a product from the display.
     * @param shopper The shopper requesting the removal (needed for some logic).
     * @return The removed Product object, or null if removal failed.
     */
    public abstract Product removeProduct(Shopper shopper);
    
    /**
     * Attempts to return a product to the display, checking for type appropriateness and capacity.
     * @param product The product to be returned.
     * @return true if the product was successfully added, false otherwise.
     */
    public abstract boolean returnProduct(Product product);
    
    /**
     * Checks if the display has reached its maximum product capacity.
     * @return true if full, false otherwise.
     */
    public abstract boolean isFull();
    
    /**
     * Searches the contents of the display for a product matching the given name.
     * @param name The name fragment to search for.
     * @return true if a matching product is found, false otherwise.
     */
    public abstract boolean containsProductByName(String name);

    /**
     * Gets the address of the display.
     * @return The address string.
     */
    public String getAddress() { return address; }
}