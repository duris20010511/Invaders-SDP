package clove;

import engine.DrawManager;
import screen.Screen;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

// TODO : Replace Object to Achievement Class
// Fixed, need testing
public class AchievementManager {
    /*
        Variables
     */
    private final Logger LOGGER = Logger.getLogger(AchievementManager.class.getName());
    private HashMap<Achievement, Boolean> achievementMap; // Object -> Achievement
    private ArrayList<AchievementChangedCallback> achievementChangedCallbacks;

    private AchievementConditions achievementConditions;

    public AchievementManager(DrawManager drawManager) {
        this.achievementConditions = new AchievementConditions(drawManager);
        this.achievementChangedCallbacks = new ArrayList<>();
    }

    public void updateAchievements(Screen screen) {
        LOGGER.info("Updating achievements...");

        achievementConditions.checkAllAchievements();
    }
    // 게임 시작, FastKill 설정
    public void onGameStart() {
        if (achievementConditions != null) {
            achievementConditions.startFastKillCheck();
            LOGGER.info("Fast kill check started.");
        }
    }
    // 게임 종료
    public void onGameEnd() {
        if (achievementConditions != null) {
            achievementConditions.stopFastKillCheck();
            LOGGER.info("Fast kill check stopped.");
        }
    }





    /*
        Callbacks
     */
    @FunctionalInterface
    public interface AchievementChangedCallback {
        void onAchievementChanged(Achievement achievement, boolean value); // Object -> Achievement
    }

    public void addAchievementChangedCallback(AchievementChangedCallback callback){
        achievementChangedCallbacks.add(callback);
    }

    public void removeAchievementChangedCallback(AchievementChangedCallback callback){
        achievementChangedCallbacks.remove(callback);
    }

    /*
        Declare
     */


    /*
        Functions
     */

    public boolean addAchievement(Achievement achievement, Boolean completed) { // Object -> Achievement
        if (achievementMap.containsKey(achievement))
            return false;
        achievementMap.put(achievement, completed);
        return true; // Changed code
    }

    public boolean addAchievement(Achievement achievement) { // Object -> Achievement로 변경
        return addAchievement(achievement, false);
    }

    public boolean hasAchivement(Achievement achievement){ // Object -> Achievement
        return achievementMap.containsKey(achievement);
    }

    public Set<Achievement> getAchievements() { // Object -> Achievement
        return achievementMap.keySet();
    }

    public boolean setAchievementValue(Achievement achievement, Boolean completed) { // Object -> Achievement
        if(!hasAchivement(achievement)){
            // TODO : Output a notification (or log) that setting an achievement failed
            //Completed writing log output code, needs testing
            LOGGER.warning("Failed to set achievement: " + achievement.getAchievementName()); // 로그 추가
            return false;
        }
        achievementMap.replace(achievement, completed);
        for (AchievementChangedCallback callback : achievementChangedCallbacks) {
            callback.onAchievementChanged(achievement, completed);
        }
        return true;
    }

    public boolean getAchievementValue(Achievement achievement) { // Object -> Achievement
        // Converts Boolean type to boolean type and returns
        return Boolean.TRUE.equals(achievementMap.getOrDefault(achievement, false));
    }

    /*
    Convenience function
    (Function added to make the code easier to use)
    */

    public boolean completeAchievement(Achievement achievement) { // Added Code
        if (!achievement.isCompleted()) {
            achievement.CompleteAchievement();
            return true;
        }
        return false;
    }

    public List<Achievement> getAllAchievements() {
        return achievementConditions.getAllAchievements();
    }


}
