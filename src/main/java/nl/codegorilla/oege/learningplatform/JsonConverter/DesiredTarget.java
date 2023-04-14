package nl.codegorilla.oege.learningplatform.JsonConverter;

import java.util.List;

public class DesiredTarget {
    private String studentNr;
    private String targetCode;
    private List<String> steps;

    public String getStudentNr() {
        return studentNr;
    }

    public void setStudentNr(String studentNr) {
        this.studentNr = studentNr;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
