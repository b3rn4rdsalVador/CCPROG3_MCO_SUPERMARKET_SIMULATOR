package com.example.mco2;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all movable inventory containers (Cart, Basket).
 * Manages product storage, capacity, and basic inventory operations.
 */
public abstract class Equipment {
    /** The display name of the equipment (e.g., "Cart"). */
    private final String name;
    /** The maximum number of products the equipment can hold. */
    private final int maxCapacity;
    /** The list of products currently held by the equipment. */
    private final List<Product> currentProducts;

    /**
     * Constructs an Equipment object.
     * @param name The name of the equipment.
     * @param maxCapacity The maximum product capacity.
     */
    public Equipment(String name, int maxCapacity) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.currentProducts = new ArrayList<>(maxCapacity);
    }

    /**
     * Attempts to add a product to the equipment.
     * @param product The product to add.
     * @return true if the product was added successfully (not full), false otherwise.
     */
    public boolean addProduct(Product product) {
        if (isFull()) return false;
        currentProducts.add(product);
        return true;
    }

    /**
     * Attempts to remove a specific product from the equipment.
     * @param product The product to remove.
     * @return The removed Product object if successful, or null if the product was not found.
     */
    public Product removeProduct(Product product) {
        return currentProducts.remove(product) ? product : null;
    }

    /**
     * Checks if the equipment is full.
     * @return true if capacity is reached, false otherwise.
     */
    public boolean isFull() { return currentProducts.size() >= maxCapacity; }
    
    /**
     * Checks if the equipment is empty.
     * @return true if no products are inside, false otherwise.
     */
    public boolean isEmpty() { return currentProducts.isEmpty(); }
    
    /**
     * Gets the name of the equipment.
     * @return The name string.
     */
    public String getName() { return name; }
    
    /**
     * Gets the list of products currently in the equipment.
     * @return The list of products.
     */
    public List<Product> getCurrentProducts() { return currentProducts; }
}