package nl.codegorilla.oege.learningplatform.jsonconverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    public static void convert(String filePathIn, String filePathOut) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, JsonEntry.class);
        try (InputStream inputStream = new FileInputStream(filePathIn)) {

            // convert incoming json to a java data structure
            List<JsonEntry> originalJsonList = objectMapper.readValue(inputStream, listType);

            // convert this to the desired data structure
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

            // serialize the new datastructure to json
            String desiredOutput = objectMapper.writeValueAsString(desiredJson);
            FileWriter writer = new FileWriter(filePathOut);
            writer.write(desiredOutput);

            writer.close();
        }
    }
}
