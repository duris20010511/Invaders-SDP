package screen;

import engine.Core;
import engine.Cooldown;
import engine.DrawManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipModelScreenTest {

    private ShipModelScreen shipModelScreen;
    private List<DrawManager.SpriteType> skins;

    @BeforeEach
    void setUp() {
        // Initialize ShipModelScreen with a mock screen size
        shipModelScreen = new ShipModelScreen(800, 600, 60);
        // Simulate some skins being loaded
        skins = List.of(DrawManager.SpriteType.values());
        shipModelScreen.skins = skins;
    }

    @Test
    void testNextPage() {
        // Initial page is 0
        assertEquals(0, shipModelScreen.currentPage, "Initial page should be 0");

        // Call nextPage and verify it changes
        shipModelScreen.nextPage();
        assertEquals(1, shipModelScreen.currentPage, "Page should move to the next one.");

        // Call nextPage again and check wrapping behavior
        shipModelScreen.nextPage();
        assertEquals(2 % skins.size(), shipModelScreen.currentPage, "Page should wrap around to the first skin.");
    }

    @Test
    void testPreviousPage() {
        // Start from the last page to test the wrap around
        shipModelScreen.currentPage = skins.size() - 1;

        // Call previousPage and verify the behavior
        shipModelScreen.previousPage();
        assertEquals(skins.size() - 2, shipModelScreen.currentPage, "Page should move to the previous one.");
    }



    @Test
    void testSaveSelectedSpriteType() {
        // Assume saveSelectedSpriteType() writes the selected skin to a file.
        // In the case of non-mocking, we just check if the method runs without exceptions.
        try {
            shipModelScreen.saveSelectedSpriteType();
        } catch (Exception e) {
            fail("saveSelectedSpriteType() threw an exception: " + e.getMessage());
        }
    }

    @Disabled
    void testUpdate() {

    }

    @Disabled
    void testDrawModelShip() {

    }


    @Disabled
    void testRun() {

    }
}
