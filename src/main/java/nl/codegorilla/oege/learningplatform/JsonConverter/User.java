package nl.codegorilla.oege.learningplatform.JsonConverter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("FullName")
    private String fullName;
    @JsonProperty("StudentID")
    private int studentID;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
}
