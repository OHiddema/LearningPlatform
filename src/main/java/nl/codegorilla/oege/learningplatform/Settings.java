package nl.codegorilla.oege.learningplatform;

public class Settings {
    private int maxPatternLength;
    private int maxGap;
    private double minSupRel;

    // default constructor, necessary for Jackson library!
    public Settings() {
    }

    public Settings(int maxPatternLength, int maxGap, double minSupRel) {
        this.maxPatternLength = maxPatternLength;
        this.maxGap = maxGap;
        this.minSupRel = minSupRel;
    }

    public int getMaxPatternLength() {
        return maxPatternLength;
    }

    public void setMaxPatternLength(int maxPatternLength) {
        this.maxPatternLength = maxPatternLength;
    }

    public int getMaxGap() {
        return maxGap;
    }

    public void setMaxGap(int maxGap) {
        this.maxGap = maxGap;
    }

    public double getMinSupRel() {
        return minSupRel;
    }

    public void setMinSupRel(double minSupRel) {
        this.minSupRel = minSupRel;
    }
}
