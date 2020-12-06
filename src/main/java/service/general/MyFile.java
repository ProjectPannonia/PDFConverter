package service.general;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyFile {
    public static File readFile(String pathToFile) {
        return new File(pathToFile);
    }
    public static File[] listFiles(String pathToFile) {
        return new File(pathToFile).listFiles();
    }
    public static BufferedImage[] listBufferedImages(String pathToFile) {
        File[] files;
        BufferedImage[] bims = null;
        try {
            files = listFiles(pathToFile);
            bims = new BufferedImage[files.length];

            for (int i = 0; i < files.length; i++) {
                bims[i] = ImageIO.read(files[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bims;
    }
    public static String giveFilename(String absFilename) {
        int dotPost = absFilename.lastIndexOf(".");
        return absFilename.substring(0,dotPost);
    }
    public static String giveFormat(String absFilename) {
        int dotPos = absFilename.lastIndexOf(".");
        return absFilename.substring(dotPos + 1);
    }
}
