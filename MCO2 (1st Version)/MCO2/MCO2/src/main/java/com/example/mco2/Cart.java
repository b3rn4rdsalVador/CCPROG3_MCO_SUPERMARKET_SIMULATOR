package com.example.mco2;

/**
 * Represents a large, movable container for products.
 * Inherits product handling logic and capacity from the abstract Equipment class.
 */
public class Cart extends Equipment {
    /**
     * Constructs a Cart with a fixed name and capacity (30).
     */
    public Cart() { super("Cart", 30); }
}