package com.example.mco2; 

import java.util.ArrayList;
import java.util.List;

/**
 * The core class managing the supermarket's layout, inventory, and location data.
 * Contains the grid structure and methods for amenity lookup and stocking.
 */
public class SupermarketMap {
    /** The fixed size of the square map grid (rows and columns). */
    private static final int MAP_SIZE = 22;
    /** Constant for the name of the Ground Floor. */
    private static final String GF = "GF";

    /** The 2D array representing the map grid, holding Amenity objects. */
    private final Amenity[][] grid;
    /** A complete list of all distinct product types available in the supermarket. */
    private final List<Product> allProducts;
    /** A list of all Display amenities (Shelves, Counters, Tables) for easy iteration. */
    private final List<Display> allDisplays;

    /**
     * Constructs and initializes the SupermarketMap, setting up the grid layout,
     * product list, and initial display stocking.
     */
    public SupermarketMap() {
        this.grid = new Amenity[MAP_SIZE][MAP_SIZE];
        this.allProducts = new ArrayList<>();
        this.allDisplays = new ArrayList<>();
        initializeGF();
    }

    /**
     * Initializes the Ground Floor layout, including placing all amenities
     * and defining the master list of all sellable products.
     */
    private void initializeGF() {
        // ... (product and map layout initialization logic) ...
    }

    /**
     * Stocks the Display amenities (Shelves, Counters, Tables) with specific products
     * based on their location and prefix requirements.
     */
    private void stockDisplays() {
        // ... (stocking logic) ...
    }

    /**
     * Determines the product serial prefix required for a specific Display amenity
     * based on its type and location on the grid.
     * @param d The Display amenity.
     * @return The required product prefix string.
     */
    private String getPrefixForDisplay(Display d) {
        // ... (prefix lookup logic) ...
        return "";
    }

    /**
     * Filters the master product list to find all products matching a given prefix.
     * @param prefix The serial number prefix (e.g., "FRU").
     * @return A list of matching Product objects.
     */
    private List<Product> getProductsByPrefix(String prefix) {
        List<Product> filtered = new ArrayList<>();
        for (Product p : allProducts) {
            if (p.getSerialPrefix().equals(prefix)) filtered.add(p);
        }
        return filtered;
    }

    /**
     * Retrieves the Amenity object located at the specified (x, y) coordinates.
     * @param x The X-coordinate (column).
     * @param y The Y-coordinate (row).
     * @return The Amenity at that location, or null if coordinates are out of bounds or the tile is empty.
     */
    public Amenity getAmenityAt(int x, int y) {
        if (x < 0 || x >= MAP_SIZE || y < 0 || y >= MAP_SIZE) return null;
        return grid[y][x];
    }

    /**
     * Retrieves the Amenity object located in the square immediately in front of the given position
     * and direction (i.e., where the shopper is looking).
     * @param pos The shopper's current position Point.
     * @param dir The shopper's current facing Direction.
     * @return The Amenity in the line of sight, or null if the square is empty or out of bounds.
     */
    public Amenity getAmenityInVision(Point pos, Direction dir) {
        Point vision = pos.getNextPosition(dir);
        return getAmenityAt(vision.getX(), vision.getY());
    }

    /**
     * Gets the list of all Display amenities on the map.
     * @return The list of Display objects.
     */
    public List<Display> getAllDisplays() { return allDisplays; }
}