package service.panes.pane5;

import service.panes.pane5.fileModels.DestinationFile;
import service.panes.pane5.fileModels.SourceFile;
import service.panes.pane5.manipulate.PdfManipulate;
import service.panes.pane5.recognitor.MyOcr;
import service.panes.pdftoimage.DataForImageGeneration;
import service.panes.pdftoimage.PdfToImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class GetTextFromFileRefR {
    private static String destPath = "D:\\PDF converter tests\\conversion\\";
    private static int sourceNumPages;

    public static void convertToText(SourceFile sourceFile, DestinationFile destinationFile) {
        destPath = destinationFile.getPathNameAndFormat();
        PdfToImage.convert(new DataForImageGeneration(sourceFile.getSourcePath(), destPath, false, 600, "jpg"));

        sourceNumPages = sourceFile.numberOfPages();
        PdfManipulate.create(destPath);
        PdfManipulate.addPages(PdfManipulate.load(destPath), sourceNumPages, destPath);
        mainConverter(sourceFile, destinationFile);
    }

    private static void mainConverter(SourceFile sourceFile, DestinationFile destinationFile) {
        if(!sourceFile.isMultipleFiles()) {
            convertOneFile(sourceFile, destinationFile);
        } else {
            //convertMultipleFiles(sourceFile);
        }
    }

    private static void convertOneFile(SourceFile sourceFile, DestinationFile destinationFile) {
        String sourceFormat = sourceFile.getFormat();

        switch (sourceFormat) {
            case "pdf" : readPdf(sourceFile, destinationFile);
                break;
            //case "jpg" : readJpg(sourceFile.getSourcePath());
            //break;
        }
    }

    public static void readPdf(SourceFile sourceFile, DestinationFile destinationFile) {
        String dataPath = "src/main/resources/tessdata/";
        MyOcr ocr = new MyOcr(dataPath, sourceFile.getSourceLanguage(), 1, 1);
        String mDestpath = destPath.substring(0,destPath.lastIndexOf("\\"));
        File file = new File(mDestpath);
        File[] listFiles = file.listFiles();
        Arrays.sort(listFiles);
        System.out.println("Files: " + listFiles.length);
        for (int i = 0; i < listFiles.length; i++) {
            try {
                File image = listFiles[i];
                BufferedImage bim = ImageIO.read(image);
                //BufferedImage nBim = new BufferedImage(bim.getWidth(),bim.getHeight(), BufferedImage.TYPE_INT_ARGB);
                scanText(bim, ocr, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    private static void readPdf(SourceFile sourceFile, DestinationFile destinationFile) {
//        try {
//            String dataPath = "src/main/resources/tessdata/";
//            CreateOcr ocr = new CreateOcr(dataPath, sourceFile.getSourceLanguage(), 1, 1);
//            PDDocument sourceDoc = PDDocument.load(new File(sourceFile.getSourcePath()));
//            PDFRenderer sourceRenderer = new PDFRenderer(sourceDoc);
//            for (int i = 9; i < sourceNumPages; i++) {
//                BufferedImage bim = sourceRenderer.renderImageWithDPI(i, 600, ImageType.RGB);
//                scanText(bim, ocr, i);
//            }
//            sourceDoc.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static void scanText(BufferedImage bim, MyOcr ocr, int page) {
        String text = ocr.textScanner(bim);
        writeToFile(text, page);
    }

    private static void writeToFile(String text, int page) {
        System.out.println(text);
    }

//    private static void writeToFile(String text, int page) {
//
//        try {
//            PDDocument destDoc = PdfManipulate.load(destPath);
//            PDPage blankPage = destDoc.getPage(page);
//            PDPageContentStream contentStream = new PDPageContentStream(destDoc, blankPage);
//            PDFont sans = PDType0Font.load(destDoc, new File("D:\\PDF converter tests\\DejaVuLGCSerif.ttf"));
//            PDResources res = new PDResources();
//            COSName fontName = res.add(sans);
//            contentStream.beginText();
//            contentStream.newLineAtOffset(25, 700);
//            contentStream.setLeading(14.5f);
//            contentStream.setFont(sans, 8);
//            contentStream.showText(text);
//            contentStream.endText();
//            contentStream.close();
//            destDoc.save(destPath);
//            destDoc.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
