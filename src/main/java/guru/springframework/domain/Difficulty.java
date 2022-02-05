package guru.springframework.domain;

public enum Difficulty {
    EASY("Easy"),
    MODERATE("Moderate"),
    HARD("Hard");

    private final String displayValue;

    Difficulty(String displayName) {
        this.displayValue = displayName;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
