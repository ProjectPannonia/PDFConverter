package main.service.getTextFromFiles;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static main.service.getTextFromFiles.ScanedImage.processImg;

public class GetTextFromFiles {
    public static void convertToText(String destinationFolderPath, String destinationFormat, String sourceFilePath) throws TesseractException {
        File image = new File("src/main/resources/img/pdf-1.JPG");
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
