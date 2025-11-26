package com.example.mco2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

/**
 * Represents the Checkout Counter where shoppers finalize their purchase.
 * Handles discount calculations (Senior discounts) and receipt generation.
 */
public class CheckoutCounter extends Service {
    /** Discount rate for food products (Senior). */
    private static final double FOOD_DISCOUNT_RATE = 0.20;
    /** Discount rate for beverage products (Senior). */
    private static final double BEVERAGE_DISCOUNT_RATE = 0.10;

    /**
     * Constructs a CheckoutCounter at the specified position.
     * @param position The (x, y) coordinates of the counter.
     */
    public CheckoutCounter(Point position) {
        super(position);
    }

    /**
     * The CheckoutCounter is designed to be passable so the shopper can stop on it.
     * @return true, allowing the shopper to pass onto the tile.
     */
    @Override
    public boolean isPassable() { return true; }

    /**
     * Processes the shopper's transaction: applies discounts, clears inventory,
     * returns equipment, and generates a receipt file.
     * @param shopper The shopper initiating the checkout process.
     */
    @Override
    public void interact(Shopper shopper) {
        List<Product> items = shopper.getAllProducts();

        if (items.isEmpty()) {
            System.out.println("Checkout denied: You must have products to check out.");
            return;
        }
        if (shopper.getHasCheckedOut()) {
            System.out.println("Checkout denied: You have already completed your transaction.");
            return;
        }

        double totalPrice = 0.0;
        double discountedTotal = 0.0;
        double totalDiscountApplied = 0.0;
        boolean isSenior = shopper.getAge() >= 60;
        Map<String, ProductSummary> summaryMap = new HashMap<>();

        for (Product item : items) {
            double itemPrice = item.getPrice();
            double discount = 0.0;

            if (isSenior && item.isConsumable()) {
                // Senior discount logic
                if (item.getSerialPrefix().equals("ALC")) discount = 0.0; // Alcohol excluded
                else if (item.isFood()) discount = itemPrice * FOOD_DISCOUNT_RATE;
                else if (item.isBeverage()) discount = itemPrice * BEVERAGE_DISCOUNT_RATE;
            }

            double finalPrice = itemPrice - discount;
            totalPrice += itemPrice;
            discountedTotal += finalPrice;
            totalDiscountApplied += discount;

            summaryMap.putIfAbsent(item.getSerialNumber(), new ProductSummary(item.getName(), item.getPrice()));
            summaryMap.get(item.getSerialNumber()).addQuantity();
        }

        // For receipt generating
        String filename = "receipt_" + shopper.getName() + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("--- Supermarket Receipt ---\n");
            writer.write("Shopper: " + shopper.getName() + " (Age: " + shopper.getAge() + ")\n");
            writer.write("Transaction Date: " + new Date() + "\n");
            writer.write("--- Items Purchased ---\n");
            for (ProductSummary summary : summaryMap.values()) {
                writer.write(summary.toString() + "\n");
            }
            writer.write("\n-----------------------------------\n");
            writer.write(String.format("Total Price: PHP %.2f\n", totalPrice));
            if (isSenior) writer.write(String.format("Senior Discount: PHP %.2f\n", totalDiscountApplied));
            writer.write(String.format("FINAL TOTAL: PHP %.2f\n", discountedTotal));
            writer.write("-----------------------------------\n");

            System.out.println("\nSUCCESS: Transaction complete!");
            System.out.println(String.format("Final Total: PHP %.2f", discountedTotal));
            System.out.println("Receipt saved to " + filename);
        } catch (IOException e) {
            System.out.println("ERROR: Could not save receipt file.");
        }

        // Finalize transaction state
        if (shopper.hasEquipment()) {
            shopper.removeEquipment();
            System.out.println("Your equipment has been returned.");
        }
        shopper.getHandCarried().clear();
        shopper.setHasCheckedOut(true);
    }
}