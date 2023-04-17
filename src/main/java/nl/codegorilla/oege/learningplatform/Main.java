package nl.codegorilla.oege.learningplatform;

import ca.pfv.spmf.algorithms.sequentialpatterns.spam.AlgoVMSP;
import ca.pfv.spmf.algorithms.sequentialpatterns.spam.PatternVMSP;
import ca.pfv.spmf.patterns.itemset_list_integers_without_support.Itemset;
import nl.codegorilla.oege.learningplatform.jsonconverter.JsonConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // map where all the files for this program are stored
    private static final String USERMAP_NAME = "learningplatform";

    private static final String FILENAME_INPUT = "input.json";
    private static final String FILENAME_CONVERTED = "converted.json";
    public static final String FILENAME_OUTPUT = "output.json";
    private static final String FILENAME_SETTINGS = "settings.json";

    // intermediate files, re-created for each target in loop, necessary for AlgoVMSP
    private static final String VMSP_INPUT = "vsmp_input.txt";
    private static final String VMSP_OUTPUT = "vsmp_output.txt";

    public static String getFilePathString(String fileName) {
        return System.getProperty("user.home") +
                File.separator +
                USERMAP_NAME +
                File.separator +
                fileName;
    }

    public static void main(String[] args) throws IOException {

        List<Target> targetList;
        Optional<Settings> optionalSettings;
        Settings settings;
        try {
            JsonConverter.convert(getFilePathString(FILENAME_INPUT), getFilePathString(FILENAME_CONVERTED));
            targetList = JsonHandler.getTargetListFromJson(getFilePathString(FILENAME_CONVERTED));
            optionalSettings = JsonHandler.getSettingsFromJson(getFilePathString(FILENAME_SETTINGS));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        settings = optionalSettings.orElseThrow(() -> new NoSuchElementException("No settings found!"));

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
            try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePathString(VMSP_INPUT)))) {
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
            List<TreeSet<PatternVMSP>> maxPatterns = algo.runAlgorithm(getFilePathString(VMSP_INPUT), getFilePathString(VMSP_OUTPUT), minSupRel);
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
            JsonHandler.writeOutputToJson(outputTargets, getFilePathString(FILENAME_OUTPUT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}