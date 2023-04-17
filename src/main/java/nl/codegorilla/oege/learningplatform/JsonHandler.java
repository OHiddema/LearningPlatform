package nl.codegorilla.oege.learningplatform;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JsonHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Optional<Settings> getSettingsFromJson(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return Optional.of(objectMapper.readValue(inputStream, Settings.class));
        }
    }

    public static TargetList getTargetListFromJson(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return objectMapper.readValue(inputStream, TargetList.class);
        }
    }

    public static Map<String, TargetData> readInputFromJson(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return objectMapper.readValue(inputStream, new TypeReference<>() {
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