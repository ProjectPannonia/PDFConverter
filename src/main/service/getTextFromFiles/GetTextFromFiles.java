package main.service.getTextFromFiles;

import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static main.service.getTextFromFiles.ScanedImage.processImg;

public class GetTextFromFiles {
    public static void convertToText(String destinationFolderPath, String destinationFormat, String sourceFilePath) {
        File file = new File("D:\\PDF converter tests\\images\\9.JPG");
        try {
            BufferedImage ipimage = ImageIO.read(file);
            double d = ipimage.getRGB(ipimage.getTileWidth() / 2,ipimage.getTileHeight() / 2);
            // comparing the values
            // and setting new scaling values
            // that are later on used by RescaleOP
            if (d >= -1.4211511E7 && d < -7254228) {
                processImg(ipimage, 3f, -10f);
            }
            else if (d >= -7254228 && d < -2171170) {
                processImg(ipimage, 1.455f, -47f);
            }
            else if (d >= -2171170 && d < -1907998) {
                processImg(ipimage, 1.35f, -10f);
            }
            else if (d >= -1907998 && d < -257) {
                processImg(ipimage, 1.19f, 0.5f);
            }
            else if (d >= -257 && d < -1) {
                processImg(ipimage, 1f, 0.5f);
            }
            else if (d >= -1 && d < 2) {
                processImg(ipimage, 1f, 0.35f);
            }
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
        }

    }
    public static String getFileFormat(String absolutePath) {
        int dotPosition = absolutePath.lastIndexOf(".");
        return absolutePath.substring(dotPosition + 1);
    }

}
