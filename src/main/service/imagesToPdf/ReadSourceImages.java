package main.service.imagesToPdf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class ReadSourceImages {

    private static ObservableList<PdfFile> filesInFolder = FXCollections.observableArrayList();

    public static ObservableList<PdfFile> readSourceFiles(String filePath) {
        readSourceFolder(filePath);
        return filesInFolder;
    }
    private static void readSourceFolder(String filePath) {
        readFilesFromSourceFolder(new File(filePath));
    }
    private static void readFilesFromSourceFolder(File sourceFilesFolder) {
        File[] images = sourceFilesFolder.listFiles();
        File img;
        for(int i = 0; i < images.length; i++) {
            img = images[i];
            String fileName = img.getName();
            String fileFormat = getFileFormat(fileName);
            String filePath = img.getAbsolutePath();
            PdfFile file = new PdfFile(i,fileName,fileFormat,filePath);

            filesInFolder.add(file);
        }
    }
    public static String getFileFormat(String fileName) {
        int dotPosition = fileName.indexOf(".");
        return fileName.substring(dotPosition + 1);
    }
}
