/**
 * ProductSummary.java
 *
 * This class represents a summarized view of a single product item type within
 * a collection (either the Shopper's cart or a final receipt). It tracks the item's
 * name, unit price, quantity, and unique serial number (if provided).
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

/**
 * Represents a summary line item, used to group identical products and track their total count and price.
 * This class supports two different use cases via overloaded constructors: detailed receipt generation (with serial)
 * and simple inventory viewing (grouped by name).
 */
public class ProductSummary {
    private final String name;
    private final double unitPrice;
    private final String serialNumber;
    private int quantity;

    /**
     * Constructs a ProductSummary instance intended for final receipt generation.
     * This constructor requires the unique serial number as the primary identifier.
     *
     * @param serialNumber The unique serial number of the product item (e.g., BRD001).
     * @param name The common name of the product.
     * @param price The unit price of the product.
     */
    public ProductSummary(String serialNumber, String name, double price) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.unitPrice = price;
        this.quantity = 0;
    }

    /**
     * Constructs a ProductSummary instance intended for simple inventory viewing where grouping is by name.
     * The serial number is set to "N/A" as it is not needed for the inventory display.
     *
     * @param name The common name of the product.
     * @param price The unit price of the product.
     */
    public ProductSummary(String name, double price) {
        this.serialNumber = "N/A";
        this.name = name;
        this.unitPrice = price;
        this.quantity = 0;
    }

    /**
     * Increments the quantity count for this summary item by one.
     */
    public void addQuantity() { this.quantity++; }

    /**
     * Calculates the total price for this item summary (unit price multiplied by quantity).
     *
     * @return The total cost of all units of this product type.
     */
    public double getTotalPrice() { return unitPrice * quantity; }

    /**
     * Provides a detailed string representation of the product summary, suitable for receipt output.
     * Format includes serial number, name, quantity, and total price.
     *
     * @return A formatted string summarizing the product, quantity, and cost.
     */
    @Override
    public String toString() {
        return String.format("  [%s] %-25s | Qty: %-3d | Total: PHP %.2f",
                serialNumber, name, quantity, getTotalPrice());
    }

    /**
     * Gets the common name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current accumulated quantity of this product type.
     *
     * @return The total number of units grouped under this summary.
     */
    public int getQuantity() {
        return quantity;
    }
}