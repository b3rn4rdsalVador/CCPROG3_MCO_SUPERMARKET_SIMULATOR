package com.example.mco2;

/**
 * Represents a small, movable container for products.
 * Inherits product handling logic and capacity from the abstract Equipment class.
 */
public class Basket extends Equipment {

    /**
     * Constructs a Basket with a fixed name and capacity (15).
     */
    public Basket() {
        super("Basket", 15);
    }
}