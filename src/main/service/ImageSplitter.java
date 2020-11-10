package main.service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageSplitter {
    private static List<BufferedImage> imagesInFolder;

    public static void splitImage(String pathToRawFiles, String conversionDestinationPath) {
        System.out.println("Image splitter,splitImage: " + pathToRawFiles + ", " + conversionDestinationPath);
        readFolder(pathToRawFiles);
        writeImagesToDisk(imagesInFolder,conversionDestinationPath);
    }

    private static void readFolder(String pathToRawFiles) {
        File rawFilesFolder = new File(pathToRawFiles);
        System.out.println("Image splitter, readFolder: " + pathToRawFiles + ", " + rawFilesFolder.getAbsolutePath());
        readFilesFromFolder(rawFilesFolder);
    }
    private static void readFilesFromFolder(File folder) {
        File[] filesInFolder = folder.listFiles();
        imageHalving(filesInFolder);
    }
    private static void imageHalving(File[] wholeImages) {
        imagesInFolder = new ArrayList<>();
        BufferedImage wholeImage;
        BufferedImage[] twoHalf;

        for(int image = 0; image < wholeImages.length; ++image) {
            try {
                wholeImage = ImageIO.read(wholeImages[image]);
                twoHalf = imageDivider(wholeImage);
                imagesInFolder.add(twoHalf[0]);
                imagesInFolder.add(twoHalf[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static BufferedImage[] imageDivider(BufferedImage wholeImage) {
        BufferedImage[] imageParts = new BufferedImage[2];
        int width = wholeImage.getWidth();
        int height = wholeImage.getHeight();
        imageParts[0] = wholeImage.getSubimage(0,0,width/2,height);
        imageParts[1] = wholeImage.getSubimage(width/2,0,width/2,height);

        return imageParts;
    }
    private static void writeImagesToDisk(List<BufferedImage> loadedImages, String conversionDestinationPath) {
        int fileNameCounter = 0;
        File outputFile;

        for(BufferedImage image : loadedImages) {
            try {
                outputFile = new File(conversionDestinationPath + String.valueOf(fileNameCounter) + ".jpg");
                ImageIO.write(loadedImages.get(fileNameCounter), "JPG", outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileNameCounter++;
        }
    }
}
