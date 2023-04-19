package nl.codegorilla.oege.learningplatform;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JsonHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T getObjectFromJson(String filePath, TypeReference<T> typeReference) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return objectMapper.readValue(inputStream, typeReference);
        }
    }

    public static void writeOutputToJson(Map<String, TargetData> outputPatterns, String filePath) throws IOException {
        File outputFile = new File(filePath);
        try (JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputFile, JsonEncoding.UTF8)) {
            jsonGenerator.useDefaultPrettyPrinter();
            objectMapper.writeValue(jsonGenerator, outputPatterns);
        }
    }
    public static void writeRecommendToJson(Recommended outputPatterns, String filePath) throws IOException {
        File outputFile = new File(filePath);
        try (JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputFile, JsonEncoding.UTF8)) {
            jsonGenerator.useDefaultPrettyPrinter();
            objectMapper.writeValue(jsonGenerator, outputPatterns);
        }
    }
}