package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.*;

import clove.Achievement;
import clove.AchievementManager;
import engine.*;


/**
 * Implements the achievements Screen
 */


public class AchievementsScreen extends Screen {

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;
    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    /** Maximum number of achievements per page */
    private static final int ACHIEVEMENTS_PER_PAGE = 10;
    /** Current page */
    private int currentPage;
    /** Total pages */
    private int totalPages;
    /** List od all achievements */
    private List<Achievement> achievementList;



    /**
     * Constructor, initializes the achievements screen properties.
     *
     * @param width
     *              Screen width
     * @param height
     *              Screen height
     * @param fps
     *              Frames per second, frame rate at which the game is run.
     *
     */
    public AchievementsScreen(final int width, final int height, final int fps, AchievementManager achievementManager) {
        super(width, height, fps);

        // Initialize
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
        this.currentPage = 0;

        // Get list
        try {
            // AchievementManager에서 모든 업적을 가져옵니다.
            this.achievementList = achievementManager.getAllAchievements();
            this.totalPages = (int) Math.ceil((double) achievementList.size() / ACHIEVEMENTS_PER_PAGE);
        } catch (Exception e) {
            logger.warning("Couldn't load achievements!" + e.getMessage());
            this.achievementList = new ArrayList<>();
            this.totalPages = 1;
        }
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
     * Updates the elements on screen and checks for user inputs.
     */
    protected final void update() {
        super.update();

        draw();
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
                this.returnCode = 1; //main menu
                this.isRunning = false;
            }
        }
    }

    /**
     * Moves to the next page of achievements, if available.
     */
    private void nextPage() {
        if (currentPage < totalPages - 1) {
            currentPage++;
        }
    }

    /**
     * Moves to the previous page of achievements, if available.
     */
    private void previousPage() {
        if (currentPage > 0) {
            currentPage --;
        }
    }

    private void drawAchievements() {
        // Calculate which achievements to display for the current page
        int start = currentPage * ACHIEVEMENTS_PER_PAGE;
        int end = Math.min(start + ACHIEVEMENTS_PER_PAGE, achievementList.size());

        // Draw achievements
        int yPosition = 200; // 시작 위치
        for (int i = start; i < end; i++) {
            Achievement achievement = achievementList.get(i);
            DrawManager.drawAchievement(50, yPosition, achievement);
            yPosition += 30;
        }
    }


    /**
     * Draws the achievements on the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        // Title
        drawManager.drawAchievementTitle(this);

        //
        drawAchievements();
        super.drawPost();
        drawManager.completeDrawing(this);
    }
}
