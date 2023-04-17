package nl.codegorilla.oege.learningplatform;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class JsonHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Optional<Settings> getSettingsFromJson(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return Optional.of(objectMapper.readValue(inputStream, Settings.class));
        }
    }

    public static List<Target> getTargetListFromJson(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return objectMapper.readValue(inputStream, TargetList.class).getTargets();
        }
    }

    public static Map<String, TargetData> readInputFromJson(String filePath) throws IOException {
        File inputFile = new File(filePath);
        try (JsonParser jsonParser = objectMapper.getFactory().createParser(inputFile)) {
            return objectMapper.readValue(jsonParser, new TypeReference<>() {
            });
        }
    }

    public static void writeOutputToJson(Map<String, TargetData> outputPatterns, String filePath) throws IOException {
        File outputFile = new File(filePath);
        try (JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputFile, JsonEncoding.UTF8)) {
            jsonGenerator.useDefaultPrettyPrinter();
            objectMapper.writeValue(jsonGenerator, outputPatterns);
        }
    }
}