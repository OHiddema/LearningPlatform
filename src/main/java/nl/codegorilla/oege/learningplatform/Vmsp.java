package nl.codegorilla.oege.learningplatform;

import nl.codegorilla.oege.learningplatform.SMPF.AlgoVMSP;
import nl.codegorilla.oege.learningplatform.SMPF.PatternVMSP;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public class Vmsp {
    public static List<TreeSet<PatternVMSP>> execute(String tmpInputFile, Settings settings) throws IOException {

        // optional parameters

        // setMaximumPatternLength
        // maximum pattern length to search for.

        // setMaxGap
        // max gap between two itemsets in a pattern.
        // If set to 1, only patterns of contiguous itemsets will be found (no gap).
        // If this property is not set, MaxGaP will be positive infinity.

        // if you set the following parameter to true, the sequence ids of the sequences where
        // each pattern appears will be shown in the result
        // algo.showSequenceIdentifiersInOutput(true);
        // This does not affect the patterns found,
        // Ony the output to tmp_output, which is not used.

        AlgoVMSP algo = new AlgoVMSP();
        algo.setMaximumPatternLength(settings.getMaxPatternLength());
        algo.setMaxGap(settings.getMaxGap());
        double minsupRel = settings.getMinSupRel();
        List<TreeSet<PatternVMSP>> maxPatterns = algo.runAlgorithm(tmpInputFile, minsupRel);
        algo.printStatistics();
        return maxPatterns;
    }
}
