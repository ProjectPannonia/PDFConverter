package main.service.imagesToPdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteImagesIntoFile {
    public static void uniteFilesIntoPdf(String imagesRootPath, String givenFileName, String createdFileDestination, boolean splitWanted) {
        List<String> pathsInFolder = getFileNamesFromFolder(imagesRootPath);
        PDDocument pDoc;
        PDPage pdPage;
            try {
                pDoc = new PDDocument();

                for (int i = 0; i < pathsInFolder.size(); i++) {
                    pdPage = addPage();
                    pDoc.addPage(pdPage);
                    addImageToPage(pDoc, pdPage, pathsInFolder.get(i));
                }
                pDoc.save(createdFileDestination + "\\" + checkFileNameOperativeness(givenFileName) + ".pdf");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static PDPage addPage() {
        return new PDPage(PDRectangle.A4);
    }
    public static void addImageToPage(PDDocument pdDoc, PDPage pdPage, String imageAbsolutePath) throws IOException {
        PDImageXObject pdImage = PDImageXObject.createFromFile(imageAbsolutePath, pdDoc);

        PDPageContentStream contentStream = new PDPageContentStream(pdDoc, pdPage);
        contentStream.drawImage(pdImage, 0, 0, PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight());
        contentStream.close();
    }
    public static List<String> getFileNamesFromFolder(String imagesFolderPath) {
        File[] files = new File(imagesFolderPath).listFiles();
        List<String> paths = new ArrayList<>();

        for(File f : files) {
            paths.add(f.getAbsolutePath());
        }
        return paths;
    }
    public static String checkFileNameOperativeness(String wantedFileName) {

        if(wantedFileName.contains(".") || wantedFileName.contains("*"))
            return "default";

        return wantedFileName;
    }
    /*  -> Get a buffered image from a given PDImageXOBject
        -> cut the image into 2
        -> create 2 new PDImageXOBject from the image parts
     */
    public static PDImageXObject[] cutImageIntoTo(PDImageXObject originalImage) {
        PDImageXObject[] splittedImages = new PDImageXObject[2];
        splittedImages[0] = originalImage;
        splittedImages[1] = originalImage;

        try {
            BufferedImage originalBIMG = originalImage.getImage();
            int width = originalBIMG.getWidth();
            int height = originalBIMG.getHeight();
            BufferedImage first = originalBIMG.getSubimage(0, 0, width/2, height);
            BufferedImage second = originalBIMG.getSubimage(width/2,0, width/2, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splittedImages;
    }

}