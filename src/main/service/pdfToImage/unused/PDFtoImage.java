package main.service.pdfToImage.unused;

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
                bufferedImage = pdfRenderer.renderImageWithDPI(page, targetDpi, ImageType.RGB);
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
                System.out.println(imageParts.length);
                for (int i = 0; i < imageParts.length; i++) {
                    System.out.println(imageParts[i].getData());
                    System.out.println(i);
                    synchronized (PDFtoImage.class) {
                        ImageIOUtil.writeImage(imageParts[i], String.format(destinationLabel + "pdf-%d.%s", page, extension), targetDpi);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void convertToImagesAndSplitMod(String pdfChooserLabel, String extension, String destinationLabel, int targetDpi, boolean split) {

        try {
            BufferedImage wholeImageFromPdf;
            PDDocument document = PDDocument.load(new File(pdfChooserLabel));
            int numOfPages = document.getPages().getCount();
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            for (int i = 0; i < numOfPages; i++) {
                wholeImageFromPdf = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);
                ImageIOUtil.writeImage(wholeImageFromPdf, destinationLabel + String.valueOf(i) + "." + extension, targetDpi);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private synchronized static void readTempFolderFiles(String tempFiles) {

    }
    private synchronized static BufferedImage[] cutIntoTwo(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        BufferedImage[] result = new BufferedImage[2];
        result[0] = bufferedImage.getSubimage(0, 0, width/2, height);
        result[1] = bufferedImage.getSubimage(width/2,0, width/2, height);
        return result;
    }
    private synchronized static void writeImageArrToDisk(BufferedImage[] imageParts, String destinationPath) {
        for(int i = 0; i < imageParts.length; i++) {
            try {
                ImageIOUtil.writeImage(imageParts[i], destinationPath + "\\" + i + ".jpg", 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
