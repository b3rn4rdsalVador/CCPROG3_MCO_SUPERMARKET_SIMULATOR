/**
 * SupermarketMap.java
 *
 * This class represents the entire layout and product catalog of the multi-story supermarket.
 * It is responsible for initializing the map grid, populating the product data,
 * placing amenities (Walls, Displays, Services), and managing access to map tiles.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the layout, product data, and amenity placement for the two-floor supermarket simulation.
 * It provides methods for checking the contents and position of amenities on both floors.
 */
public class SupermarketMap {
    /** The uniform size (width and height) of the square map grid for each floor. */
    private static final int MAP_SIZE = 22;

    /** Identifier for the Ground Floor. */
    private static final String GF_NAME = "GF";

    /** Identifier for the Second Floor. */
    private static final String SECOND_F_NAME = "2F";

    private final List<Amenity[][]> floors;
    private final List<Product> allProducts;
    private final List<Display> allDisplays;

    /**
     * Constructs the SupermarketMap, initializing the grid structure for two floors,
     * loading all product data, and parsing the map layouts to place amenities.
     */
    public SupermarketMap() {
        this.floors = new ArrayList<>();
        this.floors.add(new Amenity[MAP_SIZE][MAP_SIZE]); // Floor 0
        this.floors.add(new Amenity[MAP_SIZE][MAP_SIZE]); // Floor 1

        this.allProducts = new ArrayList<>();
        this.allDisplays = new ArrayList<>();

        initializeProducts();
        initializeLayouts();
    }

