/**
 * Product.java
 *
 * This class represents a single item available for purchase in the supermarket.
 * It stores key attributes such as unique serial number, price, and categorization flags
 * (consumable, beverage).
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

/**
 * Represents a product item available for sale in the supermarket.
 * The product is defined by its serial number, name, price, and consumption attributes.
 */
public class Product {
    private final String serialNumber;
    private final String name;
    private final double price;
    private final boolean isConsumable;
    private final boolean isBeverage;

    /**
     * Constructs a new Product instance.
     *
     * @param serialNumber The unique identification code for the product (e.g., BRD001).
     * @param name The common name of the product (e.g., "Gardenia White Bread").
     * @param price The selling price of the product in PHP.
     * @param isConsumable {@code true} if the product is meant to be eaten or drunk.
     * @param isBeverage {@code true} if the product is a drink (a subset of consumable items).
     */
    public Product(String serialNumber, String name, double price, boolean isConsumable, boolean isBeverage) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
        this.isConsumable = isConsumable;
        this.isBeverage = isBeverage;
    }

    /**
     * Extracts the 3-character prefix from the serial number.
     * This prefix is typically used to identify the product category (e.g., "BRD" for bread).
     *
     * @return The 3-character serial prefix, or an empty string if the serial number is too short or null.
     */
    public String getSerialPrefix() {
        return (serialNumber != null && serialNumber.length() >= 3) ? serialNumber.substring(0, 3) : "";
    }

    /**
     * Determines if the product is classified as a solid food item.
     * A product is considered food if it is consumable but NOT a beverage.
     *
     * @return {@code true} if the product is a food item; {@code false} otherwise.
     */
    public boolean isFood() {
        return isConsumable && !isBeverage;
    }

    /**
     * Gets the full unique serial number of the product.
     *
     * @return The serial number string.
     */
    public String getSerialNumber() { return serialNumber; }

    /**
     * Gets the common name of the product.
     *
     * @return The name of the product.
     */
    public String getName() { return name; }

    /**
     * Gets the selling price of the product.
     *
     * @return The price as a double.
     */
    public double getPrice() { return price; }

    /**
     * Checks if the product is consumable (food or drink).
     *
     * @return {@code true} if consumable; {@code false} otherwise.
     */
    public boolean isConsumable() { return isConsumable; }

    /**
     * Checks if the product is a beverage (drink).
     *
     * @return {@code true} if it is a beverage; {@code false} otherwise.
     */
    public boolean isBeverage() { return isBeverage; }

    /**
     * Provides a display-friendly string representation of the product, showing the name and price.
     *
     * @return A formatted string (e.g., "Gardenia White Bread (PHP 85.00)").
     */
    @Override
    public String toString() {
        return name + " (PHP " + String.format("%.2f", price) + ")";
    }
}