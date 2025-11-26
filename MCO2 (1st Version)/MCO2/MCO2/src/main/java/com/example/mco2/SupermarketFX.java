package com.example.mco2; 

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

/**
 * The main application driver and User Interface (UI) class for the Supermarket Simulator.
 * This class extends the JavaFX {@code Application} and manages the graphical display, 
 * user input, and visualization of the model's state.
 * NOTE: This is the **View/Controller** layer and is excluded from the Model-level UML Class Diagram.
 */
public class SupermarketFX extends Application {

    /** The fixed size of the grid tiles in pixels for visualization. */
    private static final int TILE_SIZE = 30;
    /** The map size (22x22), used for UI grid construction. */
    private static final int MAP_SIZE = 22;

    /** Reference to the core map model containing the grid and amenities. */
    private SupermarketMap map;
    /** Reference to the main actor model. */
    private Shopper shopper;

    /** The JavaFX GridPane used to render the map tiles. */
    private GridPane mapGrid;
    /** The text area for displaying game events and messages (unused in final code but defined). */
    private TextArea gameConsole;
    /** The visual nodes representing each tile on the map. */
    private StackPane[][] tileViews = new StackPane[MAP_SIZE][MAP_SIZE];
    /** The visual sprite representing the shopper. */
    private Rectangle playerSprite;
    /** Label for displaying status information (unused in final code but defined). */
    private Label statusLabel;

    /**
     * The main entry point for the JavaFX application lifecycle.
     * Initializes the shopper with user input (Name, Age), loads the map model, 
     * and sets up the graphical stage and input handlers.
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
       
        map = new SupermarketMap();

       
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Welcome");
        nameDialog.setHeaderText("Welcome to the Supermarket!");
        nameDialog.setContentText("Please enter your Name:");

       
        Optional<String> nameResult = nameDialog.showAndWait();
        String name = nameResult.orElse("Guest");
        if (name.trim().isEmpty()) name = "Guest";

        
        int age = 0;
        while (age <= 0) {
            TextInputDialog ageDialog = new TextInputDialog();
            ageDialog.setTitle("Shopper Details");
            ageDialog.setHeaderText("Hello, " + name + ".");
            ageDialog.setContentText("Please enter your Age (Required for discounts):");

            Optional<String> ageResult = ageDialog.showAndWait();

            if (ageResult.isPresent()) {
                try {
                    age = Integer.parseInt(ageResult.get());
                    if (age <= 0) {
                        Alert error = new Alert(Alert.AlertType.ERROR, "Age must be a positive number.");
                        error.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Please enter a valid number.");
                    error.showAndWait();
                }
            } else {
                
                age = 25;
            }
        }

      
        map = new SupermarketMap();
       
        shopper = new Shopper(name, age, new Point(11, 21));
        shopper.setCurrentMap(map);

        
        BorderPane root = new BorderPane();

       
        mapGrid = new GridPane();
        mapGrid.setStyle("-fx-background-color: #222;"); 
        setupGrid();
        root.setCenter(mapGrid);



        
        refreshMapVisuals();
        placePlayer();

       
        Scene scene = new Scene(root, 1000, 700); 
        scene.setOnKeyPressed(e -> handleInput(e.getCode()));

        stage.setTitle("Supermarket Simulator GUI");
        stage.setScene(scene);


        stage.setMaximized(true); 

        stage.show();
        mapGrid.requestFocus(); 
        System.out.println("Map focus requested."); 
    }

    /**
     * Handles key press events, translating them into model actions (move, face) 
     * or view actions (inventory, interaction).
     * The simulation ends if the shopper exits.
     * @param code The KeyCode representing the key that was pressed.
     */
    private void handleInput(KeyCode code) {
        System.out.println("Key Pressed: " + code);

        if (shopper.getHasExited()) return;

        boolean actionTaken = false;

        switch (code) {
            case W: shopper.move(Direction.NORTH, map); actionTaken = true; break;
            case S: shopper.move(Direction.SOUTH, map); actionTaken = true; break;
            case A: shopper.move(Direction.WEST, map); actionTaken = true; break;
            case D: shopper.move(Direction.EAST, map); actionTaken = true; break;
            case I: shopper.face(Direction.NORTH); actionTaken = true; break;
            case K: shopper.face(Direction.SOUTH); actionTaken = true; break;
            case J: shopper.face(Direction.WEST); actionTaken = true; break;
            case L: shopper.face(Direction.EAST); actionTaken = true; break;
            case V: System.out.println(shopper.viewChosenProducts()); break;
            case SPACE: handleInteraction(); break;
        }

        if (actionTaken) {
            placePlayer(); 
        }

        if (shopper.getHasExited()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Simulation Ended");
            alert.setContentText("Thank you for shopping!");
            alert.showAndWait();
            Platform.exit();
        }
    }