    /**
     * Populates the internal list with all defined {@link Product} items, categorized by their
     * 3-letter serial prefix (e.g., BRD, FRZ, CHK).
     */
    private void initializeProducts() {
        // ==========================================
        //       2ND FLOOR PRODUCTS (Your New List)
        // ==========================================

        // 1. BREAD (BRD)
        allProducts.add(new Product("BRD001", "Gardenia White Bread", 85.00, true, false));
        allProducts.add(new Product("BRD002", "Pan de Manila Pandesal", 60.00, true, false));
        allProducts.add(new Product("BRD003", "Monde Special Mamon", 75.00, true, false));

        // 2. EGGS (EGG)
        allProducts.add(new Product("EGG001", "Bounty Fresh L-Eggs", 290.00, true, false));
        allProducts.add(new Product("EGG002", "Magnolia Brown Eggs", 130.00, true, false));
        allProducts.add(new Product("EGG003", "Salted Eggs (6pcs)", 85.00, true, false));

        // 3. FROZEN (FRZ)
        allProducts.add(new Product("FRZ001", "Tender Juicy Hotdog", 220.00, true, false));
        allProducts.add(new Product("FRZ002", "Pampanga's Best Tocino", 150.00, true, false));
        allProducts.add(new Product("FRZ003", "CDO Young Pork Tocino", 75.00, true, false));

        // 4. MILK (MLK)
        allProducts.add(new Product("MLK001", "Bear Brand Fresh Milk", 115.00, true, true));
        allProducts.add(new Product("MLK002", "Selecta Skim Milk", 35.00, true, true));
        allProducts.add(new Product("MLK003", "Magnolia Fresh Milk", 98.00, true, true));

        // 5. CHEESE (CHS)
        allProducts.add(new Product("CHS001", "Eden Cheese Original", 58.00, true, false));
        allProducts.add(new Product("CHS002", "Magnolia Cheezee", 65.00, true, false));
        allProducts.add(new Product("CHS003", "Ques-O Cheddar Block", 48.00, true, false));

        // 6. CLEANING (CLE)
        allProducts.add(new Product("CLE001", "Joy Dishwashing Liq", 60.00, false, false));
        allProducts.add(new Product("CLE002", "Zonrox Bleach (1L)", 40.00, false, false));
        allProducts.add(new Product("CLE003", "Ariel Powder (600g)", 135.00, false, false));

        // 7. HOME ESSENTIALS (HOM)
        allProducts.add(new Product("HOM001", "Sanicare Kitchen Towel", 95.00, false, false));
        allProducts.add(new Product("HOM002", "Reynolds Aluminum Foil", 110.00, false, false));
        allProducts.add(new Product("HOM003", "Scotch Brite Sponge", 35.00, false, false));

        // 8. HAIR CARE (HAR)
        allProducts.add(new Product("HAR001", "Palmolive Shampoo", 95.00, false, false));
        allProducts.add(new Product("HAR002", "Cream Silk Conditioner", 110.00, false, false));
        allProducts.add(new Product("HAR003", "Gatsby Styling Wax", 130.00, false, false));

        // 9. BODY CARE (BOD)
        allProducts.add(new Product("BOD001", "Safeguard Bar Soap", 55.00, false, false));
        allProducts.add(new Product("BOD002", "Johnson's Baby Powder", 70.00, false, false));
        allProducts.add(new Product("BOD003", "Green Cross Alcohol", 80.00, false, false));

        // 10. DENTAL CARE (DEN)
        allProducts.add(new Product("DEN001", "Colgate Toothpaste", 105.00, false, false));
        allProducts.add(new Product("DEN002", "Oral-B Toothbrush", 90.00, false, false));
        allProducts.add(new Product("DEN003", "Listerine Mouthwash", 140.00, false, false));

        // 11. VEGETABLES (VEG)
        allProducts.add(new Product("VEG001", "Broccoli Heads", 140.00, true, false));
        allProducts.add(new Product("VEG002", "Baguio Carrots", 95.00, true, false));
        allProducts.add(new Product("VEG003", "Ampalaya (1kg)", 110.00, true, false));

        // 12. CLOTHES (CLO)
        allProducts.add(new Product("CLO001", "Cotton T-Shirt", 250.00, false, false));
        allProducts.add(new Product("CLO002", "Boxer Shorts (3pk)", 350.00, false, false));
        allProducts.add(new Product("CLO003", "Ankle Socks (3pk)", 150.00, false, false));

        // 13. STATIONERY (STN)
        allProducts.add(new Product("STN001", "Panda Ballpens (12)", 85.00, false, false));
        allProducts.add(new Product("STN002", "Cattleya Notebook", 35.00, false, false));
        allProducts.add(new Product("STN003", "Yellow Pad Ream", 120.00, false, false));

        // 14. PET FOOD (PET)
        allProducts.add(new Product("PET001", "Pedigree Dog Food", 140.00, true, false));
        allProducts.add(new Product("PET002", "Whiskas Cat Food", 35.00, true, false));
        allProducts.add(new Product("PET003", "Purina One Dry Food", 380.00, true, false));

        // ==========================================
        //       GROUND FLOOR PRODUCTS (RESTORED)
        // ==========================================
        // System requires 3 variants per type to display properly.

        // FRUITS (FRU)
        allProducts.add(new Product("FRU001", "Davao Pomelos", 120.00, true, false));
        allProducts.add(new Product("FRU002", "Phil. Mangoes", 180.00, true, false));
        allProducts.add(new Product("FRU003", "Apples", 60.00, true, false));
        // CHICKEN (CHK)
        allProducts.add(new Product("CHK001", "Chicken Thigh", 200.00, true, false));
        allProducts.add(new Product("CHK002", "Ground Chicken", 120.00, true, false));
        allProducts.add(new Product("CHK003", "Drumsticks", 185.00, true, false));
        // BEEF (BEF)
        allProducts.add(new Product("BEF001", "Rib-eye Steak", 450.00, true, false));
        allProducts.add(new Product("BEF002", "Ground Beef", 305.00, true, false));
        allProducts.add(new Product("BEF003", "Beef Shank", 310.00, true, false));
        // SEAFOOD (SEA)
        allProducts.add(new Product("SEA001", "Tilapia Fillet", 210.00, true, false));
        allProducts.add(new Product("SEA002", "Shrimp", 350.00, true, false));
        allProducts.add(new Product("SEA003", "Squid Rings", 175.00, true, false));
        // CEREAL (CER)
        allProducts.add(new Product("CER001", "Koko Krunch", 120.00, true, false));
        allProducts.add(new Product("CER002", "Quaker Oats", 95.00, true, false));
        allProducts.add(new Product("CER003", "Honey Bunches", 145.00, true, false));
        // NOODLES (NDL)
        allProducts.add(new Product("NDL001", "Pancit Canton", 25.00, true, false));
        allProducts.add(new Product("NDL002", "Miswa", 40.00, true, false));
        allProducts.add(new Product("NDL003", "Egg Noodles", 65.00, true, false));
        // SNACKS (SNK)
        allProducts.add(new Product("SNK001", "Lay's Chips", 55.00, true, false));
        allProducts.add(new Product("SNK002", "Choco Cookies", 110.00, true, false));
        allProducts.add(new Product("SNK003", "Crackers", 45.00, true, false));
        // CANNED (CAN)
        allProducts.add(new Product("CAN001", "Century Tuna", 49.00, true, false));
        allProducts.add(new Product("CAN002", "Condensed Soup", 65.00, true, false));
        allProducts.add(new Product("CAN003", "Sardines", 35.00, true, false));
        // CONDIMENTS (CON)
        allProducts.add(new Product("CON001", "Soy Sauce", 21.00, true, false));
        allProducts.add(new Product("CON002", "Banana Ketchup", 60.00, true, false));
        allProducts.add(new Product("CON003", "Vinegar", 85.00, true, false));
        // DRINKS/SOFT (SFT)
        allProducts.add(new Product("SFT001", "Distilled Water", 30.00, true, true));
        allProducts.add(new Product("SFT002", "Coke 1.5L", 65.00, true, true));
        allProducts.add(new Product("SFT003", "Sprite", 40.00, true, true));
        // JUICE (JUC)
        allProducts.add(new Product("JUC001", "C2 Green Tea", 85.00, true, true));
        allProducts.add(new Product("JUC002", "Orange Juice", 70.00, true, true));
        allProducts.add(new Product("JUC003", "Pineapple Juice", 90.00, true, true));
        // ALCOHOL (ALC)
        allProducts.add(new Product("ALC001", "Pale Pilsen", 60.00, true, true));
        allProducts.add(new Product("ALC002", "Tanduay Rhum", 350.00, true, true));
        allProducts.add(new Product("ALC003", "Emperador", 500.00, true, true));
    }

