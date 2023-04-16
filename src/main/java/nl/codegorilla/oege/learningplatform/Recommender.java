package nl.codegorilla.oege.learningplatform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recommender {
    public static void main(String[] args) {
        Map<String, Integer> stepScores = new HashMap<>();

        // input: ***********************************************
        String searchTarget = "T2";
        List<String> listSearchFor = List.of("S2", "S3");
        // ******************************************************

        Map<String, TargetData> patternsFound = JsonHandler.readInputFromJson(Main.getFilePathString(Main.FILENAME_OUTPUT));
        TargetData t = patternsFound.get(searchTarget);
        for (Map.Entry<Integer, List<String>> entry : t.getPatterns()) {
            List<String> listSearchIn = entry.getValue();
            if (listSearchIn.size() - listSearchFor.size() < 1) continue;
            int max = listSearchIn.size() - listSearchFor.size();
            outerloop:
            for (int startPosSearchIn = 0; startPosSearchIn < max; startPosSearchIn++) {
                for (int i = 0; i < listSearchFor.size(); i++) {
                    if (!listSearchFor.get(i).equals(listSearchIn.get(startPosSearchIn + i))) {
                        // startPosSearchIn++;
                        continue outerloop;
                    }
                }
                String stepAdvice = listSearchIn.get(startPosSearchIn + listSearchFor.size());
                if (stepScores.containsKey(stepAdvice)) {
                    stepScores.put(stepAdvice, stepScores.get(stepAdvice) + entry.getKey());
                } else {
                    stepScores.put(stepAdvice, entry.getKey());
                }
                System.out.println("Found in: " + listSearchIn
                        + " weight: " + entry.getKey()
                        + " advice: " + listSearchIn.get(startPosSearchIn + listSearchFor.size()));
                break;
            }
        }
        String stepWithMaxScore = stepScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        if (stepWithMaxScore != null) {
            System.out.println("Advised next step: " + stepWithMaxScore);
        } else {
            System.out.println("I'm sorry, no advice can be given.");
        }
    }
}