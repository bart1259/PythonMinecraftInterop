package net.bart1259.pymcinteropmod;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.slf4j.Logger;

public class PyFileManager {

    // Directory ex: "C:/scripts/"
    private String scriptDirectory;
    private Logger logger;

    PyFileManager(Logger logger, String scriptDirectory) {
        this.logger = logger;
        setScriptDirectory(scriptDirectory);
    }

    public boolean fileExists(String fileName) {
        // Add extension if needed
        String scriptName = cleanScriptName(fileName);

        // Check if file exists
        File tempFile = new File(scriptDirectory + scriptName);
        return tempFile.exists();
    }

    public String getFileContents(String fileName) throws IOException {
        String scriptName = cleanScriptName(fileName);
        File tempFile = new File(scriptDirectory + scriptName);
        String content = Files.readString(tempFile.toPath());
        return content;
    }

    public String getScriptDirectory() {
        return scriptDirectory;
    }

    public void setScriptDirectory(String scriptDirectory) {
        if(!scriptDirectory.endsWith("/") && !scriptDirectory.endsWith("\\")) {
            scriptDirectory += "/";
        }
        logger.info("Setting script directory to " + scriptDirectory);
        this.scriptDirectory = scriptDirectory;
    }

    private String cleanScriptName(String scriptName) {
        if(!scriptName.endsWith(".py")) {
            scriptName += ".py";
        }
        return scriptName;
    }
}