    private void initializeLayouts() {
        // --- GROUND FLOOR LAYOUT (Unchanged) ---
        String[] gfLayout = {
                "w w w w w w w w w w w w w w w w w w w w w w",
                "w r r r r r r b r r r r r r b r r r r r r w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w st b b b b b b p b b b b p b b b b b b st w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w b b b b b b b b b w w b b b b b b b b b w",
                "w w c w c w c w c b w w b c w c w c w c w w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w bs b b b b b b b b b b b b b b b b b b cs w",
                "w w w w w w w w w w ex en w w w w w w w w w w"
        };

        // --- SECOND FLOOR LAYOUT (Unchanged) ---
        String[] secondFloorLayout = {
                "w w w w w w w w w w w w w w w w w w w w w w",
                "w bs b f f f f b b f f f f b b f f f f b cs w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b sh sh b b sh sh b b t t b b sh sh b b sh sh b w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w st b b b b b b b b b b b b b b b b b b st w",
                "w b b b w w b b b b w w b b b b w w b b b w",
                "w b b b w w b b b b w w b b b b w w b b b w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w b b b b b b b b b b b b b b b b b b b b w",
                "w p b t t t t t b t t t t b t t t t t b p w",
                "w w w w w w w w w w w w w w w w w w w w w w"
        };

        parseMap(gfLayout, 0);
        parseMap(secondFloorLayout, 1);
        stockDisplays();
    }

    private void parseMap(String[] layout, int floorIndex) {
        Amenity[][] targetGrid = floors.get(floorIndex);
        String floorPrefix = (floorIndex == 0) ? GF_NAME : SECOND_F_NAME;

        for (int r = 0; r < MAP_SIZE; r++) {
            String[] row = layout[r].split(" ");
            for (int c = 0; c < MAP_SIZE; c++) {
                Point pos = new Point(c, r);
                String type = row[c];
                Amenity amenity = null;
                String addr = floorPrefix + ", R" + r + "C" + c;

                switch (type) {
                    case "w": amenity = new Wall(pos); break;
                    case "r": amenity = new ChilledCounter(pos, addr); allDisplays.add((Display)amenity); break;
                    case "sh": amenity = new Shelf(pos, addr); allDisplays.add((Display)amenity); break;
                    case "t": amenity = new Table(pos, addr); allDisplays.add((Display)amenity); break;
                    case "f": amenity = new Refrigerator(pos, addr); allDisplays.add((Display)amenity); break;
                    case "p": amenity = new ProductSearch(pos); break;
                    case "c": amenity = new CheckoutCounter(pos); break;
                    case "bs": amenity = new CartStation(pos); break;
                    case "cs": amenity = new BasketStation(pos); break;
                    case "ex": amenity = new Exit(pos); break;
                    case "en": amenity = new Entrance(pos); break;
                    case "st": amenity = new Stairs(pos); break;
                    case "b": break;
                }

                if (amenity == null && (r == 0 || r == MAP_SIZE - 1 || c == 0 || c == MAP_SIZE - 1)) {
                    amenity = new Wall(pos);
                }
                targetGrid[r][c] = amenity;
            }
        }
    }

