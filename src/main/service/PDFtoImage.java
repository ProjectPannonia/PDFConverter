package main.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFtoImage {

//    private String sourcePath;
//    private String extension;
//    private String resultPath;
//
//    public PDFtoImage(String sourcePath, String extension, String resultPath) {
//        this.sourcePath = sourcePath;
//        this.extension = extension;
//        this.resultPath = resultPath;
//    }

    public static boolean convertToImages(String sourcePath, String extension, String resultPath) {
        PDDocument sourceFile = readSourceFile(sourcePath);
        converter(sourceFile,resultPath,extension);

        return true;
    }
    private static void converter(PDDocument loadedSourceFile, String resultPath, String extension) {
        PDFRenderer pdfRenderer = new PDFRenderer(loadedSourceFile);
        BufferedImage actualPage;
        for (int page = 0; page < loadedSourceFile.getNumberOfPages(); page++) {
            try {
                actualPage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                ImageIOUtil.writeImage(actualPage, String.format(resultPath, page + 1, extension), 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private static PDDocument readSourceFile(String sourcePath) {
        PDDocument loadedDocument = null;

        try {
            loadedDocument = PDDocument.load(new File(sourcePath));
            //loadedDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedDocument;
    }


}
