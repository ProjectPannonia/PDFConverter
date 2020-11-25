package service.modify;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;

public class GetTextFromPDF {
    public static String getText(PDDocument document) {
        String text = null;
        try {
            text = new PDFTextStripper().getText(document);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }
}
