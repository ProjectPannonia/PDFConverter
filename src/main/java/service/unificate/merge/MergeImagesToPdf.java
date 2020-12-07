package service.unificate.merge;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MergeImagesToPdf {
    private String existingDocPath;
    private PDDocument existingDoc;
    private List<File> images;

    public MergeImagesToPdf(String existingDocPath) {
        this.existingDocPath = existingDocPath;
    }

    public void mergePdf(String imagesPath) {
        this.images = Arrays.asList(new File(imagesPath).listFiles());
        merge();
    }

    private void merge() {
        try {
            this.existingDoc = PDDocument.load(new File(this.existingDocPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PDDocument getExistingDoc() {
        return existingDoc;
    }

    public void setExistingDoc(PDDocument existingDoc) {
        this.existingDoc = existingDoc;
    }

    public List<File> getImages() {
        return images;
    }

    public void setImages(List<File> images) {
        this.images = images;
    }
}
