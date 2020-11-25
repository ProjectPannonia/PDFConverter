package service.imagesToPdf;

import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadImagesFromFolder {
    private static int tempPageCount = 0;

    // 1. Main method called -> from controller
    public static void uniteImagesIntoPDF(String imagesRoot) throws IOException {
        int backSlashPosition = imagesRoot.lastIndexOf("\\");

        String tempFilesRoot = imagesRoot.substring(0, backSlashPosition + 1) + "\\temp\\";

        // Temporary folder created?
       createTempFilesFolder(tempFilesRoot);
        // Load original files from disk
        createBImagesFromFolder(imagesRoot, tempFilesRoot);

    }
    // 2. Create a folder to store temporary images
    public static void createTempFilesFolder(String tempFilesRoot) {
        Path path = Paths.get(tempFilesRoot);
        try {
            Files.createDirectories(path);
            System.out.println("Temporary directory is created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 3. Read a complete folder and list it's files -> Cut every image into two
    public static void createBImagesFromFolder(String imagesRoot, String tempFilesRoot) throws IOException {
        // Create files list from images folder
        File[] filesInFolder = new File(imagesRoot).listFiles();

        //System.out.println("Number of originals: " + filesInFolder.length);

        // Iterating through the file list
        for(File file : filesInFolder) {
            // Read every image and cut it into two pieces
            BufferedImage originalImage = ImageIO.read(file);

            cutIntoTwo(originalImage, tempFilesRoot);
            //System.out.println(file + " original ready!");
        }

    }
    // 4. Get an image and cut it into two
    public static void cutIntoTwo(BufferedImage original, String tempFilesRoot) {
        // Original image width
        int width = original.getWidth();
        // Original image height
        int height = original.getHeight();

        // Cut off the first part of the image
        BufferedImage first = original.getSubimage(0, 0, width/2, height);
        // Cut off the second part of the image
        BufferedImage second = original.getSubimage(width/2,0, width/2, height);

        // Write image parts into temporary folder
        writeToTempFolder(new BufferedImage[]{first,second}, tempFilesRoot);
    }
    // 5. Write temp image into temp folder
    public static void writeToTempFolder(BufferedImage[] imageParts, String tempFilesRoot) {
        try {
            String createdFileName;
            for (int i = 0; i <imageParts.length; i++) {
                createdFileName = String.format(tempFilesRoot + "pdf-%d.%s", tempPageCount, "JPG");
                ImageIOUtil.writeImage(imageParts[i], createdFileName, 300);
                ReadImagesFromFolder.tempPageCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
