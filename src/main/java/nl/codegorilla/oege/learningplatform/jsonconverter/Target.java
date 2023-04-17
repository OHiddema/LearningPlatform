package nl.codegorilla.oege.learningplatform.jsonconverter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Target {
    @JsonProperty("Path")
    private Path path;
    @JsonProperty("TargetList")
    private TargetList targetList;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public TargetList getTargetList() {
        return targetList;
    }

    public void setTargetList(TargetList targetList) {
        this.targetList = targetList;
    }
}
