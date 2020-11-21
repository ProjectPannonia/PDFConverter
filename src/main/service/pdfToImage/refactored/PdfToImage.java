package main.service.pdfToImage.refactored;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfToImage {
    static int pageCounter = 0;

    public static void convert(String sourceFilePath, String conversionDestinationPath, boolean split, int targetDpi, String targetFormat) {
        System.out.println("Convert method");
        PDDocument pdDocument = openSourceFile(sourceFilePath);
        PDFRenderer sourceRenderer = new PDFRenderer(pdDocument);
        imageWriter(split, conversionDestinationPath, sourceRenderer, pdDocument.getNumberOfPages(), targetDpi, targetFormat);
    }
    public static PDDocument openSourceFile(String sourceFilePath) {
        System.out.println("OpenSourceFile method");
        PDDocument openedFile = null;
        try {
            openedFile = PDDocument.load(new File(sourceFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return openedFile;
    }
    public static void imageWriter(boolean split, String conversionDestinationPath,PDFRenderer pdfRenderer, int numOfPages, int targetDpi, String targetFormat) {
        System.out.println("Image writer method");
        if(split) {
            System.out.println("Image writer method -> split");
            /* Cut image into two then write    -> create a temp folder for whole images
                                                -> cut the whole images into two
                                                -> write to the real destination folder */
            String tempPath = getTempFolderPath(conversionDestinationPath);
            createFolder(tempPath);
            synchronized (PdfToImage.class) {
                for (int i = 0; i < numOfPages; i++) {
                    System.out.println("Image writer method -> for");
                    try {
                        specifiedImageWriter(tempPath, pdfRenderer.renderImageWithDPI(i,targetDpi, ImageType.RGB), i, targetDpi, targetFormat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized (PdfToImage.class) {
                File[] wholeImages = new File(tempPath).listFiles();
                for (int i = 0; i < wholeImages.length; i++) {
                    try {
                        System.out.println("Image writer method -> for -> split");
                        //ImageIO.read(wholeImages[i]);
                        BufferedImage[] images = imageCutter(ImageIO.read(wholeImages[i]));
                        imagePartsWriter(conversionDestinationPath, images,targetDpi, targetFormat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            // Write the images to the destination folder
        }
    }
    public /*synchronized*/ static void specifiedImageWriter(String writeDestPath, BufferedImage image, int counter, int targetDpi, String targetFormat) {
        System.out.println("SpecifiedImageWriter method.");
        // image fileName dpi
        try {
            ImageIOUtil.writeImage(image, (writeDestPath + counter + "." + targetFormat), targetDpi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public /*synchronized*/ static void imagePartsWriter(String destPath, BufferedImage[] images, int targetDpi, String targetFormat) {
        for (int i = 0; i < images.length; i++) {
            try {
                synchronized (PdfToImage.class) {
                    ImageIOUtil.writeImage(images[i], (destPath + pageCounter + "." + targetFormat), targetDpi);
                }
                ++pageCounter;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void createFolder(String conversionDestinationPath) {
        System.out.println("CreateFolder method");
        Path destinationPath = Paths.get(conversionDestinationPath);
        try {
            Files.createDirectory(destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getTempFolderPath(String conversionDestination) {
        System.out.println("GetTempFolderPath method:");
        int slashIndex = conversionDestination.lastIndexOf("\\");
        return conversionDestination.substring(0, slashIndex).concat("\\temp\\");
    }
    public /*synchronized*/ static BufferedImage[] imageCutter(BufferedImage wholeImage) {
        BufferedImage[] imageParts = new BufferedImage[2];
        int width = wholeImage.getWidth();
        int height = wholeImage.getHeight();
        imageParts[0] = wholeImage.getSubimage(0, 0, width/2, height);
        imageParts[1] = wholeImage.getSubimage(width/2,0, width/2, height);

        return imageParts;
    }
}
