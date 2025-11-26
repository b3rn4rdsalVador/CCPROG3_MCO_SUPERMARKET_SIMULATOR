package com.example.mco2;

/**
 * A specialized type of {@code Amenity} that represents interactive service points
 * (e.g., Checkout Counter, Stations, Exits).
 * By default, they are non-passable and require subclasses to implement specific interaction logic.
 */
public class Service extends Amenity {
    
    /**
     * Constructs a Service amenity.
     * @param position The (x, y) coordinates of the service point.
     */
    public Service(Point position) {
        super(position);
    }

    /**
     * Service points are generally barriers unless overridden (e.g., CheckoutCounter is passable).
     * @return false.
     */
    @Override
    public boolean isPassable() {
        return false;
    }
}