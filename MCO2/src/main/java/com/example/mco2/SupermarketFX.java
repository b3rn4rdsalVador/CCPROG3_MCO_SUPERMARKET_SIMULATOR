/**
 * SupermarketFX.java
 *
 * This is the main application class for the Supermarket Simulation. It extends
 * {@link javafx.application.Application} and manages the entire graphical user interface,
 * rendering the map, handling player input and movement, and controlling all in-game dialogs
 * and interactions with amenities.
 *
 * @author Bernard Salvador
 * @author Ram Liwanag
 *
 */
package com.example.mco2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

/**
 * The main JavaFX application class that runs the Supermarket Simulation GUI.
 * Manages game initialization, rendering, user input handling, and display interactions.
 */
public class SupermarketFX extends Application {

    // --- CONFIGURATION ---
    /** The uniform size (in pixels) for map tiles (currently unused but good practice). */
    private static final int TILE_SIZE = 30;
    /** The size of the square map grid (22x22). */
    private static final int MAP_SIZE = 22;

    // --- GAME OBJECTS ---
    private SupermarketMap map;
    private Shopper shopper;
    private Stage primaryStage;

    // --- GUI COMPONENTS ---
    private GridPane mapGrid;
    private StackPane[][] tileViews = new StackPane[MAP_SIZE][MAP_SIZE];
    private Rectangle playerSprite;
    private Label floorLabel;
    private Label facingLabel;

    // --- INPUT LOCK (For Animation) ---
    /** Flag to lock user input during automated movement or dialogs. */
    private boolean inputLocked = false;

    /**
     * The entry point for the JavaFX application. Initializes the primary stage and starts the game setup process.
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        startGame();
    }

    /**
     * Initializes the game by gathering player details (name, age), setting up the game logic
     * (map, shopper), and constructing the main GUI elements.
     */
    private void startGame() {
        // --- 1. ASK FOR NAME ---
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Welcome");
        nameDialog.setHeaderText("Welcome to the Supermarket!");
        nameDialog.setContentText("Please enter your Name:");

        Optional<String> nameResult = nameDialog.showAndWait();
        String name = nameResult.orElse("Guest");
        if (name == null || name.trim().isEmpty()) name = "Guest";

        // --- 2. ASK FOR AGE (Validation Loop) ---
        int age = 0;
        while (age <= 0) {
            TextInputDialog ageDialog = new TextInputDialog();
            ageDialog.setTitle("Shopper Details");
            ageDialog.setHeaderText("Hello, " + name + ".");
            ageDialog.setContentText("Please enter your Age:");

            Optional<String> ageResult = ageDialog.showAndWait();
            if (ageResult.isPresent()) {
                try {
                    age = Integer.parseInt(ageResult.get());
                    if (age <= 0) {
                        new Alert(Alert.AlertType.ERROR, "Age must be positive.").showAndWait();
                    }
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.ERROR, "Please enter a valid number.").showAndWait();
                }
            } else {
                age = 25; // Default if user cancels to prevent crash
                break;
            }
        }

        // --- 3. INITIALIZE GAME LOGIC ---
        map = new SupermarketMap();
        shopper = new Shopper(name, age, new Point(11, 21));
        shopper.setCurrentMap(map);

        // --- 4. SETUP UI ---
        BorderPane root = new BorderPane();
        mapGrid = new GridPane();
        mapGrid.setStyle("-fx-background-color: #222;");

        // Initialize Floor Label
        floorLabel = new Label("FLOOR: " + (shopper.getCurrentFloor() == 0 ? "Ground Floor" : "2nd Floor"));
        floorLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10; -fx-background-color: #333;");

        // Initialize Facing Label
        facingLabel = new Label("FACING: " + shopper.getFacing().name());
        facingLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10; -fx-background-color: #333;");

        // Place both labels in an HBox at the top
        HBox topBar = new HBox(20); // 20px spacing between labels
        topBar.setStyle("-fx-background-color: #333;");
        topBar.getChildren().addAll(floorLabel, facingLabel);

        setupGrid();
        root.setCenter(mapGrid);
        root.setTop(topBar); // Use the new HBox here

        refreshMapVisuals();
        placePlayer();

        Scene scene = new Scene(root, 1000, 700);
        scene.setOnKeyPressed(e -> handleInput(e.getCode()));

