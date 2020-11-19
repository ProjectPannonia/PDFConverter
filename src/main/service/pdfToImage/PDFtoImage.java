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
                // BufferedImage,String.format(fileName,extension),dpi
                ImageIOUtil.writeImage(bufferedImage, String.format(destinationLabel + "pdf-%d.%s", page + 1, extension), targetDpi);
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void convertToImagesAndSplit(String pdfChooserLabel, String extension, String destinationLabel, int targetDpi, boolean split) {

        BufferedImage bufferedImage;

        try {
            PDDocument document= PDDocument.load(new File(pdfChooserLabel));
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                bufferedImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                BufferedImage[] imageParts = cutIntoTwo(bufferedImage);

                for (BufferedImage b : imageParts) {
                    ImageIOUtil.writeImage(bufferedImage, String.format(destinationLabel , page + 1, extension), targetDpi);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage[] cutIntoTwo(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage first = bufferedImage.getSubimage(0, 0, width/2, height);
        BufferedImage second = bufferedImage.getSubimage(width/2,0, width/2, height);

        return new BufferedImage[]{first,second};
    }
}
