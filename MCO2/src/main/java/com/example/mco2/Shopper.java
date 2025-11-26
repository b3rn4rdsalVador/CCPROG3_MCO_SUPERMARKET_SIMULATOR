/**
 * Shopper.java
 *
 * This class represents the user-controlled character in the supermarket simulation.
 * It manages the shopper's state, including position, inventory, movement logic,
 * and interaction capabilities with amenities.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the shopper navigating the supermarket.
 * Manages the shopper's location, carried equipment, product inventory (hand-carried and equipment),
 * and movement rules across different floors and amenities.
 */
public class Shopper {
    private final String name;
    private final int age;
    private Point position;
    private Direction facing;
    private int currentFloor; // 0 = GF, 1 = 2F

    private Equipment equipment;
    private final List<Product> handCarried;
    private boolean hasCheckedOut;
    private boolean hasExited = false;
    private SupermarketMap currentMap;

    /**
     * Constructs a new Shopper instance.
     *
     * @param name The name of the shopper.
     * @param age The age of the shopper (used for age-restricted purchases and discounts).
     * @param startPosition The initial {@link Point} coordinate of the shopper on the map.
     */
    public Shopper(String name, int age, Point startPosition) {
        this.name = name;
        this.age = age;
        this.position = startPosition;
        this.facing = Direction.NORTH;
        this.currentFloor = 0; // Start at GF
        this.equipment = null;
        this.handCarried = new ArrayList<>(2);
        this.hasCheckedOut = false;
    }

    /**
     * Attempts to move the shopper one tile in the specified direction.
     * Movement is blocked if the target amenity is impassable (e.g., Wall, Display).
     * Handles floor transitions if the shopper walks onto {@link Stairs}.
     *
     * @param direction The {@link Direction} of movement.
     * @param map The {@link SupermarketMap} used to check amenities and boundaries.
     */
    public void move(Direction direction, SupermarketMap map) {
        Point newPosition = this.position.getNextPosition(direction);

        // Check amenity on CURRENT FLOOR
        Amenity targetAmenity = map.getAmenityAt(newPosition.getX(), newPosition.getY(), this.currentFloor);

        if (targetAmenity == null || targetAmenity.isPassable()) {

            // --- ENTRANCE LOCKING LOGIC ---
            Amenity currentAmenity = map.getAmenityAt(this.position.getX(), this.position.getY(), this.currentFloor);
            if (currentAmenity instanceof Entrance) {
                ((Entrance) currentAmenity).setHasBeenUsed();
            }
            // ------------------------------

            this.position = newPosition;

            // Check if we walked onto Stairs
            if (targetAmenity instanceof Stairs) {
                // Simple Toggle Logic: 0 -> 1, or 1 -> 0
                int oldFloor = this.currentFloor;
                this.currentFloor = (this.currentFloor == 0) ? 1 : 0;
                System.out.println("Traveled from Floor " + oldFloor + " to " + this.currentFloor);
                // Note: We stay at the same (x,y), assuming stairs are vertically aligned
            }
            // Normal Interaction (Exit/Stations)
            else if (targetAmenity != null) {
                targetAmenity.interact(this);
            }

        } else {
            System.out.println("Blocked by " + (targetAmenity != null ? targetAmenity.getClass().getSimpleName() : "null"));
        }
    }

    /**
     * Sets the direction the shopper is currently facing.
     *
     * @param direction The new {@link Direction} the shopper is facing.
     */
    public void face(Direction direction) { this.facing = direction; }

    /**
     * Attempts to take a product and add it to the shopper's inventory.
     * Checks capacity of equipment (if held) or hand-carry slots (max 2).
     * Enforces age restriction on alcohol ("ALC" prefix).
     *
     * @param product The {@link Product} to be taken.
     * @return {@code true} if the product was successfully added; {@code false} if inventory is full or restricted.
     */
    public boolean takeProduct(Product product) {
        if (this.age < 18 && product.getSerialPrefix().equals("ALC")) return false;
        if (this.equipment != null) return this.equipment.addProduct(product);
        if (this.handCarried.size() < 2) { this.handCarried.add(product); return true; }
        return false;
    }

    /**
     * Attempts to remove a product from the shopper's inventory (hand-carried or equipment).
     *
     * @param product The exact {@link Product} instance to return.
     * @return The removed {@link Product} if successful; {@code null} if the product was not found.
     */
    public Product returnProduct(Product product) {
        if (this.handCarried.remove(product)) return product;
        if (this.equipment != null) return this.equipment.removeProduct(product);
        return null;
    }

