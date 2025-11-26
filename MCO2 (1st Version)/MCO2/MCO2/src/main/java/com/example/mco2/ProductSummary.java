package com.example.mco2;

/**
 * A data structure used during checkout and inventory viewing to aggregate 
 * the quantity and total cost for a single line item.
 * It is not the same as a {@code Product}.
 */
public class ProductSummary {
    /** The name of the product being summarized. */
    private final String name;
    /** The price of one unit of the product. */
    private final double unitPrice;
    /** The current count of this product in the inventory. */
    private int quantity;

    /**
     * Constructs a ProductSummary object.
     * @param name The name of the product.
     * @param price The unit price of the product.
     */
    public ProductSummary(String name, double price) {
        this.name = name;
        this.unitPrice = price;
        this.quantity = 0;
    }

    /**
     * Increments the quantity count for this line item.
     */
    public void addQuantity() { this.quantity++; }
    
    /**
     * Calculates the total cost for this line item (unitPrice * quantity).
     * @return The calculated total price.
     */
    public double getTotalPrice() { return unitPrice * quantity; }
    
    /**
     * Provides a formatted string for the receipt or inventory view, including
     * Name, Quantity, and Total Price.
     * @return The formatted summary string.
     */
    public String toString() {
        return String.format("%-30s | Qty: %-3d | Total: PHP %.2f", name, quantity, getTotalPrice());
    }
}