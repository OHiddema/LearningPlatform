package nl.codegorilla.oege.learningplatform.jsonconverter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TargetList {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("TargetCode")
    private String targetCode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }
}