    /**
     * Sets up the column and row constraints for the map grid and initializes the tile views.
     */
    private void setupGrid() {
        
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
                
                tile.setStyle("-fx-border-color: #444; -fx-border-width: 0.5px;");
                tileViews[r][c] = tile;
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
     * Updates the visual appearance (color) of every map tile based on the Amenity 
     * type currently stored in the {@code SupermarketMap} grid.
     */
    private void refreshMapVisuals() {
        for (int r = 0; r < MAP_SIZE; r++) {
            for (int c = 0; c < MAP_SIZE; c++) {
                Amenity amenity = map.getAmenityAt(c, r);
                StackPane tile = tileViews[r][c];

                String color = "white"; 

                
                if (amenity != null) {
                    if (amenity instanceof Wall) color = "#44546A";
                    else if (amenity instanceof ChilledCounter) color = "#5b9bd5";
                    else if (amenity instanceof Shelf) color = "#FFC000";
                    else if (amenity instanceof Table) color = "#70AD47";
                    else if (amenity instanceof CheckoutCounter) color = "#a5a5a5";
                    else if (amenity instanceof Entrance) color = "#a5a5a5";
                    else if (amenity instanceof Exit) color = "#a5a5a5";
                    else if (amenity instanceof ProductSearch) color = "#a5a5a5";
                    else if (amenity instanceof CartStation || amenity instanceof BasketStation) color = "#a5a5a5";
                }

                
                tile.setStyle("-fx-background-color: " + color + "; -fx-border-color: #555; -fx-border-width: 0.5px;");
            }
        }
    }

    /**
     * Moves the visual {@code playerSprite} to the correct tile corresponding to the 
     * shopper model's {@code position}.
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
     * Executes the interaction logic for the Amenity in the shopper's line of sight.
     * Triggers appropriate UI dialogs for Displays and ProductSearch, otherwise calls 
     * the model's {@code amenity.interact()} method.
     */
    private void handleInteraction() {
        Amenity amenity = map.getAmenityInVision(shopper.getPosition(), shopper.getFacing());

        if (amenity == null || amenity instanceof Wall) {
            System.out.println("Nothing to interact with.");
            return;
        }

        
        if (amenity instanceof Display) {
            showDisplayDialog((Display) amenity);
        }
       
        else if (amenity instanceof ProductSearch) {
            showSearchDialog((ProductSearch) amenity);
        }
       
        else {
           
            amenity.interact(shopper);
        }
    }

    /**
     * Displays a dialog for taking products from a Display amenity (Shelf, Table, Counter).
     * @param display The Display amenity to take products from.
     */
    private void showDisplayDialog(Display display) {
        List<Product> products;

        
        if (display instanceof ChilledCounter) {
            products = ((ChilledCounter)display).getProducts();
        } else if (display instanceof Table) {
            products = ((Table)display).getProducts();
        } else if (display instanceof Shelf) {
           
            List<List<Product>> tiers = ((Shelf)display).getTiers();
            products = new java.util.ArrayList<>();
            for(List<Product> tier : tiers) products.addAll(tier);
        } else {
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle(display.getClass().getSimpleName());
        dialog.setHeaderText("Select an item to TAKE (or cancel)");
        dialog.setContentText("Available items:");

       
        if (products.isEmpty()) {
            dialog.getItems().add("(Empty)");
        } else {
            for (int i = 0; i < products.size(); i++) {
                dialog.getItems().add((i + 1) + ". " + products.get(i).toString());
            }
        }
        dialog.getItems().add("[RETURN AN ITEM FROM INVENTORY]");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String selection = result.get();

            if (selection.equals("[RETURN AN ITEM FROM INVENTORY]")) {
                showReturnDialog(display);
            } else if (!selection.equals("(Empty)")) {
                try {
                    int index = Integer.parseInt(selection.split("\\.")[0]) - 1;
                    Product p = products.remove(index);

                    
                    if(shopper.takeProduct(p)) {
                        System.out.println("You took: " + p.getName());
                    } else {
                        products.add(index, p); 
                    }
                } catch (Exception e) {
                    System.out.println("Selection error.");
                }
            }
        }
    }

    /**
     * Displays a dialog for returning a product from the shopper's inventory to a Display amenity.
     * @param display The Display amenity to return the product to.
     */
    private void showReturnDialog(Display display) {
        List<Product> myItems = shopper.getAllProducts();
        if (myItems.isEmpty()) {
            System.out.println("You have nothing to return.");
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
                System.out.println("Returned " + p.getName());
            }
        }
    }

    /**
     * Displays a dialog for the ProductSearch terminal, prompting for a product name to search for.
     * Prints search results to the console based on the map's inventory.
     * @param searchParams The ProductSearch amenity being interacted with.
     */
    private void showSearchDialog(ProductSearch searchParams) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Product Search");
        dialog.setHeaderText("Enter product name:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            System.out.println("Searching for: " + name);
            List<Display> displays = map.getAllDisplays();
            boolean found = false;
            for (Display d : displays) {
                if (d.containsProductByName(name)) {
                    System.out.println("Found at: " + d.getAddress());
                    found = true;
                }
            }
            if (!found) System.out.println("Product not found.");
        });
    }


    /**
     * The standard main entry method for launching the JavaFX application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}