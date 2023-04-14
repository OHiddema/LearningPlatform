package nl.codegorilla.oege.learningplatform.JsonConverter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Step {
    @JsonProperty("StepPosition")
    private int stepPosition;
    @JsonProperty("StepList")
    private StepList stepList;

    public int getStepPosition() {
        return stepPosition;
    }

    public void setStepPosition(int stepPosition) {
        this.stepPosition = stepPosition;
    }

    public StepList getStepList() {
        return stepList;
    }

    public void setStepList(StepList stepList) {
        this.stepList = stepList;
    }
}
