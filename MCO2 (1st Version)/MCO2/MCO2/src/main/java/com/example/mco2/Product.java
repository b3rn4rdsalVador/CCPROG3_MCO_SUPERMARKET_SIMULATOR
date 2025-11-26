package com.example.mco2;

/**
 * Represents a single product item available in the supermarket.
 * Stores immutable details like serial number, name, price, and type characteristics.
 */
public class Product {
    /** The unique identification number for the product. */
    private final String serialNumber;
    /** The display name of the product. */
    private final String name;
    /** The base retail price of the product. */
    private final double price;
    /** Flag indicating if the product can be consumed (i.e., not a tool, clothing, etc.). */
    private final boolean isConsumable;
    /** Flag indicating if the product is a drink (used for specific discount rules). */
    private final boolean isBeverage;

    /**
     * Constructs a Product object.
     * @param serialNumber The unique serial number (e.g., "FRU00001").
     * @param name The product name.
     * @param price The unit price.
     * @param isConsumable True if the product is edible/usable, false otherwise.
     * @param isBeverage True if the product is a drink.
     */
    public Product(String serialNumber, String name, double price, boolean isConsumable, boolean isBeverage) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
        this.isConsumable = isConsumable;
        this.isBeverage = isBeverage;
    }

    /**
     * Extracts the first three characters of the serial number, used for product classification
     * and placement rules (e.g., "FRU").
     * @return The 3-character serial prefix, or an empty string if not available.
     */
    public String getSerialPrefix() {
        return (serialNumber != null && serialNumber.length() >= 3) ? serialNumber.substring(0, 3) : "";
    }

    /**
     * Checks if the product is a non-beverage consumable item (i.e., solid food).
     * Used primarily for discount calculation logic.
     * @return true if consumable but not a beverage, false otherwise.
     */
    public boolean isFood() {
        return isConsumable && !isBeverage;
    }

    /** Gets the serial number. */
    public String getSerialNumber() { return serialNumber; }
    /** Gets the product name. */
    public String getName() { return name; }
    /** Gets the price. */
    public double getPrice() { return price; }
    /** Checks if the product is consumable. */
    public boolean isConsumable() { return isConsumable; }
    /** Checks if the product is a beverage. */
    public boolean isBeverage() { return isBeverage; }

    /**
     * Provides a formatted string representation of the product (Name and Price).
     * @return The product name and price formatted to two decimal places.
     */
    @Override
    public String toString() {
        return name + " (PHP " + String.format("%.2f", price) + ")";
    }
}