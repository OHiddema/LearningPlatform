package nl.codegorilla.oege.learningplatform.jsonconverter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonEntry {
    @JsonProperty("Target")
    private Target target;
    @JsonProperty("User")
    private User user;
    @JsonProperty("Steps")
    private List<Step> steps;

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
