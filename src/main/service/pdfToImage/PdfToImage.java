package main.service.pdfToImage;

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

    public static void convert(DataForImageGeneration data) {
        System.out.println("Convert method");
        PDDocument pdDocument = openSourceFile(data.getSourceFilePath());
        PDFRenderer sourceRenderer = new PDFRenderer(pdDocument);
        imageWriter(data.isSplit(), data.getDestinationPath(), sourceRenderer, pdDocument.getNumberOfPages(), data.getTargetDpi(), data.getTargetFormat());
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

                for (int i = 0; i < numOfPages; i++) {
                    System.out.println("Image writer method -> for");
                    try {
                        writerForTemporaryImages(tempPath, pdfRenderer.renderImageWithDPI(i,targetDpi, ImageType.RGB), i, targetDpi, targetFormat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                File[] wholeImages = new File(tempPath).listFiles();
                for (int i = 0; i < wholeImages.length; i++) {
                    try {
                        System.out.println("Image writer method -> for -> split");

                        String fileName = wholeImages[i].getName();
                        int[] splittedTargetNames = generateSplittedFileNames(fileName);
                        BufferedImage[] images = imageCutter(ImageIO.read(wholeImages[i]));
                        writerForSplittedImages(conversionDestinationPath, images,targetDpi, targetFormat, splittedTargetNames);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        } else {
            // Write the images to the destination folder
            writerForNormalImage(conversionDestinationPath,pdfRenderer,numOfPages,targetDpi,targetFormat);
        }
    }
    public static void writerForNormalImage(String destPath, PDFRenderer renderer, int numOfPages, int targetDpi, String targetFormat){
        for (int i = 0; i < numOfPages; i++) {
            try {
                BufferedImage renderedImage = renderer.renderImageWithDPI(i, targetDpi, ImageType.RGB);
                ImageIOUtil.writeImage(renderedImage, (destPath + i + "." + targetFormat), targetDpi);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void writerForTemporaryImages(String writeDestPath, BufferedImage image, int counter, int targetDpi, String targetFormat) {
        System.out.println("SpecifiedImageWriter method.");
        // image fileName dpi
        try {
            ImageIOUtil.writeImage(image, (writeDestPath + counter + "." + targetFormat), targetDpi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writerForSplittedImages(String destPath, BufferedImage[] images, int targetDpi, String targetFormat, int[] pagesCounter) {
        for (int i = 0; i < images.length; i++) {
            try {
                    ImageIOUtil.writeImage(images[i], (destPath + pagesCounter[i] + "." + targetFormat), targetDpi);
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
    public static BufferedImage[] imageCutter(BufferedImage wholeImage) {
        BufferedImage[] imageParts = new BufferedImage[2];
        int width = wholeImage.getWidth();
        int height = wholeImage.getHeight();
        imageParts[0] = wholeImage.getSubimage(0, 0, width/2, height);
        imageParts[1] = wholeImage.getSubimage(width/2,0, width/2, height);

        return imageParts;
    }
    public static int[] generateSplittedFileNames(String fileName) {
        int dotPlace = fileName.indexOf(".");
        int pageNumber = Integer.valueOf(fileName.substring(0,dotPlace));
        int[] counters = new int[2];
        if(pageNumber == 0) {
            counters[0] = 0;
            counters[1] = 1;
        } else {
            counters[0] = pageNumber * 2;
            counters[1] = (pageNumber * 2) + 1;
        }
        return counters;
    }
}
