package main.service.getTextFromFiles;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

public class ScanedImage {
    public static void processImg(BufferedImage ipimage, float scaleFactor, float offset) throws IOException, TesseractException {
        BufferedImage opimage = new BufferedImage(1050, 1024, ipimage.getType());
        Graphics2D graphics = opimage.createGraphics();
        graphics.drawImage(ipimage, 0, 0, 1050, 1024, null);
        graphics.dispose();
        RescaleOp rescale = new RescaleOp(scaleFactor, offset, null);
        BufferedImage fopimage = rescale.filter(opimage, null);
        ImageIO.write(fopimage, "jpg", new File("D:\\PDF converter tests\\images\\9.JPG"));
        Tesseract it = new Tesseract();
        it.setDatapath("D:\\PDF converter tests\\Tess4J");
        String str = it.doOCR(fopimage);
        System.out.println(str);
    }
}
