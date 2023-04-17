package nl.codegorilla.oege.learningplatform;

public class Settings {
    private int maxPatternLength;
    private int maxGap;
    private double minSupRel;

    // default constructor, necessary for Jackson library!
    public Settings() {
    }

    public int getMaxPatternLength() {
        return maxPatternLength;
    }

    public int getMaxGap() {
        return maxGap;
    }

    public double getMinSupRel() {
        return minSupRel;
    }
}
