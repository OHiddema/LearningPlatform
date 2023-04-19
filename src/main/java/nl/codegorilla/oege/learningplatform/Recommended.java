package nl.codegorilla.oege.learningplatform;

import java.util.List;
import java.util.Map;

public class Recommended {
    private String targetCode;
    private String studentNr;
    private List<Map.Entry<String, Integer>> scores;

    public Recommended(String targetCode, String studentNr, List<Map.Entry<String, Integer>> scores) {
        this.targetCode = targetCode;
        this.studentNr = studentNr;
        this.scores = scores;
    }

    public Recommended() {
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public String getStudentNr() {
        return studentNr;
    }

    public void setStudentNr(String studentNr) {
        this.studentNr = studentNr;
    }

    public List<Map.Entry<String, Integer>> getScores() {
        return scores;
    }

    public void setScores(List<Map.Entry<String, Integer>> scores) {
        this.scores = scores;
    }
}
