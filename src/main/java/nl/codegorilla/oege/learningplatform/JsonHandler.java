package nl.codegorilla.oege.learningplatform;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonHandler {

    public static Settings getSettingsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            URL resourceUrl = JsonHandler.class.getClassLoader().getResource("settings.json");
            return objectMapper.readValue(resourceUrl, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Target> getTargetListFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            URL resourceUrl = JsonHandler.class.getClassLoader().getResource("input.json");
            TypeReference<HashMap<String, Target>> typeRef = new TypeReference<>() {
            };
            HashMap<String, Target> dataMap = objectMapper.readValue(resourceUrl, typeRef);
            return new ArrayList<>(dataMap.values());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeOutputToJson(Map<String, List<List<String>>> outputPatterns) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File outputFile = new File("src/main/resources/output.json");
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputFile, JsonEncoding.UTF8);
        jsonGenerator.useDefaultPrettyPrinter();
        objectMapper.writeValue(jsonGenerator, outputPatterns);
    }
}