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
    private static void imageWriter(boolean split, String conversionDestinationPath,PDFRenderer pdfRenderer, int numOfPages, int targetDpi, String targetFormat) {
        if(split) {
            /* -> Get pages from document -> Write pages to disk -> cut whole images into two -> write cutted images to disk */
            createTermoraryThenFinalFiles(conversionDestinationPath,pdfRenderer,targetDpi,targetFormat,numOfPages);
        } else {
            // Write whole images to the destination folder
            createWholeImagesFromDocument(conversionDestinationPath,pdfRenderer,numOfPages,targetDpi,targetFormat);
        }
    }
    private static void createTermoraryThenFinalFiles(String conversionDestPath, PDFRenderer renderer, int targetDpi, String targetFormat, int numOfPages) {
        String tempPath = getTempFolderPath(conversionDestPath);
        createFolder(tempPath);
        for (int i = 0; i < numOfPages; i++) {
            try {
                BufferedImage renderedImage = renderer.renderImageWithDPI(i, targetDpi, ImageType.RGB);
                ImageIOUtil.writeImage(renderedImage, (tempPath + i + "." + targetFormat), targetDpi);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File[] tempImages = new File(tempPath).listFiles();
        writerForFinal(tempImages, conversionDestPath, targetDpi, targetFormat);
    }
    private static void writerForFinal(File[] filesInFolder, String conversionDestinationPath, int targetDpi, String targetFormat) {
        for (int file = 0; file < filesInFolder.length; file++) {
            try {
                String fileName = filesInFolder[file].getName();
                int[] splittedTargetNames = fileNameGenerator(fileName);
                BufferedImage[] images = imageCutter(ImageIO.read(filesInFolder[file]));
                writerForSplittedImages(conversionDestinationPath, images,targetDpi, targetFormat, splittedTargetNames);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    //
    public static void createWholeImagesFromDocument(String destPath, PDFRenderer renderer, int numOfPages, int targetDpi, String targetFormat){
        for (int i = 0; i < numOfPages; i++) {
            try {
                BufferedImage renderedImage = renderer.renderImageWithDPI(i, targetDpi, ImageType.RGB);
                ImageIOUtil.writeImage(renderedImage, (destPath + i + "." + targetFormat), targetDpi);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void createFolder(String conversionDestinationPath) {
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
    public static int[] fileNameGenerator(String fileName) {
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
