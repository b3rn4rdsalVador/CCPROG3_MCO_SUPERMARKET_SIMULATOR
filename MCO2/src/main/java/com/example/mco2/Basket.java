/**
 * Basket.java
 *
 * This class represents a small, hand-held shopping basket used by the Shopper.
 * It is a specialized type of Equipment with a fixed capacity.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

/**
 * Represents a standard shopping basket available for the Shopper to use.
 * Extends {@link Equipment} and has a lower capacity suitable for small purchases.
 */
public class Basket extends Equipment {

    /**
     * Constructs a new Basket instance.
     * Initializes the Basket with a fixed name ("Basket") and a maximum capacity of 15 products.
     */
    public Basket() {
        super("Basket", 15);
    }
}