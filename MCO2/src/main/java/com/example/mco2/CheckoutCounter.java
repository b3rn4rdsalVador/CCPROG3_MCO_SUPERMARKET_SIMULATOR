/**
 * CheckoutCounter.java
 *
 * This class represents a service point where the Shopper can finalize their
 * shopping trip, pay for their items, receive applicable discounts, and generate a receipt file.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import javafx.scene.control.Alert;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

/**
 * Represents the checkout service amenity on the map.
 * This counter is responsible for calculating totals, applying discounts (e.g., Senior Citizen),
 * generating a receipt, and finalizing the shopper's status.
 */
public class CheckoutCounter extends Service {

    /** The discount rate applied to food products for eligible shoppers. */
    private static final double FOOD_DISCOUNT_RATE = 0.20;

    /** The discount rate applied to non-food/non-alcoholic beverages for eligible shoppers. */
    private static final double BEVERAGE_DISCOUNT_RATE = 0.10;

    /**
     * Constructs a new CheckoutCounter at the specified map position.
     *
     * @param position The (x, y) coordinate where the CheckoutCounter is located.
     */
    public CheckoutCounter(Point position) {
        super(position);
    }

    /**
     * Overrides the default impassable status. The CheckoutCounter is passable
     * so the shopper can move onto it to interact.
     *
     * @return always {@code true}.
     */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * Handles the checkout and payment process for the shopper.
     * This method calculates the total, applies senior discounts, generates a receipt
     * file, and clears the shopper's inventory.
     *
     * @param shopper The {@link Shopper} instance initiating the transaction.
     */
    @Override
    public void interact(Shopper shopper) {
        List<Product> items = shopper.getAllProducts();

        // 1. CHECK: No Products
        if (items.isEmpty()) {
            showPopup(Alert.AlertType.WARNING, "Checkout Denied", "You have no products to pay for.");
            return;
        }

        // 2. CHECK: Already Paid
        if (shopper.getHasCheckedOut()) {
            showPopup(Alert.AlertType.INFORMATION, "Status", "You have already paid for your items.");
            return;
        }

        // 3. CALCULATE TOTALS
        double totalPrice = 0.0;
        double discountedTotal = 0.0;
        double totalDiscountApplied = 0.0;
        boolean isSenior = shopper.getAge() >= 60;

        // Uses the full serial number as the unique key to accurately summarize items
        Map<String, ProductSummary> summaryMap = new HashMap<>();

        for (Product item : items) {
            double itemPrice = item.getPrice();
            double discount = 0.0;

            if (isSenior && item.isConsumable()) {
                if (item.getSerialPrefix().equals("ALC")) discount = 0.0; // No discount on alcohol
                else if (item.isFood()) discount = itemPrice * FOOD_DISCOUNT_RATE;
                else if (item.isBeverage()) discount = itemPrice * BEVERAGE_DISCOUNT_RATE;
            }

            double finalPrice = itemPrice - discount;
            totalPrice += itemPrice;
            discountedTotal += finalPrice;
            totalDiscountApplied += discount;

            // Using full serial number for summary key and constructor
            String serial = item.getSerialNumber();

            // Note: ProductSummary must have a 3-argument constructor for (serial, name, price)
            summaryMap.putIfAbsent(serial, new ProductSummary(serial, item.getName(), item.getPrice()));
            summaryMap.get(serial).addQuantity();
        }

        // 4. GENERATE RECEIPT FILE
        String filename = "receipt_" + shopper.getName() + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("--- Supermarket Receipt ---\n");
            writer.write("Shopper: " + shopper.getName() + " (Age: " + shopper.getAge() + ")\n");
            writer.write("Transaction Date: " + new Date() + "\n");
            writer.write("--- Items Purchased ---\n");

            // ProductSummary.toString() now includes the serial number
            for (ProductSummary summary : summaryMap.values()) {
                writer.write(summary.toString() + "\n");
            }

            writer.write("\n-----------------------------------\n");
            writer.write(String.format("Total Price: PHP %.2f\n", totalPrice));
            if (isSenior) {
                writer.write(String.format("Senior Discount: PHP %.2f\n", totalDiscountApplied));
            }
            writer.write(String.format("FINAL TOTAL: PHP %.2f\n", discountedTotal));
            writer.write("-----------------------------------\n");

            // 5. SHOW SUCCESS POPUP
            String message = String.format("Total: PHP %.2f\nDiscount: PHP %.2f\nPaid: PHP %.2f\n\nReceipt saved to file.",
                    totalPrice, totalDiscountApplied, discountedTotal);
            showPopup(Alert.AlertType.INFORMATION, "Transaction Complete", message);

        } catch (IOException e) {
            showPopup(Alert.AlertType.ERROR, "Error", "Could not save receipt file. Check write permissions.");
        }

        // 6. FINALIZE: Clear equipment and items, set checked-out status
        if (shopper.hasEquipment()) {
            shopper.removeEquipment();
        }
        shopper.getHandCarried().clear();
        shopper.setHasCheckedOut(true);
    }

    /**
     * Helper method to display a JavaFX alert dialog.
     *
     * @param type The type of alert (e.g., INFORMATION, ERROR).
     * @param title The title of the dialog window.
     * @param message The content text displayed inside the dialog.
     */
    private void showPopup(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}