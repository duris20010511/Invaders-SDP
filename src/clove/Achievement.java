package clove;

import java.util.logging.Logger;

public class Achievement {



    public enum AchievementType {
        KILLS,
        KILLSTREAKS,
        LIVES,
        STAGE,
        TRIALS,
        FASTKILL,
        SCORE
    }

    private  static final Logger LOGGER = Logger.getLogger(Achievement.class.getName());
    private String achievementName;
    private String achievementDescription;
    private int requiredValue;
    private boolean isCompleted;
    private AchievementType achievementType;
    private int gem;

    public Achievement(String achievementName, String achievementDescription, int requiredValue, AchievementType type) {
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.isCompleted = false;
        this.achievementType = type;
        this.requiredValue = requiredValue;
        this.gem = 0;
    }
    public Achievement(String achievementName, String achievementDescription, int requiredValue, AchievementType type, int gem) {
        this(achievementName, achievementDescription, requiredValue, type);
        this.gem = gem;
    }

    public void CompleteAchievement() {
        if (!this.isCompleted) {
            this.isCompleted = true;
            LOGGER.info("Achievement completed: " + achievementName);
        }
    }

    public AchievementType getType() {
        return achievementType;
    }

    public int getRequiredValue() {
        return requiredValue;
    }

    public String getAchievementDescription() {
        return achievementDescription;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }


    public int getGem() { return gem; }

    public void setCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Achievement that = (Achievement) o;
        return achievementName.equals(that.achievementName);
    }

    @Override
    public int hashCode() {
        return achievementName.hashCode();
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "achievementName='" + achievementName + '\'' +
                ", achievementDescription='" + achievementDescription + '\'' +
                ", isCompleted=" + isCompleted +
                ", achievementType=" + achievementType +
                ", requiredValue=" + requiredValue +
                ", gem=" + gem +
                '}';
    }
}