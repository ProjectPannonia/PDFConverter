package main.service.pdfToImage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFtoImage {


    public static void convertToImages(String pdfChooserLabel, String extension, String destinationLabel, int targetDpi) {
        PDDocument document;
        PDFRenderer pdfRenderer;
        BufferedImage bufferedImage;

        try {
            document= PDDocument.load(new File(pdfChooserLabel));
            pdfRenderer = new PDFRenderer(document);

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                bufferedImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                ImageIOUtil.writeImage(bufferedImage, String.format(destinationLabel + "pdf-%d.%s", page + 1, extension), targetDpi);
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void convertToImages(String pdfChooserLabel) {
        PDDocument document;
        PDFRenderer pdfRenderer;
        BufferedImage bufferedImage;

        try {
            document = PDDocument.load(new File(pdfChooserLabel));
            System.out.println(document.getNumberOfPages());
            System.out.println(document.getDocumentCatalog());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
