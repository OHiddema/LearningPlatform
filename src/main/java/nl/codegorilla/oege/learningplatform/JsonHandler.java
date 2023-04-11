package nl.codegorilla.oege.learningplatform;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JsonHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Optional<Settings> getSettingsFromJson(String fileName) {
        try (InputStream inputStream = JsonHandler.class.getClassLoader().getResourceAsStream(fileName)) {
            return Optional.of(objectMapper.readValue(inputStream, Settings.class));
        } catch (IOException | NullPointerException e) {
            System.out.println("Failed to read settings.json: " + e.getMessage());
            return Optional.empty();
        }
    }

    public static List<Target> getTargetListFromJson(String fileName) {
        try (InputStream inputStream = JsonHandler.class.getClassLoader().getResourceAsStream(fileName)) {
            TargetList targetList = objectMapper.readValue(inputStream, TargetList.class);
            return targetList.getTargets();
        } catch (IOException e) {
            System.out.println("Failed to read input.json: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static void writeOutputToJson(Map<String, List<Map.Entry<Integer, List<String>>>> outputPatterns, String pathName) {
        File outputFile = new File(pathName);
        try (JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputFile, JsonEncoding.UTF8)) {
            jsonGenerator.useDefaultPrettyPrinter();
            objectMapper.writeValue(jsonGenerator, outputPatterns);
        } catch (IOException e) {
            System.out.println("Failed to write to output.json: " + e.getMessage());
        }
    }
}