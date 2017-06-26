package com.abysov;

import java.io.*;
import java.util.Properties;

public class AppSettings {

    private String XlsBaseFilePath;
    private String XlsBaseInfoSheet;
    private String OutputFolderPath;
    private String InputFolderPath;
    private String FileSeparator;
    private String SubPath;

    public String getXlsBaseFilePath() {
        return XlsBaseFilePath;
    }

    public String getXlsBaseInfoSheet() {
        return XlsBaseInfoSheet;
    }

    public String getOutputFolderPath() {
        return OutputFolderPath;
    }

    public String getInputFolderPath() {
        return InputFolderPath;
    }

    public String getFileSeparator() {
        return FileSeparator;
    }

    public String getSubPath() {
        return SubPath;
    }


    public void settingsLoad() {

        Properties properties = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            if(input==null){
                System.out.println("Config file not found.");
                return;
            }

            properties.load(input);

            this.XlsBaseFilePath = properties.getProperty("XlsBaseFilePath");
            this.XlsBaseInfoSheet = properties.getProperty("XlsBaseInfoSheet");
            this.OutputFolderPath = properties.getProperty("OutputFolderPath");
            this.InputFolderPath = properties.getProperty("InputFolderPath");
            this.FileSeparator = properties.getProperty("FileSeparator");
            this.SubPath = properties.getProperty("SubPath");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}