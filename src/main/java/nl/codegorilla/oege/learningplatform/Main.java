package nl.codegorilla.oege.learningplatform;

import ca.pfv.spmf.algorithms.sequentialpatterns.spam.AlgoVMSP;
import ca.pfv.spmf.algorithms.sequentialpatterns.spam.PatternVMSP;
import ca.pfv.spmf.patterns.itemset_list_integers_without_support.Itemset;
import com.fasterxml.jackson.core.type.TypeReference;
import nl.codegorilla.oege.learningplatform.jsonconverter.JsonConverter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        List<Target> targetList;
        Settings settings;
        try {
            JsonConverter.convert(Names.getFilePathString(Names.FILENAME_INPUT), Names.getFilePathString(Names.FILENAME_CONVERTED));
            targetList = JsonHandler.getObjectFromJson(Names.getFilePathString(Names.FILENAME_CONVERTED),
                    new TypeReference<TargetList>() {
                    }).getTargets();
            settings = JsonHandler.getObjectFromJson(Names.getFilePathString(Names.FILENAME_SETTINGS),
                    new TypeReference<>() {
                    });
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

        // the results will be stored in this map
        // key: targetCode
        // value: patterns found for this target code:
        Map<String, TargetData> outputTargets = new TreeMap<>();

        //loop through the targetCodes
        for (Map.Entry<String, List<Target>> item : targetsByCode.entrySet()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(Names.getFilePathString(Names.VMSP_INPUT)))) {
                for (Target target : item.getValue()) {
                    String line = target.steps.stream()
                            .map(stepKeyMap::get)
                            .map(String::valueOf)
                            .collect(Collectors.joining(" -1 ")) +
                            " -1 -2";
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // get the maxPatterns for this targetCode, by running AlgoVMSP
            AlgoVMSP algo = new AlgoVMSP();
            algo.setMaximumPatternLength(settings.getMaxPatternLength());
            algo.setMaxGap(settings.getMaxGap());
            double minSupRel = settings.getMinSupRel();
            List<TreeSet<PatternVMSP>> maxPatterns = algo.runAlgorithm(Names.getFilePathString(Names.VMSP_INPUT), Names.getFilePathString(Names.VMSP_OUTPUT), minSupRel);
            algo.printStatistics();

            // key in each Map.Entry does not have to be unique!
            List<Map.Entry<Integer, List<String>>> nestedList = new ArrayList<>();
            for (TreeSet<PatternVMSP> patternVMSPTreeSet : maxPatterns) {
                if (patternVMSPTreeSet == null) {
                    continue;
                }
                // for each pattern
                for (PatternVMSP pattern : patternVMSPTreeSet) {
                    List<String> stringList = new ArrayList<>();
                    for (Itemset itemset : pattern.getPrefix().getItemsets()) {
                        stringList.add(invertedMap.get(itemset.getItems().get(0)));
                    }
                    nestedList.add(new AbstractMap.SimpleEntry<>(pattern.getSupport(), stringList));
                }
            }

            // patterns with the most occurrences first
            nestedList.sort(Collections.reverseOrder(Map.Entry.comparingByKey()));
            outputTargets.put(item.getKey(), new TargetData(item.getValue().size(), nestedList));
        }

        try {
            JsonHandler.writeOutputToJson(outputTargets, Names.getFilePathString(Names.FILENAME_OUTPUT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}