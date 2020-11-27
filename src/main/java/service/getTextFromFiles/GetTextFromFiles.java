package service.getTextFromFiles;


import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import service.getTextFromFiles.fileModels.DestinationFile;
import service.getTextFromFiles.fileModels.SourceFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetTextFromFiles {
    public static void convertToText(SourceFile sourceFile, DestinationFile destinationFile) throws TesseractException {
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage(sourceFile.getSourceLanguage());
        tesseract.setDatapath("src/main/resources/tessdata/");
        mainConverter(sourceFile, destinationFile, tesseract);

        //String destinationFolderPath, String destinationFormat, String sourceFilePath, String destinationFileName, boolean multipleFiles, String sourceLanguage
        //File image = new File("src/main/resources/img/pdf-1.JPG");

        //Tesseract tesseract = new Tesseract();
//        tesseract.setLanguage(sourceFile.getSourceLanguage());
//        tesseract.setDatapath("src/main/resources/tessdata/");
//
//        if(sourceFile.isMultipleFiles()) {
//            File images = new File(sourceFile.getSourcePath());
//            File[] imagesInFolder = images.listFiles();
//        }else {
//            File image = new File(sourceFile.getSourcePath());
//            oneFileToConvert(image);
//            tesseract.setPageSegMode(1);
//            tesseract.setOcrEngineMode(1);
//            String result = tesseract.doOCR(image);
//            System.out.println(result);
//        }
        //File image = new File(sourceFilePath);
//        Tesseract tesseract = new Tesseract();
//        tesseract.setDatapath("src/main/resources/tessdata/");
//        tesseract.setLanguage("hun");
//        tesseract.setPageSegMode(1);
//        tesseract.setOcrEngineMode(1);
//        String result = tesseract.doOCR(image);
//        System.out.println(result);

    }
    private static void mainConverter(SourceFile sourceFile, DestinationFile destinationFile, Tesseract tesseract) {
        if(!sourceFile.isMultipleFiles()) {
            oneFileToConvert(sourceFile, tesseract, destinationFile);
        } else {
            multipleFileConverter(sourceFile);
        }
    }
    public static void oneFileToConvert(SourceFile sourceFile, Tesseract tesseract, DestinationFile destinationFile) {
        String sourceFormat = getFileFormat(sourceFile.getSourcePath()).toLowerCase();

        switch (sourceFormat) {
            case "pdf" : readPdf(sourceFile.getSourcePath(), tesseract, destinationFile);
                        break;
            case "jpg" : readJpg(sourceFile.getSourcePath());
                        break;
        }
    }

    private static void readJpg(String sourcePath) {

    }

    private static void multipleFileConverter(SourceFile sourceFile) {

    }
    private static void readPdf(String sourcePath, Tesseract tesseract, DestinationFile destinationFile) {
        String destinationPath = destinationFile.getDestinationPath();
        String destinationFilename = destinationFile.getDestinationFileName();
        String destinationFormat = destinationFile.getDestinationFormat();
        try {
            // TO-Do
            File createdDestinationFile = createDestinationFile(destinationPath, destinationFilename, destinationFormat);
            File sourcePdf = new File(sourcePath);
            PDDocument pdDocument = PDDocument.load(sourcePdf);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            tesseract.setPageSegMode(1);
            tesseract.setOcrEngineMode(1);
            for(int page = 0; page < pdDocument.getNumberOfPages(); page++) {
                PDPage actPage = pdDocument.getPage(page);
                BufferedImage bim = renderer.renderImageWithDPI(page, 300, ImageType.RGB);

                String result = tesseract.doOCR(bim);
                System.out.println(result);
            }

        } catch (IOException | TesseractException e) {
            e.printStackTrace();
        }
    }
    public static String getFileFormat(String absolutePath) {
        int dotPosition = absolutePath.lastIndexOf(".");
        return absolutePath.substring(dotPosition + 1);
    }
    public static File createDestinationFile(String destPath, String destFilename, String destFormat) {
        createDestinationFolder(destPath);
        String purePath = cutPath(destPath);
        String fileNameAndFormat = purePath + "\\" + destFilename + "\\." + destFormat;

        return new File(fileNameAndFormat);
    }
    public static void createDestinationFolder(String destPath) {
        try {
            Path path = Paths.get(destPath);
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String cutPath(String destPath) {
        int lastSlashPosition = destPath.lastIndexOf("\\");
        return destPath.substring(0,lastSlashPosition);
    }
}
