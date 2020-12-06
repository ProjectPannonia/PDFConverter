package service.panes.pane4;

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
            String fileName = getFileName(img.getName());
            String fileFormat = getFileFormat(img.getName());
            String filePath = img.getAbsolutePath();
            PdfFile file = new PdfFile(i,fileName,fileFormat,filePath);

            filesInFolder.add(file);
        }
    }
    public static String getFileFormat(String fileName) {
        int dotPosition = fileName.indexOf(".");
        return fileName.substring(dotPosition + 1);
    }
    public static String getFileName(String fileName) {
        int dotPosition = fileName.indexOf(".");
        return fileName.substring(0,dotPosition);
    }
}