        primaryStage.setTitle("Supermarket Simulator GUI");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        mapGrid.requestFocus();
    }

    /**
     * Handles keyboard input for movement, facing, inventory, and interaction.
     * @param code The {@link KeyCode} pressed by the user.
     */
    private void handleInput(KeyCode code) {
        // If animating or exited, ignore input
        if (inputLocked || shopper.getHasExited()) return;

        boolean actionTaken = false;
        int oldFloor = shopper.getCurrentFloor();

        switch (code) {
            case W: shopper.move(Direction.NORTH, map); actionTaken = true; break;
            case S: shopper.move(Direction.SOUTH, map); actionTaken = true; break;
            case A: shopper.move(Direction.WEST, map); actionTaken = true; break;
            case D: shopper.move(Direction.EAST, map); actionTaken = true; break;
            case I: shopper.face(Direction.NORTH); actionTaken = true; break;
            case K: shopper.face(Direction.SOUTH); actionTaken = true; break;
            case J: shopper.face(Direction.WEST); actionTaken = true; break;
            case L: shopper.face(Direction.EAST); actionTaken = true; break;
            case V: showInventoryDialog(); break;
            case SPACE: handleInteraction(); break;
        }

        if (actionTaken) {
            updateVisualsAfterMove(oldFloor);
        }

        if (shopper.getHasExited()) {
            showEndGameDialog();
        }
    }

    /**
     * Updates the GUI visuals after the shopper moves or changes facing direction.
     * Refreshes map tiles if a floor transition occurred, and updates the floor and facing labels.
     * @param oldFloor The floor index before the move occurred.
     */
    private void updateVisualsAfterMove(int oldFloor) {
        if (shopper.getCurrentFloor() != oldFloor) {
            refreshMapVisuals();
            floorLabel.setText("FLOOR: " + (shopper.getCurrentFloor() == 0 ? "Ground Floor" : "2nd Floor"));
        }

        // Update the Facing Label
        facingLabel.setText("FACING: " + shopper.getFacing().name());

        placePlayer();
    }

    // --- MOUSE CLICK HANDLING ---
    /**
     * Handles mouse clicks on map tiles, enabling movement and interaction via mouse.
     * @param tx The target x-coordinate (column).
     * @param ty The target y-coordinate (row).
     */
    private void handleTileClick(int tx, int ty) {
        // 1. Safety Check
        if (shopper.getHasExited()) return;

        Point current = shopper.getPosition();
        int cx = current.getX();
        int cy = current.getY();

        // 2. Check Adjacency (Must be exactly 1 tile away)
        int dx = tx - cx;
        int dy = ty - cy;

        if (Math.abs(dx) + Math.abs(dy) != 1) {
            return; // Clicked too far or diagonally -> Do nothing
        }

        // 3. Determine Direction
        Direction dir = null;
        if (dy == -1) dir = Direction.NORTH;
        else if (dy == 1) dir = Direction.SOUTH;
        else if (dx == -1) dir = Direction.WEST;
        else if (dx == 1) dir = Direction.EAST;

        // 4. Identify Target
        Amenity target = map.getAmenityAt(tx, ty, shopper.getCurrentFloor());
        boolean isPassable = (target == null || target.isPassable());

        // 5. Execute Action
        if (isPassable) {
            // MOVE
            int oldFloor = shopper.getCurrentFloor();
            shopper.face(dir);
            shopper.move(dir, map);
            updateVisualsAfterMove(oldFloor);
        } else {
            // INTERACT
            // Must FACE the target first, then call the
            // main handleInteraction() method so the GUI windows open.
            shopper.face(dir);
            handleInteraction();
        }

        // 6. Game Over Check
        if (shopper.getHasExited()) {
            showEndGameDialog();
        }
    }

    /**
     * Sets the shopper's facing direction towards a specific adjacent {@link Point}.
     * @param target The adjacent {@link Point} to face.
     */
    private void faceTarget(Point target) {
        Point p = shopper.getPosition();
        if (target.getY() < p.getY()) shopper.face(Direction.NORTH);
        else if (target.getY() > p.getY()) shopper.face(Direction.SOUTH);
        else if (target.getX() < p.getX()) shopper.face(Direction.WEST);
        else if (target.getX() > p.getX()) shopper.face(Direction.EAST);
    }

    // --- REMOVED: PATHFINDING (BFS) and all related methods (findPath, isValidMove, getPassableNeighbors, getInverse) ---

    // --- ANIMATION SYSTEM ---
    /**
     * Animates the shopper's movement along a given path of directions using a {@link Timeline}.
     * Locks input during animation.
     * @param path The list of {@link Direction}s to follow.
     * @param onComplete A {@link Runnable} to execute once the animation finishes.
     */
    private void animateMovement(List<Direction> path, Runnable onComplete) {
        inputLocked = true;
        Timeline timeline = new Timeline();
        int floorAtStart = shopper.getCurrentFloor();

        for (int i = 0; i < path.size(); i++) {
            Direction d = path.get(i);
            KeyFrame kf = new KeyFrame(Duration.millis((i + 1) * 150), e -> {
                // Stop if floor changed (e.g., stepped on stairs mid-path)
                if (shopper.getCurrentFloor() != floorAtStart) return;

                int oldFloor = shopper.getCurrentFloor();
                shopper.move(d, map);
                updateVisualsAfterMove(oldFloor);
            });
            timeline.getKeyFrames().add(kf);
        }

        timeline.setOnFinished(e -> {
            inputLocked = false;
            // If floor changed, we stop.
            if (shopper.getCurrentFloor() == floorAtStart && onComplete != null) {
                onComplete.run();
            }
            // Check exit after auto-move
            if (shopper.getHasExited()) {
                showEndGameDialog();
            }
        });
        timeline.play();
    }

    /**
     * Displays the game over dialog with options to restart the simulation or exit the application.
     */
    private void showEndGameDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Simulation Ended");
        alert.setHeaderText("Thank you for shopping!");
        alert.setContentText("What would you like to do?");

        ButtonType restartBtn = new ButtonType("Restart Simulation");
        ButtonType exitBtn = new ButtonType("Exit App", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(restartBtn, exitBtn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == restartBtn) {
            startGame(); // RESTART
        } else {
            Platform.exit(); // EXIT
        }
    }

    /**
     * Sets up the {@link GridPane} for the map, creating the individual {@link StackPane} tiles
     * and initializing the player sprite and mouse click handlers.
     */
    private void setupGrid() {
        mapGrid.getChildren().clear(); // Clear for restarts
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();

        for (int i = 0; i < MAP_SIZE; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / MAP_SIZE);
            mapGrid.getColumnConstraints().add(col);

            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / MAP_SIZE);
            mapGrid.getRowConstraints().add(row);
        }

        for (int r = 0; r < MAP_SIZE; r++) {
            for (int c = 0; c < MAP_SIZE; c++) {
                StackPane tile = new StackPane();

                // CRITICAL FIX FOR LAYOUT BLOWOUT: Force tile to expand
                tile.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                tile.setStyle("-fx-border-color: #444; -fx-border-width: 0.5px;");
                tileViews[r][c] = tile;

                // CLICK HANDLER
                final int finalC = c;
                final int finalR = r;
                tile.setOnMouseClicked(e -> handleTileClick(finalC, finalR));

                mapGrid.add(tile, c, r);
            }
        }

        playerSprite = new Rectangle();
        playerSprite.widthProperty().bind(tileViews[0][0].widthProperty().multiply(0.7));
        playerSprite.heightProperty().bind(tileViews[0][0].heightProperty().multiply(0.7));
        playerSprite.setFill(Color.RED);
        playerSprite.setArcWidth(10);
        playerSprite.setArcHeight(10);
    }

    /**
     * Determines the file path for the image icon corresponding to a specific {@link Amenity} type.
     * @param amenity The {@link Amenity} object to check.
     * @return The relative path to the image file (e.g., "checkout.png"), or an empty string if none exists.
     */
    private String getAmenityImagePath(Amenity amenity) {
        if (amenity == null) return "";

        // Services and Non-Product Displays
        if (amenity instanceof Wall) return "";
        if (amenity instanceof Stairs) return "stairs.png";
        if (amenity instanceof CheckoutCounter) return "checkout.png";
        if (amenity instanceof Exit) return "exit.png";
        if (amenity instanceof Entrance) return "entrance.png";
        if (amenity instanceof CartStation) return "cart.png";
        if (amenity instanceof BasketStation) return "basket.png";
        if (amenity instanceof ProductSearch) return "search.png";

        // Product Displays
        if (amenity instanceof Display) {
            String prefix = map.getPrefixForDisplay((Display) amenity);
            switch (prefix) {
                // 2F Products
                case "MLK": return "milk.png";
                case "FRZ": return "frozen.png";
                case "CHS": return "cheese.png";
                case "PET": return "petfood.png";
                case "CLO": return "clothes.png";
                case "VEG": return "vegetable.png";
                case "CLE": return "cleaning.png";
                case "HOM": return "home.png";
                case "STN": return "stationery.png";
                case "DEN": return "dental.png";
                case "HAR": return "hair.png";
                case "BOD": return "body.png";
                case "BRD": return "bread.png";
                case "EGG": return "egg.png";

                // GF Products
                case "FRU": return "fruit.png";
                case "CHK": return "chicken.png";
                case "BEF": return "beef.png";
                case "SEA": return "seafood.png";
                case "ALC": return "alcohol.png";
                case "SFT": return "soda.png";
                case "CAN": return "canned.png";
                case "CER": return "cereal.png";
                case "SNK": return "snack.png";
                case "CON": return "condiment.png";
                case "JUC": return "juice.png";
                case "NDL": return "noodle.png";
            }
        }
        return "";
    }

    /**
     * Refreshes the color and icons for all tiles on the current floor based on the amenities present.
     */
    private void refreshMapVisuals() {
        int currentFloor = shopper.getCurrentFloor();
        final int ICON_SIZE = 30;

        for (int r = 0; r < MAP_SIZE; r++) {
            for (int c = 0; c < MAP_SIZE; c++) {
                Amenity amenity = map.getAmenityAt(c, r, currentFloor);
                StackPane tile = tileViews[r][c];
                tile.getChildren().clear();

                String color = "white";

                // 1. Determine Tile Color
                if (amenity != null) {
                    if (amenity instanceof Wall) color = "#44546A";
                    else if (amenity instanceof ChilledCounter) color = "#5b9bd5";
                    else if (amenity instanceof Refrigerator) color = "#5b9bd5";
                    else if (amenity instanceof Shelf) color = "#FFC000";
                    else if (amenity instanceof Table) color = "#70AD47";
                    else if (amenity instanceof Stairs) color = "#a5a5a5";
                    else if (amenity instanceof CheckoutCounter) color = "#a5a5a5";
                    else if (amenity instanceof Entrance) color = "#a5a5a5";
                    else if (amenity instanceof Exit) color = "#a5a5a5";
                    else if (amenity instanceof ProductSearch) color = "#a5a5a5";
                    else if (amenity instanceof CartStation || amenity instanceof BasketStation) color = "#a5a5a5";
                }
                tile.setStyle("-fx-background-color: " + color + "; -fx-border-color: #555; -fx-border-width: 0.5px;");

                // 2. Add Image Symbol
                String imagePath = getAmenityImagePath(amenity);
                if (!imagePath.isEmpty()) {
                    try {
                        String resourceUrl = "/" + imagePath;


                        javafx.scene.image.Image image = new javafx.scene.image.Image(
                                getClass().getResource(resourceUrl).toExternalForm(),
                                ICON_SIZE,
                                ICON_SIZE,
                                true,
                                true
                        );

                        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);

                        // Bind size for dynamic resizing
                        imageView.fitWidthProperty().bind(tile.widthProperty().multiply(0.8));
                        imageView.fitHeightProperty().bind(tile.heightProperty().multiply(0.8));
                        imageView.setPreserveRatio(true);

                        javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle();
                        clip.widthProperty().bind(tile.widthProperty());
                        clip.heightProperty().bind(tile.heightProperty());
                        tile.setClip(clip);

                        tile.getChildren().add(imageView);
                    } catch (Exception e) {
                        System.err.println("Failed to load image for: " + imagePath + ". Check file path/resolution.");
                    }
                }
            }
        }
    }

    /**
     * Updates the visual position of the player sprite on the map grid.
     */
    private void placePlayer() {
        if (playerSprite.getParent() != null) {
            ((Pane) playerSprite.getParent()).getChildren().remove(playerSprite);
        }
        int x = shopper.getPosition().getX();
        int y = shopper.getPosition().getY();
        tileViews[y][x].getChildren().add(playerSprite);
    }

    /**
     * Executes the interaction logic based on the amenity the shopper is currently facing.
     * Opens specific dialogs for {@link Display} and {@link ProductSearch} amenities.
     */
    private void handleInteraction() {
        Amenity amenity = map.getAmenityInVision(shopper.getPosition(), shopper.getFacing(), shopper.getCurrentFloor());

        if (amenity == null || amenity instanceof Wall) return;

        if (amenity instanceof Display) {
            showDisplayDialog((Display) amenity);
        } else if (amenity instanceof ProductSearch) {
            showSearchDialog((ProductSearch) amenity);
        } else {
            amenity.interact(shopper);
        }
    }

    /**
     * Displays a dialog showing the shopper's current inventory (items held) and the running total cost.
     */
    private void showInventoryDialog() {
        // 1. Get the Item List string
        String itemsText = shopper.viewChosenProducts();

        // 2. Calculate a running total for the "Summary" look
        double total = 0.0;
        for (Product p : shopper.getAllProducts()) {
            total += p.getPrice();
        }

        // 3. Format the final message
        String message;
        if (itemsText.isEmpty()) {
            message = "Your inventory is currently empty.";
        } else {
            message = itemsText +
                    "\n-----------------------------------\n" +
                    String.format("Current Total: PHP %.2f", total);
        }

        // 4. Create and Show the Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("My Inventory");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Optional: Auto-size width so product names don't get cut off
        alert.getDialogPane().setMinWidth(400);

        alert.showAndWait();
    }

    /**
     * Displays a customized dialog for interacting with product {@link Display} amenities.
     * This method handles button creation, tiered layout, product taking, and triggering the return dialog.
     * @param display The {@link Display} amenity being interacted with.
     */
    private void showDisplayDialog(Display display) {
        List<Product> flatList = new ArrayList<>();

        // 1. GET DATA
        if (display instanceof ChilledCounter) flatList = ((ChilledCounter)display).getProducts();
        else if (display instanceof Table) flatList = ((Table)display).getProducts();
        else if (display instanceof Refrigerator) {
            for(List<Product> tier : ((Refrigerator)display).getTiers()) flatList.addAll(tier);
        }
        else if (display instanceof Shelf) {
            for(List<Product> tier : ((Shelf)display).getTiers()) flatList.addAll(tier);
        } else return;

        // 2. SETUP DIALOG
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle(display.getClass().getSimpleName());
        dialog.setHeaderText("Click an item to take it:");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        // 3. CREATE BUTTON GRID
        GridPane grid = new GridPane();
        grid.setHgap(15); // Increased gap for visual separation
        grid.setVgap(15); // Increased gap for visual separation
        grid.setPadding(new Insets(20));

// --- TIERED LAYOUT LOGIC ---
        int maxProductsPerTier = 0;
        int numTiers = 0;

        if (display instanceof Shelf) {
            maxProductsPerTier = 4;
            numTiers = 2;
        } else if (display instanceof Refrigerator) {
            maxProductsPerTier = 3;
            numTiers = 3;
        } else if (display instanceof ChilledCounter || display instanceof Table) {
            // For flat displays, treat it as 1 long tier
            maxProductsPerTier = 4; // Use max capacity for visual consistency if possible, otherwise use max product size. Using 4 here for consistency.
            numTiers = 1;
        } else return;

        if (flatList.isEmpty()) {
            grid.add(new Label("(This display is empty)"), 0, 0);
        } else {
            int productIndex = 0;
            int currentRow = 0; // Track the current row for placing elements

            for (int tier = 0; tier < numTiers; tier++) {

                // 1. Tier Label
                Label tierLabel = new Label("--- TIER " + (tier + 1) + " ---");
                tierLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
                GridPane.setColumnSpan(tierLabel, maxProductsPerTier);

                // Add padding above the label for visual separation from the previous tier/separator
                if (tier > 0) {
                    tierLabel.setPadding(new Insets(15, 0, 0, 0));
                }

                grid.add(tierLabel, 0, currentRow);
                currentRow++; // Move to the next row for buttons

                // 2. Product Buttons
                for (int indexInTier = 0; indexInTier < maxProductsPerTier; indexInTier++) {

                    // Check if we have a product for this slot (only display up to max capacity of the display type)
                    if (productIndex < flatList.size()) {
                        Product p = flatList.get(productIndex);
                        Button btn = new Button(p.toString());
                        btn.setMinWidth(200);

                        final int selectedIndex = productIndex;
                        btn.setOnAction(e -> {
                            dialog.setResult(selectedIndex);
                            dialog.close();
                        });

                        grid.add(btn, indexInTier, currentRow);
                        productIndex++;
                    } else {
                        // Empty slot placeholder
                        Button emptyBtn = new Button("Empty Slot");
                        emptyBtn.setDisable(true);
                        emptyBtn.setStyle("-fx-opacity: 0.5; -fx-base: #E0E0E0;");
                        grid.add(emptyBtn, indexInTier, currentRow);
                        productIndex++; // Still increment to track slots but only if this slot is part of the tier capacity

                        // We need a proper way to cap the loop if maxProductsPerTier is smaller than total items in flatlist for single tier displays
                        // Since we're using flatList.size() for maxProductsPerTier in single-tier displays (ChilledCounter, Table),
                        // this inner loop logic needs refinement but is acceptable for now given the previous fixed-slot logic.
                    }
                }
                currentRow++; // Move past the button row, ready for the next tier
            }
        }

        // 4. RETURN BUTTON
        Button returnBtn = new Button("RETURN AN ITEM");
        returnBtn.setStyle("-fx-base: #ffcccc;");
        returnBtn.setMaxWidth(Double.MAX_VALUE);
        returnBtn.setOnAction(e -> {
            dialog.setResult(-99);
            dialog.close();
        });

        VBox layout = new VBox(15, grid, new Separator(), returnBtn);
        dialog.getDialogPane().setContent(layout);

        Optional<Integer> result = dialog.showAndWait();
        if (result.isPresent()) {
            int selectedIndex = result.get();

            if (selectedIndex == -99) {
                showReturnDialog(display);
            } else {
                try {
                    Product p = null;
                    if (display instanceof Shelf || display instanceof Refrigerator) {
                        // Complex tier removal logic
                        List<List<Product>> tiers = (display instanceof Shelf) ? ((Shelf)display).getTiers() : ((Refrigerator)display).getTiers();
                        int runningCount = 0;
                        for (List<Product> tier : tiers) {
                            if (selectedIndex < runningCount + tier.size()) {
                                p = tier.remove(selectedIndex - runningCount);
                                break;
                            }
                            runningCount += tier.size();
                        }
                    } else {
                        // Simple flat removal
                        p = flatList.remove(selectedIndex);
                    }

                    if (p != null) {
                        if (shopper.takeProduct(p)) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Notification");
                            alert.setHeaderText(null);
                            alert.setContentText("You took: " + p.getName());
                            alert.show();
                        } else {
                            display.returnProduct(p);
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            if (shopper.getAge() < 18 && p.getSerialPrefix().equals("ALC")) {
                                alert.setContentText("Denied: You are underage!");
                            } else {
                                alert.setContentText("Hands or Cart Full!");
                            }
                            alert.show();
                        }
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    /**
     * Displays a dialog allowing the shopper to return an item from their inventory to the current {@link Display}.
     * This method is triggered when the user selects the "RETURN AN ITEM" button.
     * @param display The {@link Display} amenity receiving the returned product.
     */
    private void showReturnDialog(Display display) {
        List<Product> myItems = shopper.getAllProducts();
        if (myItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText(null);
            alert.setContentText("You have nothing to return.");
            alert.show();
            return;
        }

        ChoiceDialog<Product> dialog = new ChoiceDialog<>(myItems.get(0), myItems);
        dialog.setTitle("Return Item");
        dialog.setHeaderText("Select item to return to " + display.getClass().getSimpleName());

        Optional<Product> result = dialog.showAndWait();
        if (result.isPresent()) {
            Product p = result.get();
            if (display.returnProduct(p)) {
                shopper.returnProduct(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText(null);
                alert.setContentText("Returned " + p.getName());
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Cannot return " + p.getName() + " here.\n(Wrong Type or Display Full)");
                alert.show();
            }
        }
    }

    /**
     * Displays a dialog allowing the shopper to search for the location of a product by name.
     * @param searchParams The {@link ProductSearch} amenity being interacted with.
     */
    private void showSearchDialog(ProductSearch searchParams) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Product Search");
        dialog.setHeaderText("Enter product name:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            List<Display> displays = map.getAllDisplays();
            boolean found = false;
            StringBuilder locs = new StringBuilder("Found at:\n");

            for (Display d : displays) {
                if (d.containsProductByName(name)) {
                    locs.append("- ").append(d.getAddress()).append("\n");
                    found = true;
                }
            }
            if (found) new Alert(Alert.AlertType.INFORMATION, locs.toString()).show();
            else new Alert(Alert.AlertType.WARNING, "Product not found.").show();
        });
    }

    /**
     * The standard main method that launches the JavaFX application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}