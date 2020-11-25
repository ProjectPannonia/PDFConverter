package service.modify;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetLinesFromPDF extends PDFTextStripper {
    private static List<String> lines = new ArrayList<String>();
    public GetLinesFromPDF() throws IOException {
    }

    public static List<String> getLines() {
        return lines;
    }

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        lines.add(text);
    }
}
