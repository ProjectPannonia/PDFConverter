package main.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFtoImage {
    public static String convertToImages(String sourcePath, String extension, String resultPath) {
        PDDocument document;
        PDFRenderer pdfRenderer;
        BufferedImage bim;
        String conversionResult;
        int nameCounter = 0;
        File filesList[];
        File directoryDirectory = new File(resultPath);

        try {
            document = PDDocument.load(new File(sourcePath));
            pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page <document.getNumberOfPages(); page++) {
                bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                ImageIOUtil.writeImage(bim, String.format(resultPath + String.valueOf(nameCounter) + ".jpg", page + 1, extension),300);
                nameCounter++;
            }
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        filesList = directoryDirectory.listFiles();
        conversionResult = filesList.length == 0 ? "Conversion failed!" : "Conversion successful!";

        return conversionResult;
    }
}
