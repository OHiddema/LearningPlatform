package nl.codegorilla.oege.learningplatform;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class JsonHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Optional<Settings> getSettingsFromJson(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return Optional.of(objectMapper.readValue(inputStream, Settings.class));
        } catch (IOException | NullPointerException e) {
            System.out.println("Failed to read settings.json: " + e.getMessage());
            return Optional.empty();
        }
    }

    public static List<Target> getTargetListFromJson(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return objectMapper.readValue(inputStream, TargetList.class).getTargets();
        } catch (IOException e) {
            System.out.println("Failed to read input.json: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static Map<String, TargetData> readInputFromJson(String filePath) {
        File inputFile = new File(filePath);
        try (JsonParser jsonParser = objectMapper.getFactory().createParser(inputFile)) {
            return objectMapper.readValue(jsonParser, new TypeReference<Map<String, TargetData>>() {});
        } catch (IOException e) {
            System.out.println("Failed to read from " + filePath + ": " + e.getMessage());
            return null;
        }
    }

    public static void writeOutputToJson(Map<String, TargetData> outputPatterns, String filePath) {
        File outputFile = new File(filePath);
        try (JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputFile, JsonEncoding.UTF8)) {
            jsonGenerator.useDefaultPrettyPrinter();
            objectMapper.writeValue(jsonGenerator, outputPatterns);
        } catch (IOException e) {
            System.out.println("Failed to write to output.json: " + e.getMessage());
        }
    }
}