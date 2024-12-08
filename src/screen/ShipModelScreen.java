package screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import engine.Core;
import engine.Cooldown;
import engine.DrawManager.SpriteType;
import entity.Entity;

import static engine.DrawManager.drawShipModel;
import static engine.DrawManager.getSkinTypes;

/**
 * Implements a screen for selecting ship models.
 */
public class ShipModelScreen extends Screen {

    /** List of skins (ship models). */
    public List<SpriteType> skins;
    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;
    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    /** Current page (selected skin index). */
    public int currentPage;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param fps    Frames per second, frame rate at which the game is run.
     */
    public ShipModelScreen(final int width, final int height, final int fps) {
        super(width, height, fps);

        this.returnCode = 1;

        // Initialize cooldown for selection
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.currentPage = 0;
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();
        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();
        skins = getSkinTypes(); // Load available ship skins

        draw(); // Draw current state

        if (this.selectionCooldown.checkFinished()) {
            // Move to next page
            if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)) {
                nextPage();
                this.selectionCooldown.reset();
            }
            // Move to previous page
            if (inputManager.isKeyDown(KeyEvent.VK_LEFT)) {
                previousPage();
                this.selectionCooldown.reset();
            }
            // Return to main menu
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                saveSelectedSpriteType();
                this.returnCode = 1; // Main menu
                this.isRunning = false;
            }
        }
    }

    /**
     * Saves the currently selected SpriteType to a file named "skins" in the "res" directory.
     */
    public void saveSelectedSpriteType() {
        if (skins == null || skins.isEmpty()) {
            return; // No skins to save
        }

        SpriteType selectedSkin = skins.get(currentPage);

        try {
            // Ensure "res" directory exists
            File resDir = new File("res");
            if (!resDir.exists()) {
                resDir.mkdir();
            }

            // Write selected skin to "skins" file
            File skinsFile = new File(resDir, "skins");
            try (FileWriter writer = new FileWriter(skinsFile)) {
                writer.write(selectedSkin.name());
                System.out.println("Saved selected skin: " + selectedSkin.name());
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving selected skin to file.");
        }
    }


    /**
     * Moves to the next page of skins, if available.
     */
    public void nextPage() {
        if (skins != null && !skins.isEmpty()) {
            currentPage = (currentPage + 1) % skins.size(); // Wrap to first skin
        }
    }

    /**
     * Moves to the previous page of skins, if available.
     */
    public void previousPage() {
        if (skins != null && !skins.isEmpty()) {
            currentPage = (currentPage - 1 + skins.size()) % skins.size(); // Wrap to last skin
        }
    }

    /**
     * Draws the model ship for the current page.
     *
     * @param screen Screen to draw on.
     */
    public void drawModelShip(final Screen screen) {
        if (skins == null || skins.isEmpty()) {
            return; // No skins to display
        }

        // Ensure currentPage is within bounds
        currentPage = Math.floorMod(currentPage, skins.size()); // Safe wrap

        // Get the skin for the current page
        SpriteType skin = skins.get(currentPage);

        // Create the ship entity and set its sprite type
        Entity ship = new Entity(0, 0, 13 * 2, 8 * 2, Color.RED);
        ship.setSpriteType(skin);

        // Draw the ship at the center of the screen
        drawShipModel(ship, screen.getWidth() / 2 - 25, screen.getHeight() / 2);
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        // Draw menu and the current model
        drawManager.drawModelShipMenu(this);
        drawModelShip(this);

        super.drawPost();
        drawManager.completeDrawing(this);
    }
}
