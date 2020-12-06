package service.general;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.File;
import java.io.IOException;

public class MyPdf {

    private static PDDocument doc;
    private static PDPage page;

    public static String createDoc(String path, String name) {
        String res = null;
        doc = new PDDocument();
        try {
            res = path + "\\" + name + ".pdf";
            doc.save(res);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static void addPages(String absPath, int numOfPages) {
        try {
            page = new PDPage(PDRectangle.A4);
            doc = PDDocument.load(new File(absPath));
            for (int i = 0; i < numOfPages; i++) {
                doc.addPage(page);
            }
            doc.save(absPath);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static PDDocument load(String absPath) {
        doc = null;
        try {
            doc = PDDocument.load(new File(absPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
