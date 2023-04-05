package nl.codegorilla.oege.learningplatform;

import ca.pfv.spmf.algorithms.sequentialpatterns.spam.AlgoVMSP;
import ca.pfv.spmf.algorithms.sequentialpatterns.spam.PatternVMSP;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class Vmsp {
    public static List<TreeSet<PatternVMSP>> execute(String tmpInputFile, String tmpOutputFile) throws IOException {

        AlgoVMSP algo = new AlgoVMSP();

        // optional parameters

        // maximum pattern length:
        // algo.setMaximumPatternLength(4);

        // max gap between two itemsets in a pattern.
        // If set to 1, only patterns of contiguous itemsets will be found (no gap).
        // algo.setMaxGap(1);

        // if you set the following parameter to true, the sequence ids of the sequences where
        // each pattern appears will be shown in the result
        // algo.showSequenceIdentifiersInOutput(true);

        Scanner scanner = new Scanner(System.in);

        double minsupRel;
        do {
            System.out.print("Minimal relative pattern occurence. (0 <= minsupRel <= 1): ");
            while (!scanner.hasNextDouble()) {
                System.out.println("That's not a valid value.");
                System.out.print("Enter a value between 0 and 1: ");
                scanner.next();
            }
            minsupRel = scanner.nextDouble();
        } while (minsupRel < 0 || minsupRel > 1);

        HashMap<String, Boolean> mappings = new HashMap<>();
        mappings.put("y", true);
        mappings.put("n", false);
        Boolean useDefaultValues;
        do {
            System.out.print("Use default parameters for VMSP algorithm? (y/n): ");
            String input = scanner.next().toLowerCase();
            useDefaultValues = mappings.get(input);
            if (useDefaultValues == null) {
                System.out.println("That's not a valid value.");
            }
        } while (useDefaultValues == null);

        if (!useDefaultValues) {
            int maxPatternLength;
            do {
                System.out.print("Maximum pattern length to search for (>0): ");
                while (!scanner.hasNextInt()) {
                    System.out.println("That's not a valid value.");
                    System.out.print("Enter a positive integer: ");
                    scanner.next();
                }
                maxPatternLength = scanner.nextInt();
            } while (maxPatternLength <= 0);
            algo.setMaximumPatternLength(maxPatternLength);

            int maxGap;
            do {
                System.out.print("Maximal gap between steps (>0): ");
                while (!scanner.hasNextInt()) {
                    System.out.println("That's not a valid value.");
                    System.out.print("Enter a positive integer: ");
                    scanner.next();
                }
                maxGap = scanner.nextInt();
            } while (maxGap <= 0);
            algo.setMaxGap(maxGap);
        }
        scanner.close();

        List<TreeSet<PatternVMSP>> maxPatterns = algo.runAlgorithm(tmpInputFile, tmpOutputFile, minsupRel);
        algo.printStatistics();
        return maxPatterns;
    }
}
