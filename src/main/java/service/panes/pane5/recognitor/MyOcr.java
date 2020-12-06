package service.panes.pane5.recognitor;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;

public class MyOcr {
    private Tesseract tesseract;

    public MyOcr(String dataPath, String lang, int pageSegMode, int ocrEngineMode) {
        this.tesseract = new Tesseract();
        this.tesseract.setDatapath(dataPath);
        this.tesseract.setLanguage(lang);
        this.tesseract.setPageSegMode(pageSegMode);
        this.tesseract.setOcrEngineMode(ocrEngineMode);
    }

    public String textScanner(BufferedImage image) {
        String text = null;
        try {
            text = this.tesseract.doOCR(image);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return text;
    }
}
