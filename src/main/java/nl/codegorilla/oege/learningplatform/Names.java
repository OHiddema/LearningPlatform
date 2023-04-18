package nl.codegorilla.oege.learningplatform;

import java.io.File;

public class Names {
    // map where all files are stored
    public static final String USERMAP_NAME = "learningplatform";

    // files
    public static final String FILENAME_INPUT = "input.json";
    public static final String FILENAME_CONVERTED = "converted.json";
    public static final String FILENAME_OUTPUT = "output.json";
    public static final String FILENAME_SETTINGS = "settings.json";

    // intermediate files, re-created for each target in loop, necessary for AlgoVMSP
    public static final String VMSP_INPUT = "vmsp_input.txt";
    public static final String VMSP_OUTPUT = "vmsp_output.txt";

    public static String getFilePathString(String fileName) {
        return System.getProperty("user.home") +
                File.separator +
                USERMAP_NAME +
                File.separator +
                fileName;
    }
}
