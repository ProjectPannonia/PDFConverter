package main.service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSplitter {

    public static void imageSplit(String pathToContainFolder) {
        File folder = new File(pathToContainFolder);
        File[] files = folder.listFiles();
        BufferedImage[] imagesFromFolder = cutImagesIntoTwo(files);
        writeFilesToDisk(imagesFromFolder);
    }

    private static void writeFilesToDisk(BufferedImage[] images) {
        int fileNameCounter = 0;
        File outPut;

        for (int i = 0; i < images.length; i++) {
            outPut = new File(String.valueOf(fileNameCounter) + ".jpg");
            fileNameCounter++;
            try {
                ImageIO.write(images[i],"jpg",outPut);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static File[] readWholeFolder(File folder) {
        File[] files = folder.listFiles();
        return files;
    }
    private static BufferedImage[] cutImagesIntoTwo(File[] files) {
        File[] originalImages = files;
        BufferedImage[] convertedImages = new BufferedImage[originalImages.length * 2];

        BufferedImage actualWholeImage;
        BufferedImage actualFirstHalf;
        BufferedImage actualSecondHalf;

        int width;
        int height;
        int convertedCounter = 0;
        for (int i = 0; i < originalImages.length; i++) {
            try {
                actualWholeImage = ImageIO.read(originalImages[i]);
                width = actualWholeImage.getWidth();
                height = actualWholeImage.getHeight();
                actualFirstHalf = actualWholeImage.getSubimage(0,0,width/2,height);
                convertedImages[convertedCounter] = actualFirstHalf;
                convertedCounter++;
                actualSecondHalf = actualWholeImage.getSubimage(width/2,0,width/2,height);
                convertedImages[convertedCounter] = actualSecondHalf;
                convertedCounter++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return convertedImages;
    }
    private static BufferedImage readImage(String url){
        BufferedImage source = null;
        int width;
        int height;
        BufferedImage firstSide = null;
        BufferedImage secondSide;

        try {
            source = ImageIO.read(new File(url));
            width = source.getWidth();
            height = source.getHeight();
            System.out.println("Width: " + width + ", height: " + height);
            firstSide = source.getSubimage(0,0,width/2,height);
            File output = new File("firstSide.jpg");
            ImageIO.write(firstSide,"jpg",output);
            //BufferedWriter writer = new BufferedWriter(new FileWriter(firstSide));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firstSide;
    }
}
