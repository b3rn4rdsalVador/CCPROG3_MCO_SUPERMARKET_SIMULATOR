/**
 * Service.java
 *
 * This class serves as the abstract base for all non-product-holding amenities
 * that facilitate specific actions for the Shopper, such as the Checkout Counter,
 * Cart Stations, and Stairs.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

/**
 * Represents a functional amenity on the map that provides a specific service
 * or interaction logic, but does not display products.
 * <p>
 * Examples of Services include {@link CheckoutCounter}, {@link CartStation}, and {@link Stairs}.
 * By default, Services are impassable, but most concrete subclasses (like Stairs or Checkout)
 * override this to allow the shopper to move onto the tile.
 */
public class Service extends Amenity {

    /**
     * Constructs a new Service amenity.
     *
     * @param position The {@link Point} coordinate where the Service is located.
     */
    public Service(Point position) {
        super(position);
    }

    /**
     * Determines if the Service tile is passable.
     * Subclasses usually override this method to return {@code true}.
     *
     * @return {@code false} as the default for an abstract service type.
     */
    @Override
    public boolean isPassable() {
        return false;
    }
}