package nl.codegorilla.oege.learningplatform;

import nl.codegorilla.oege.learningplatform.SMPF.AlgoVMSP;
import nl.codegorilla.oege.learningplatform.SMPF.Itemset;
import nl.codegorilla.oege.learningplatform.SMPF.PatternVMSP;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        List<Target> targetList = JsonHandler.getTargetListFromJson();
        if (targetList.isEmpty()) {
            System.out.println("Problem with reading the targetlist! The program is aborted");
            return;
        }

        Settings settings;
        Optional<Settings> optionalSettings = JsonHandler.getSettingsFromJson();
        if (optionalSettings.isPresent()) {
            settings = optionalSettings.get();
        } else {
            System.out.println("Problem with reading the settings! The program is aborted");
            return;
        }

        // each unique step has to get a unique id (a positive integer)
        // because VMSP algorithm can only handle positive integers and no strings
        Set<String> steps = targetList.stream()
                .flatMap(target -> target.steps.stream())
                .collect(Collectors.toSet());

        // Hashmap that relates step as String with step as positive integer
        HashMap<String, Integer> stepKeyMap = new HashMap<>();
        int index = 1;
        for (String step : steps) {
            stepKeyMap.put(step, index++);
        }

        //create the inverted map
        HashMap<Integer, String> invertedMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : stepKeyMap.entrySet()) {
            invertedMap.put(entry.getValue(), entry.getKey());
        }

        // group all targets in targetList by targetCode.
        Map<String, List<Target>> targetsByCode = targetList.stream()
                .collect(Collectors.groupingBy(Target::getTargetCode));

        Map<String, List<Map.Entry<Integer, List<String>>>> outputPatterns = new TreeMap<>();

        //loop through the targetCodes
        for (Map.Entry<String, List<Target>> item : targetsByCode.entrySet()) {
            StringBuilder targetData = new StringBuilder();
            for (Target target : item.getValue()) {
                String line = target.steps.stream()
                        .map(stepKeyMap::get)
                        .map(String::valueOf)
                        .collect(Collectors.joining(" -1 ")) +
                        " -1 -2";
                targetData.append(line);
                targetData.append(System.lineSeparator());
            }
            // get the maxPatterns for this targetCode
            AlgoVMSP algo = new AlgoVMSP();
            algo.setMaximumPatternLength(settings.getMaxPatternLength());
            algo.setMaxGap(settings.getMaxGap());
            double minSupRel = settings.getMinSupRel();
            List<TreeSet<PatternVMSP>> maxPatterns = algo.runAlgorithm(targetData.toString(), minSupRel);
            algo.printStatistics();

            String key = item.getKey();
            // Create a new list for this key

            List<Map.Entry<Integer, List<String>>> nestedList = new ArrayList<>();

            for (TreeSet<PatternVMSP> tree : maxPatterns) {
                if (tree == null) {
                    continue;
                }
                // for each pattern
                for (PatternVMSP pattern : tree) {
                    List<String> list = new ArrayList<>();
                    for (Itemset itemset : pattern.getPrefix().getItemsets()) {
                        list.add(invertedMap.get(itemset.getItems().get(0)));
                    }
                    nestedList.add(new AbstractMap.SimpleEntry<>(pattern.getSupport(), list));
                }
            }
            nestedList.sort(Collections.reverseOrder(Map.Entry.comparingByKey()));
            outputPatterns.put(key, nestedList);
        }
        JsonHandler.writeOutputToJson(outputPatterns);
    }
}