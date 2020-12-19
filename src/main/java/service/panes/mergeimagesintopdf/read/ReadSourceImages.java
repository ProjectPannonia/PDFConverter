package service.panes.mergeimagesintopdf.read;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.panes.mergeimagesintopdf.pdfmodel.PdfFile;

import java.io.File;

public class ReadSourceImages {

    private static ObservableList<PdfFile> filesInFolder = FXCollections.observableArrayList();
    private static ObservableList<PdfFile> sortedFilesInFolder = FXCollections.observableArrayList();

    public static ObservableList<PdfFile> readSourceFiles(String filePath) {
        readSourceFolder(filePath);
        return sortedFilesInFolder;
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
        sortByFilename();
    }
    private static void sortByFilename() {

        int nextIntValue = 0;
        while (sortedFilesInFolder.size() != filesInFolder.size()) {
            for (int i = 0; i < filesInFolder.size(); i++) {
                if(Integer.valueOf(filesInFolder.get(i).getFileName())== nextIntValue) {
                    sortedFilesInFolder.add(filesInFolder.get(i));
                    nextIntValue++;
                    break;
                }
            }
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
