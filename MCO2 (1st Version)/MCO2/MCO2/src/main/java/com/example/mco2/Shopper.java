package com.example.mco2; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the main actor: the shopper navigating the supermarket.
 * Manages position, direction, inventory (hand-carried and equipment), and transaction state.
 */
public class Shopper {
    /** The shopper's name. */
    private final String name;
    /** The shopper's age (used for alcohol/senior checks). */
    private final int age;
    /** The current position on the map. */
    private Point position;
    /** The direction the shopper is currently facing. */
    private Direction facing;
    /** The current equipment carried (Cart or Basket), or null. */
    private Equipment equipment;
    /** Products carried directly by hand (max 2). */
    private final List<Product> handCarried;
    /** Flag indicating if the shopper has completed checkout. */
    private boolean hasCheckedOut;
    /** Flag indicating if the shopper has exited the store. */
    private boolean hasExited = false;
    /** Reference to the current map the shopper is interacting with. */
    private SupermarketMap currentMap;

    /**
     * Constructs a new Shopper object.
     * @param name The shopper's name.
     * @param age The shopper's age.
     * @param startPosition The initial position on the map.
     */
    public Shopper(String name, int age, Point startPosition) {
        this.name = name;
        this.age = age;
        this.position = startPosition;
        this.facing = Direction.NORTH;
        this.equipment = null;
        this.handCarried = new ArrayList<>(2);
        this.hasCheckedOut = false;
    }

    /**
     * Attempts to move the shopper one step in the given direction.
     * Movement is blocked if the new position is occupied by a non-passable amenity (e.g., Wall or Display).
     * @param direction The direction of movement.
     * @param map The map object used to check the amenity at the new position.
     */
    public void move(Direction direction, SupermarketMap map) {
        Point newPosition = this.position.getNextPosition(direction);
        Amenity amenity = map.getAmenityAt(newPosition.getX(), newPosition.getY());

        if (amenity == null || amenity.isPassable()) {
            this.position = newPosition;
           
            if (amenity != null) amenity.interact(this);
        } else {
            System.out.println("Blocked by " + amenity.getClass().getSimpleName());
        }
    }

    /**
     * Changes the direction the shopper is facing.
     * @param direction The new direction.
     */
    public void face(Direction direction) {
        this.facing = direction;
        System.out.println("Facing " + direction);
    }

    /**
     * Attempts to place a product in the equipment (if available) or the hand-carried list.
     * Checks for the 18+ age restriction for alcohol.
     * @param product The product to take.
     * @return true if the product was successfully taken, false if hands/equipment is full or age restriction applies.
     */
    public boolean takeProduct(Product product) {
        if (this.age < 18 && product.getSerialPrefix().equals("ALC")) {
            System.out.println("[RESTRICTED: 18+ only for Alcohol]");
            return false;
        }
        if (this.equipment != null) {
            return this.equipment.addProduct(product);
        } else {
            if (this.handCarried.size() < 2) {
                this.handCarried.add(product);
                return true;
            } else {
                System.out.println("Hands full! (Max 2)");
                return false;
            }
        }
    }

    /**
     * Attempts to remove a product from the hand-carried list or the equipment.
     * @param product The product to return.
     * @return The Product object if successfully returned, null otherwise.
     */
    public Product returnProduct(Product product) {
        if (this.handCarried.remove(product)) return product;
        if (this.equipment != null) {
            Product p = this.equipment.removeProduct(product);
            if (p != null) return p;
        }
        return null;
    }

    /**
     * Generates a formatted string summary of all products currently held by the shopper
     * (hand-carried and in equipment), aggregating quantities.
     * @return A string containing the inventory summary, or a message if empty.
     */
    public String viewChosenProducts() {
        List<Product> all = getAllProducts();
        if (all.isEmpty()) return "\nInventory is empty.";

        Map<String, ProductSummary> summaryMap = new HashMap<>();
        for (Product p : all) {
            // Note: The key should ideally be the name and a unique identifier if multiple products have the same name.
            summaryMap.putIfAbsent(p.getName(), new ProductSummary(p.getName(), p.getPrice()));
            summaryMap.get(p.getName()).addQuantity();
        }
        StringBuilder sb = new StringBuilder("\n--- Inventory ---\n");
        for (ProductSummary s : summaryMap.values()) sb.append(s.toString()).append("\n");
        return sb.toString();
    }

    /** Gets the shopper's name. */
    public String getName() { return name; }
    /** Gets the shopper's age. */
    public int getAge() { return age; }
    /** Gets the shopper's current position. */
    public Point getPosition() { return position; }
    /** Gets the direction the shopper is facing. */
    public Direction getFacing() { return facing; }
    /** Gets the equipment currently held. */
    public Equipment getEquipment() { return equipment; }
    /** Checks if the shopper is currently holding equipment. */
    public boolean hasEquipment() { return this.equipment != null; }
    /** Gets the list of products carried by hand. */
    public List<Product> getHandCarried() { return handCarried; }
    /** Checks if the shopper has completed checkout. */
    public boolean getHasCheckedOut() { return hasCheckedOut; }
    /** Checks if the shopper has exited the store. */
    public boolean getHasExited() { return hasExited; }
    
    /** Gets the current map floor the shopper is on. */
    public int getCurrentFloor() { 
        // Logic to determine floor from position is missing in Shopper, 
        // but it is handled by the map logic/UI logic. Assuming floor is 0 for simplicity here.
        return 0; 
    }

    /** Sets the exited status. */
    public void setHasExited(boolean status) { this.hasExited = status; }
    /** Sets the equipment carried. */
    public void setEquipment(Equipment newEquipment) { this.equipment = newEquipment; }
    /** Sets the checkout status. */
    public void setHasCheckedOut(boolean status) { this.hasCheckedOut = status; }
    /** Sets the current map reference. */
    public void setCurrentMap(SupermarketMap map) { this.currentMap = map; }

    /**
     * Removes and returns the equipment held by the shopper, setting the internal reference to null.
     * @return The equipment object that was removed.
     */
    public Equipment removeEquipment() {
        Equipment returned = this.equipment;
        this.equipment = null;
        return returned;
    }

    /**
     * Combines products from the hand-carried list and the equipment into a single list.
     * @return A new list containing all products.
     */
    public List<Product> getAllProducts() {
        List<Product> all = new ArrayList<>(this.handCarried);
        if (this.equipment != null) all.addAll(this.equipment.getCurrentProducts());
        return all;
    }
}