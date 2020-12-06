package service.panes.pane5.manipulate;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

public class PdfManipulate {
    public static void create(String path) {
        try {
            PDDocument doc = new PDDocument();
            doc.save(path);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static PDDocument load(String path) {
        PDDocument doc = null;
        try {
            doc = PDDocument.load(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    public static PDDocument addPages(PDDocument doc, int numOfPages, String path) {
        PDPage blank = null;
        try {
            for (int i = 0; i < numOfPages; i++) {
                blank = new PDPage();
                doc.addPage(blank);
            }
            doc.save(path);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    public static void deletePage(String path,int index) {
        try {
            PDDocument doc = load(path);
            doc.removePage(index);
            doc.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
