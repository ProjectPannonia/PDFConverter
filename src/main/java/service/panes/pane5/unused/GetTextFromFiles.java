package service.panes.pane5.unused;


import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import service.panes.pane5.fileModels.DestinationFile;
import service.panes.pane5.fileModels.SourceFile;

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
            convertOneFile(sourceFile, tesseract, destinationFile);
        } else {
            convertMultipleFiles(sourceFile);
        }
    }
    public static void convertOneFile(SourceFile sourceFile, Tesseract tesseract, DestinationFile destinationFile) {
        String sourceFormat = sourceFile.getFormat();

        switch (sourceFormat) {
            case "pdf" : readPdf(sourceFile, tesseract, destinationFile);
                        break;
            case "jpg" : readJpg(sourceFile.getSourcePath());
                        break;
        }
    }


    private static void readPdf(SourceFile sourceFile, Tesseract tesseract, DestinationFile destinationFile) {
        String destinationFilename = destinationFile.getDestinationFileName();
        String destinationFormat = destinationFile.getDestinationFormat();
        PDPage newPage;
        try {
            // TO-Do
            System.out.println(sourceFile.getSourcePath());
            PDDocument loadedSourcePdf = PDDocument.load(new File(sourceFile.getSourcePath()));
            PDDocument createdDestinationFile = createDestinationFile(sourceFile.getSourcePath(), destinationFilename, destinationFormat, loadedSourcePdf.getNumberOfPages());

            PDFRenderer renderer = new PDFRenderer(loadedSourcePdf);



            tesseract.setPageSegMode(1);
            tesseract.setOcrEngineMode(1);
            for(int page = 0; page < loadedSourcePdf.getNumberOfPages(); page++) {
                PDPage sourcePage = loadedSourcePdf.getPage(page);
                BufferedImage bim = renderer.renderImageWithDPI(page, 300, ImageType.RGB);
                String result = tesseract.doOCR(bim);
                PDPage destPage = createdDestinationFile.getPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(createdDestinationFile, destPage);
                contentStream.beginText();
                contentStream.newLineAtOffset(25, 700);
                contentStream.showText(result);
                contentStream.endText();
                contentStream.close();
                loadedSourcePdf.save(destinationFile.getDestinationPath());
                loadedSourcePdf.close();
                //openedDestFile.addPage(newPage);
                //System.out.println(result);
            }

        } catch (IOException | TesseractException e) {
            e.printStackTrace();
        }
    }
    public static PDDocument createDestinationFile(String destPath, String destFilename, String destFormat, int numOfPages) {
        String destAbsPath = destPath + "\\" + destFilename + "." + destFormat;
        try {
            PDDocument doc = new PDDocument();
            for(int i = 0; i < numOfPages; i++) {
                PDPage blankPage = new PDPage();
                doc.addPage(blankPage);
            }
            doc.save(destAbsPath);
            doc.close();
            return PDDocument.load(new File(destAbsPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void createDestinationFolder(String destPath) {
        Path path = null;
        try {
            path = Paths.get(destPath);
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void readJpg(String sourcePath) {

    }

    private static void convertMultipleFiles(SourceFile sourceFile) {

    }



}
