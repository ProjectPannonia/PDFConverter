package main.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFtoImage {


    public static void convertToImages(String pdfChooserLabel, String extension, String destinationLabel) {
        PDDocument document;
        PDFRenderer pdfRenderer;
        BufferedImage bufferedImage;

        try {
            document= PDDocument.load(new File(pdfChooserLabel));
            pdfRenderer = new PDFRenderer(document);

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                bufferedImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                ImageIOUtil.writeImage(bufferedImage, String.format(destinationLabel + "pdf-%d.%s", page + 1, extension), 300);
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
