package service.panes.pane3;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.*;

public class PdfModifier {
    public static void modifyPdf(String sourceFilePath, String modifiedFileDestination) {
        try {
            readSourceFile(sourceFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void readSourceFile(String sourceFilePath) throws IOException {
        PDDocument sourceDocument = null;
        PDFTextStripper stripper = new GetLinesFromPDF();
        Writer dummy = null;

        try {
            sourceDocument = PDDocument.load(new File(sourceFilePath));
            System.out.println("Source file: " + sourceDocument.getNumberOfPages());
            stripper.setSortByPosition(true);
            stripper.setStartPage(0);
            stripper.setEndPage(sourceDocument.getNumberOfPages());
            dummy = new OutputStreamWriter(new ByteArrayOutputStream());
            stripper.writeText(sourceDocument,dummy);

            /*
            for (String line: stripper.getL) {
                System.out.println(line);
            }
            */

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(sourceDocument != null) {
                try {
                    sourceDocument.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
