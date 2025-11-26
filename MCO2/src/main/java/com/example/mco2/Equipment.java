/**
 * Equipment.java
 *
 * This abstract class serves as the base for all shopping equipment a Shopper
 * can use, such as Carts and Baskets. It defines the core logic for managing
 * the capacity and inventory of products carried by the equipment.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class representing equipment (like a Cart or Basket) used by the {@link Shopper}
 * to carry multiple {@link Product} items.
 * It manages the capacity and the list of products currently held.
 */
public abstract class Equipment {
    private final String name;
    private final int maxCapacity;
    private final List<Product> currentProducts;

    /**
     * Constructs a new Equipment instance.
     *
     * @param name The descriptive name of the equipment (e.g., "Cart", "Basket").
     * @param maxCapacity The maximum number of products the equipment can hold.
     */
    public Equipment(String name, int maxCapacity) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.currentProducts = new ArrayList<>(maxCapacity);
    }

    /**
     * Attempts to add a product to the equipment.
     * The product is only added if the equipment is not currently full.
     *
     * @param product The {@link Product} to be added.
     * @return {@code true} if the product was successfully added; {@code false} if the equipment is full.
     */
    public boolean addProduct(Product product) {
        if (isFull()) return false;
        currentProducts.add(product);
        return true;
    }

    /**
     * Attempts to remove a specific product instance from the equipment.
     *
     * @param product The exact {@link Product} instance to remove.
     * @return The removed {@link Product} if successful; {@code null} if the product was not found in the equipment.
     */
    public Product removeProduct(Product product) {
        return currentProducts.remove(product) ? product : null;
    }

    /**
     * Checks if the equipment is currently at its maximum capacity.
     *
     * @return {@code true} if the number of products equals or exceeds the max capacity; {@code false} otherwise.
     */
    public boolean isFull() { return currentProducts.size() >= maxCapacity; }

    /**
     * Checks if the equipment currently holds no products.
     *
     * @return {@code true} if the list of current products is empty; {@code false} otherwise.
     */
    public boolean isEmpty() { return currentProducts.isEmpty(); }

    /**
     * Gets the name of the equipment.
     *
     * @return The descriptive name (e.g., "Cart").
     */
    public String getName() { return name; }

    /**
     * Gets the mutable list of products currently held by the equipment.
     *
     * @return The {@code List<Product>} representing the current inventory of the equipment.
     */
    public List<Product> getCurrentProducts() { return currentProducts; }
}