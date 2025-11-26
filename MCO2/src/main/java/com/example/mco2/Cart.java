/**
 * Cart.java
 *
 * This class represents a large shopping cart used by the Shopper.
 * It is a specialized type of Equipment with a high capacity.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

/**
 * Represents a shopping cart available for the Shopper to use.
 * Extends {@link Equipment} and has a high maximum capacity for large purchases.
 */
public class Cart extends Equipment {

    /**
     * Constructs a new Cart instance.
     * Initializes the Cart with a fixed name ("Cart") and a maximum capacity of 30 products.
     */
    public Cart() {
        super("Cart", 30);
    }
}