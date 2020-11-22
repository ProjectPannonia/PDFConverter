package main.service.getTextFromFiles;

public class GetTextFromFiles {
    public static void convertToText(String destinationFolderPath, String destinationFormat, String sourceFilePath) {


    }
    public static String getFileFormat(String absolutePath) {
        int dotPosition = absolutePath.lastIndexOf(".");
        return absolutePath.substring(dotPosition + 1);
    }

}
