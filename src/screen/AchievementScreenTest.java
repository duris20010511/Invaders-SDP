package screen;

import clove.Achievement;
import clove.AchievementManager;
import engine.DrawManager;
import engine.Frame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class AchievementScreenTest {

    private AchievementsScreen achievementsScreen;
    private AchievementManager achievementManager;
    private DrawManager drawManager;

    @BeforeEach
    public void setUp() {
        drawManager = DrawManager.getInstance();
        achievementManager = new AchievementManager(drawManager) {
            // Test achievement
            @Override
            public List<Achievement> getAllAchievements() {
                List<Achievement> achievements = new ArrayList<>();
                Achievement achievement1 = new Achievement("Achievement1","KILLS",5,Achievement.AchievementType.KILLS,1);
                Achievement achievement2 = new Achievement("Achievement2", "KILLSTREAKS",5,Achievement.AchievementType.KILLSTREAKS,1);
                Achievement achievement3 = new Achievement("Achievement3","LIVES",5,Achievement.AchievementType.LIVES,1);
                Achievement achievement4 = new Achievement("Achievement4","STAGE",5,Achievement.AchievementType.STAGE,1);
                Achievement achievement5 = new Achievement("Achievement5","TRIALS",5,Achievement.AchievementType.TRIALS,1);
                Achievement achievement6 = new Achievement("Achievement6","FASTKILL",5,Achievement.AchievementType.FASTKILL,1);
                Achievement achievement7 =new Achievement("Achievement7","SCORE",5,Achievement.AchievementType.SCORE,1);

                // complete
                achievementManager.completeAchievement(achievement1);
                achievementManager.completeAchievement(achievement3);

                achievements.add(achievement1);
                achievements.add(achievement2);
                achievements.add(achievement3);
                achievements.add(achievement4);
                achievements.add(achievement5);
                achievements.add(achievement6);
                achievements.add(achievement7);
                return achievements;
            }
        };

        achievementsScreen = new AchievementsScreen(690, 720, 60, achievementManager);
    }

    @Test
    public void testDraw() {
        Frame frame = new Frame(690, 720);
        DrawManager.getInstance().setFrame(frame);
        achievementsScreen.draw();
        try {
            Thread.sleep(5000);  // 5초 동안 창을 띄운 상태로 유지
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
