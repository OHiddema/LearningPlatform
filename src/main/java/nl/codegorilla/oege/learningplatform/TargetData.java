package nl.codegorilla.oege.learningplatform;

import java.util.List;
import java.util.Map;

public class TargetData {
    private final int occurrences;
    private final List<Map.Entry<Integer, List<String>>> patterns;

    public TargetData(int occurrences, List<Map.Entry<Integer, List<String>>> patterns) {
        this.occurrences = occurrences;
        this.patterns = patterns;
    }

    // getters necessary for Jackson serialization

    public int getOccurrences() {
        return occurrences;
    }

    public List<Map.Entry<Integer, List<String>>> getPatterns() {
        return patterns;
    }
}
