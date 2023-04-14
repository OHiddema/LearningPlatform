package nl.codegorilla.oege.learningplatform.JsonConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    //    public static void main(String[] args) {
    public static void convert() {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, JsonEntry.class);
        try (InputStream inputStream = new FileInputStream("C:\\Users\\CGstudent\\learningplatform\\Path.json")) {
            List<JsonEntry> originalJsonList = objectMapper.readValue(inputStream, listType);
            System.out.println(originalJsonList);

            DesiredJson desiredJson = new DesiredJson();
            List<DesiredTarget> desiredTargets = new ArrayList<>();
            for (JsonEntry jsonEntry : originalJsonList) {
                DesiredTarget desiredTarget = new DesiredTarget();
                desiredTarget.setStudentNr(Integer.toString(jsonEntry.getUser().getStudentID()));
                desiredTarget.setTargetCode(jsonEntry.getTarget().getTargetList().getTargetCode());
                List<String> desiredSteps = new ArrayList<>();
                for (Step originalStep : jsonEntry.getSteps()) {
                    desiredSteps.add(originalStep.getStepList().getStepCode());
                }
                desiredTarget.setSteps(desiredSteps);
                desiredTargets.add(desiredTarget);
            }
            desiredJson.setTargets(desiredTargets);
            String desiredOutput = objectMapper.writeValueAsString(desiredJson);

            // Create a temporary file in memory
//            File tempFile = File.createTempFile("converted", ".json");
            File tempFile = new File("C:\\Users\\CGstudent\\learningplatform\\converted.json");

            // Write the contents of the string to the file
            FileWriter writer = new FileWriter(tempFile);
            writer.write(desiredOutput);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
