package nl.codegorilla.oege.learningplatform;

import ca.pfv.spmf.algorithms.sequentialpatterns.spam.AlgoVMSP;
import ca.pfv.spmf.algorithms.sequentialpatterns.spam.PatternVMSP;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public class Vmsp {
    public static List<TreeSet<PatternVMSP>> execute() throws IOException {

        AlgoVMSP algo = new AlgoVMSP();

        // optional parameters

        // maximum pattern length:
        // algo.setMaximumPatternLength(4);

        // max gap between two itemsets in a pattern.
        // If set to 1, only patterns of contiguous itemsets will be found (no gap).
        algo.setMaxGap(1);

        // if you set the following parameter to true, the sequence ids of the sequences where
        // each pattern appears will be shown in the result
        // algo.showSequenceIdentifiersInOutput(true);

        String input = "C:\\Users\\CGstudent\\OneDrive\\Bureaublad\\input.txt";
        String output = "C:\\Users\\CGstudent\\OneDrive\\Bureaublad\\output.txt";
        double minsupRel = 0.5;
        List<TreeSet<PatternVMSP>> maxPatterns = algo.runAlgorithm(input, output, minsupRel);
        algo.printStatistics();
        return maxPatterns;
    }
}