    /**
     * Generates a simplified string summary of all products currently held by the shopper
     * (hand-carried and equipment). Items are grouped by name, showing only the name and quantity.
     *
     * @return A formatted string listing all unique products and their counts, or an empty string if inventory is empty.
     */
    public String viewChosenProducts() {
        List<Product> all = getAllProducts();
        if (all.isEmpty()) return "";

        // 1. Group and Count items using the 2-argument constructor
        Map<String, ProductSummary> summaryMap = new HashMap<>();
        for (Product p : all) {
            // This calls the 2-argument constructor: ProductSummary(String name, double price)
            summaryMap.putIfAbsent(p.getName(), new ProductSummary(p.getName(), p.getPrice()));
            summaryMap.get(p.getName()).addQuantity();
        }

        // 2. Build SIMPLIFIED String (Name and Quantity only)
        StringBuilder sb = new StringBuilder();
        // NOTE: This relies on ProductSummary.java having a public method: public int getQuantity()
        for (ProductSummary s : summaryMap.values()) {
            sb.append(String.format("%-30s | Qty: %-3d\n", s.getName(), s.getQuantity()));
        }
        return sb.toString();
    }

    /**
     * Gets the name of the shopper.
     * @return The shopper's name.
     */
    public String getName() { return name; }

    /**
     * Gets the age of the shopper.
     * @return The shopper's age.
     */
    public int getAge() { return age; }

    /**
     * Gets the current position of the shopper on the map grid.
     * @return The shopper's current {@link Point}.
     */
    public Point getPosition() { return position; }

    /**
     * Gets the direction the shopper is currently facing.
     * @return The shopper's current {@link Direction}.
     */
    public Direction getFacing() { return facing; }

    /**
     * Gets the current floor the shopper is on.
     * @return The current floor index (0 for Ground Floor, 1 for 2nd Floor).
     */
    public int getCurrentFloor() { return currentFloor; } // New Getter

    /**
     * Gets the equipment (Cart or Basket) the shopper is currently using.
     * @return The {@link Equipment} object, or {@code null} if none is held.
     */
    public Equipment getEquipment() { return equipment; }

    /**
     * Checks if the shopper is currently holding any equipment.
     * @return {@code true} if equipment is held; {@code false} otherwise.
     */
    public boolean hasEquipment() { return this.equipment != null; }

    /**
     * Gets the list of products the shopper is carrying by hand.
     * @return The list of hand-carried {@link Product}s.
     */
    public List<Product> getHandCarried() { return handCarried; }

    /**
     * Checks if the shopper has completed the checkout process.
     * @return {@code true} if the shopper has checked out; {@code false} otherwise.
     */
    public boolean getHasCheckedOut() { return hasCheckedOut; }

    /**
     * Checks if the shopper has successfully exited the supermarket.
     * @return {@code true} if the shopper has exited; {@code false} otherwise.
     */
    public boolean getHasExited() { return hasExited; }

    /**
     * Sets the exit status of the shopper.
     * @param status The new exit status.
     */
    public void setHasExited(boolean status) { this.hasExited = status; }

    /**
     * Assigns new equipment (Cart or Basket) to the shopper.
     * @param newEquipment The {@link Equipment} to assign.
     */
    public void setEquipment(Equipment newEquipment) { this.equipment = newEquipment; }

    /**
     * Sets the checked-out status of the shopper.
     * @param status The new checkout status.
     */
    public void setHasCheckedOut(boolean status) { this.hasCheckedOut = status; }

    /**
     * Sets the reference to the current {@link SupermarketMap}.
     * @param map The current map instance.
     */
    public void setCurrentMap(SupermarketMap map) { this.currentMap = map; }

    /**
     * Removes the equipment currently held by the shopper and returns it.
     * @return The {@link Equipment} that was held, or {@code null}.
     */
    public Equipment removeEquipment() {
        Equipment returned = this.equipment;
        this.equipment = null;
        return returned;
    }

    /**
     * Compiles a single list of all products currently held by the shopper (hand-carried and equipment).
     * @return A consolidated list of all {@link Product}s.
     */
    public List<Product> getAllProducts() {
        List<Product> all = new ArrayList<>(this.handCarried);
        if (this.equipment != null) all.addAll(this.equipment.getCurrentProducts());
        return all;
    }
}