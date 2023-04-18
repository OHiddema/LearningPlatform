package nl.codegorilla.oege.learningplatform;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.*;

public class Recommender {
    public static void main(String[] args) {

        // input: ***********************************************
        String searchTarget = "T2";
        List<String> listSearchFor = new ArrayList<>(Arrays.asList("S08", "S09", "S10", "S11", "S12", "S13"));
        // ******************************************************

        Map<String, TargetData> patternsFound;
        try {
            String fOut = Names.getFilePathString(Names.FILENAME_OUTPUT);
            patternsFound = JsonHandler.getObjectFromJson(fOut, new TypeReference<>() {
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        TargetData targetData = patternsFound.get(searchTarget);

        while (listSearchFor.size() >= 1) {
            Map<String, Integer> stepScores = findMatchingPatterns(targetData, listSearchFor);
            if (stepScores.isEmpty()) {
                listSearchFor.remove(0);
                if (listSearchFor.isEmpty()) {
                    System.out.println("""
                    I'm sorry, no advice can be given.
                    No patterns found for this student.""");
                }
            } else {
                List<Map.Entry<String, Integer>> sortedList = sortMapByValue(stepScores);
                System.out.println("Advised next step, sorted by score from highest to lowest:");
                for (Map.Entry<String, Integer> entry : sortedList) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
                break;
            }
        }
    }

    // static utility functions

    private static Map<String, Integer> findMatchingPatterns(TargetData targetData, List<String> listSearchFor) {
        Map<String, Integer> stepScores = new HashMap<>();
        for (Map.Entry<Integer, List<String>> entry : targetData.getPatterns()) {
            List<String> listSearchIn = entry.getValue();
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
        return stepScores;
    }


    public static List<Map.Entry<String, Integer>> sortMapByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list;
    }

}