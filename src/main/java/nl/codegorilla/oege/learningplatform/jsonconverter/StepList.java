package nl.codegorilla.oege.learningplatform.jsonconverter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StepList {
    @JsonProperty("StepCode")
    private String stepCode;

    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }
}
