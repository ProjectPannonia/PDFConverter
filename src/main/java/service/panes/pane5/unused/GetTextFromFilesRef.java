package service.panes.pane5.unused;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import service.panes.pane5.fileModels.DestinationFile;
import service.panes.pane5.fileModels.SourceFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GetTextFromFilesRef {
    private static String destPath;
    public static void convertToText(SourceFile sourceFile, DestinationFile destinationFile) {
        destPath = destinationFile.getPathNameAndFormat();
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage(sourceFile.getSourceLanguage());
        tesseract.setDatapath("src/main/resources/tessdata/");
        mainConverter(sourceFile, destinationFile, tesseract);
    }

    private static void mainConverter(SourceFile sourceFile, DestinationFile destinationFile, Tesseract tesseract) {
        if(!sourceFile.isMultipleFiles()) {
            convertOneFile(sourceFile, tesseract, destinationFile);
        } else {
            //convertMultipleFiles(sourceFile);
        }
    }

    private static void convertOneFile(SourceFile sourceFile, Tesseract tesseract, DestinationFile destinationFile) {
        String sourceFormat = sourceFile.getFormat();

        switch (sourceFormat) {
            case "pdf" : readPdf(sourceFile, tesseract, destinationFile);
                break;
            //case "jpg" : readJpg(sourceFile.getSourcePath());
                //break;
        }
    }

    private static void readPdf(SourceFile sourceFile, Tesseract tesseract, DestinationFile destinationFile) {
        try {
            PDDocument sourceDoc = PDDocument.load(new File(sourceFile.getSourcePath()));
            createDestinationFile(destinationFile.getPathNameAndFormat(), sourceDoc.getNumberOfPages());
            PDDocument destinationEmptyDoc = PDDocument.load(new File(destinationFile.getPathNameAndFormat()));
            readAndWrite(sourceDoc, destinationEmptyDoc, tesseract);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void createDestinationFile(String absPath, int numOfPages) {
        try {
            PDDocument doc = new PDDocument();
            for(int i = 0; i < numOfPages; i++) {
                PDPage blankPage = new PDPage();
                doc.addPage(blankPage);
            }
            doc.save(absPath);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void readAndWrite(PDDocument source, PDDocument destination, Tesseract tesseract) {
        PDFRenderer renderer = new PDFRenderer(source);
        for (int i = 0; i < source.getNumberOfPages(); i++) {
            ocr(tesseract, i, renderer, destination);
        }

    }
    private static void ocr(Tesseract tesseract, int page, PDFRenderer renderer, PDDocument destination) {

        try {
            tesseract.setPageSegMode(1);
            tesseract.setOcrEngineMode(1);
            BufferedImage bim = renderer.renderImageWithDPI(page, 300, ImageType.RGB);
            writeToDest(destination, tesseract.doOCR(bim), page);
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
        }

    }
    private static void writeToDest(PDDocument destination, String text, int page) {
        try {
            PDPage actual = destination.getPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(destination, actual);
            contentStream.beginText();
            contentStream.newLineAtOffset(25, 700);
            contentStream.setFont(PDType1Font.TIMES_BOLD, 18);
            contentStream.showText(text);
            contentStream.endText();
            contentStream.close();
            destination.save(destPath);
//            destination.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
