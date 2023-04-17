package nl.codegorilla.oege.learningplatform;

import java.util.List;

public class Target {
    String studentNr;
    String targetCode;
    List<String> steps;

    // Jackson library requires that getters are defined for ALL fields!
    public String getStudentNr() {
        return studentNr;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public List<String> getSteps() {
        return steps;
    }
}