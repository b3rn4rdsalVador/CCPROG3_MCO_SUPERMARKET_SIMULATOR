package com.example.mco2;

/**
 * Represents the entry point to the supermarket.
 * The entrance becomes sealed (non-passable) after the first shopper enters.
 */
public class Entrance extends Service {
    /** Flag indicating if the entrance has already been used by a shopper. */
    private boolean hasBeenUsed;

    /**
     * Constructs an Entrance amenity.
     * @param position The (x, y) coordinates of the amenity.
     */
    public Entrance(Point position) {
        super(position);
        this.hasBeenUsed = false;
    }

    /**
     * The entrance is passable only if it has not been used yet.
     * @return true if it has not been used, false otherwise.
     */
    @Override
    public boolean isPassable() { return !this.hasBeenUsed; }

    /**
     * Defines the interaction message based on whether the entrance has been used.
     * @param shopper The shopper initiating the interaction.
     */
    @Override
    public void interact(Shopper shopper) {
        if (!this.hasBeenUsed) {
            System.out.println("You are at the Entrance. Move (W/A/S/D) to enter.");
        } else {
            System.out.println("You are at the Entrance. It is now sealed.");
        }
    }

    /**
     * Seals the entrance, setting the usage flag to true.
     */
    public void setHasBeenUsed() { this.hasBeenUsed = true; }
}