package service.getTextFromFiles;


import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class GetTextFromFiles {
    public static void convertToText(String destinationFolderPath, String destinationFormat, String sourceFilePath, String destinationFileName) throws TesseractException {
        //File image = new File("src/main/resources/img/pdf-1.JPG");
        File image = new File(sourceFilePath);
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata/");
        tesseract.setLanguage("hun");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result = tesseract.doOCR(image);
        System.out.println(result);

    }
    public static String getFileFormat(String absolutePath) {
        int dotPosition = absolutePath.lastIndexOf(".");
        return absolutePath.substring(dotPosition + 1);
    }

}