    private void stockDisplays() {
        // Track cycling indices for all categories
        int brd=0, egg=0, frz=0, mlk=0, chs=0;
        int cle=0, hom=0, har=0, bod=0, den=0;
        int veg=0, clo=0, stn=0, pet=0;
        // GF Indices
        int chk=0, bef=0, sea=0, fru=0, can=0, sft=0;
        int cer=0, ndl=0, snk=0, con=0, juc=0, alc=0;

        for (Display display : allDisplays) {
            String prefix = getPrefixForDisplay(display);
            if (prefix.isEmpty()) continue;

            List<Product> source = getProductsByPrefix(prefix);
            if (source.size() < 3) continue;

            int variantIndex = 0;
            switch (prefix) {
                // NEW 2F MAPPINGS
                case "BRD": variantIndex = (brd++) % 3; break;
                case "EGG": variantIndex = (egg++) % 3; break;
                case "FRZ": variantIndex = (frz++) % 3; break;
                case "MLK": variantIndex = (mlk++) % 3; break;
                case "CHS": variantIndex = (chs++) % 3; break;
                case "CLE": variantIndex = (cle++) % 3; break;
                case "HOM": variantIndex = (hom++) % 3; break;
                case "HAR": variantIndex = (har++) % 3; break;
                case "BOD": variantIndex = (bod++) % 3; break;
                case "DEN": variantIndex = (den++) % 3; break;
                case "VEG": variantIndex = (veg++) % 3; break;
                case "CLO": variantIndex = (clo++) % 3; break;
                case "STN": variantIndex = (stn++) % 3; break;
                case "PET": variantIndex = (pet++) % 3; break;

                // GF ORIGINAL MAPPINGS
                case "CHK": variantIndex = (chk++) % 3; break;
                case "BEF": variantIndex = (bef++) % 3; break;
                case "SEA": variantIndex = (sea++) % 3; break;
                case "FRU": variantIndex = (fru++) % 3; break;
                case "CAN": variantIndex = (can++) % 3; break;
                case "SFT": variantIndex = (sft++) % 3; break;
                case "CER": variantIndex = (cer++) % 3; break;
                case "NDL": variantIndex = (ndl++) % 3; break;
                case "SNK": variantIndex = (snk++) % 3; break;
                case "CON": variantIndex = (con++) % 3; break;
                case "JUC": variantIndex = (juc++) % 3; break;
                case "ALC": variantIndex = (alc++) % 3; break;
            }

            Product p = source.get(variantIndex);
            while (!display.isFull()) display.returnProduct(p);
        }
    }

    public String getPrefixForDisplay(Display d) {
        int r = d.getPosition().getY();
        int c = d.getPosition().getX();
        boolean isGF = d.getAddress().startsWith("GF");
        boolean is2F = d.getAddress().startsWith("2F");

        // --- GROUND FLOOR LOGIC (Restored Full Logic) ---
        if (isGF) {
            if (d instanceof ChilledCounter) {
                if (c <= 7) return "CHK";
                if (c <= 14) return "BEF";
                return "SEA";
            }
            if (d instanceof Table) return "FRU";

            if (d instanceof Shelf) {
                // Top Half (Rows ~4-7)
                if (r <= 7) {
                    if (c <= 3) return "ALC";
                    if (c <= 8) return "SFT";
                    if (c <= 15) return "CER";
                    return "CAN";
                }
                // Bottom Half (Rows ~10-13)
                else {
                    if (c <= 3) return "CON";
                    if (c <= 8) return "JUC";
                    if (c <= 15) return "NDL";
                    return "SNK";
                }
            }
        }

        // --- SECOND FLOOR LOGIC (Clusters A-P) ---
        if (is2F) {
            // ROW 1: Refrigerators (Clusters A, B, C)
            if (r == 1) {
                if (c <= 6) return "MLK";
                if (c <= 12) return "FRZ";
                return "CHS";
            }

            // ROWS 4-7: Shelves & Tables (D, E, F, G, H)
            if (r >= 4 && r <= 7) {
                if (c <= 3) return "PET";
                if (c <= 7) return "CLO";
                if (c <= 11) return "VEG";
                if (c <= 15) return "CLE";
                return "HOM";
            }

            // ROWS 10-13: Shelves & Tables (I, J, K, L, M)
            if (r >= 10 && r <= 13) {
                if (c <= 3) return "STN";
                if (c <= 7) return "DEN";
                if (c <= 11) return "VEG";
                if (c <= 15) return "HAR";
                return "BOD";
            }

            // ROW 20: Tables (N, O, P)
            if (r == 20) {
                if (c <= 7) return "BRD";
                if (c <= 13) return "EGG";
                return "BRD";
            }
        }
        return "";
    }

    private List<Product> getProductsByPrefix(String prefix) {
        List<Product> filtered = new ArrayList<>();
        for (Product p : allProducts) {
            if (p.getSerialPrefix().equals(prefix)) filtered.add(p);
        }
        return filtered;
    }

    public Amenity getAmenityAt(int x, int y, int floor) {
        if (floor < 0 || floor >= floors.size()) return null;
        if (x < 0 || x >= MAP_SIZE || y < 0 || y >= MAP_SIZE) return null;
        return floors.get(floor)[y][x];
    }

    public Amenity getAmenityInVision(Point pos, Direction dir, int floor) {
        Point vision = pos.getNextPosition(dir);
        return getAmenityAt(vision.getX(), vision.getY(), floor);
    }

    public List<Display> getAllDisplays() { return allDisplays; }
}