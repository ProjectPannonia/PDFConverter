package service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToImage  {

    private String pathToPdfFile;
    private String pathToDestination;
    private String destinationExtension;
    private boolean writeReady;
    private boolean writingInProgress;
    private List<BufferedImage> bufferedImages;

    public ToImage(String pathToPdfFile, String pathToDestination, String destinationExtension) {
        this.pathToPdfFile = pathToPdfFile;
        this.pathToDestination = pathToDestination;
        this.destinationExtension = destinationExtension;
        this.writeReady = false;
        this.writingInProgress = true;
        this.bufferedImages = new ArrayList<>();
    }

    public synchronized void convertToImage() {
        converter();
        writingInProgress = false;
        notifyAll();
    }
    public synchronized String checkWritingSuccessfulness() {

        while (writingInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writeReady = true;
        notifyAll();

        return isWritingSuccesfully();
    }

    private String isWritingSuccesfully() {
        File destinationFolder = new File(this.pathToDestination);
        File[] filesInDestinationFolder = destinationFolder.listFiles();
        return filesInDestinationFolder.length != 0 ? "Converted" : "Conversion problem";
    }

    private void converter() {
        //File destinationFolder = createDestinationFolder(this.pathToDestination);
        PDDocument pdfFile = readSourceFile(this.pathToPdfFile);
        PDFRenderer renderedPdf = createdPdfRendererFromFile(pdfFile);
        createBufferedImages(pdfFile,renderedPdf);
        writeToDisk();
    }
    private void writeToDisk() {
        BufferedImage actual;
        File destinationFolder = createDestinationFolder(this.pathToDestination);
        for (int page = 0; page < bufferedImages.size(); page++) {
            try {
                actual = bufferedImages.get(page);
                ImageIOUtil.writeImage(actual,String.format(pathToDestination, page + 1,destinationExtension),300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void createBufferedImages(PDDocument pdDocument,PDFRenderer pdfRenderer) {
        BufferedImage actual;
        for (int page = 0; page < pdDocument.getNumberOfPages(); page++) {
            try {
                actual = pdfRenderer.renderImageWithDPI(page,300,ImageType.RGB);
                bufferedImages.add(actual);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            pdDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private PDFRenderer createdPdfRendererFromFile(PDDocument pdDocument) {
        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
        return pdfRenderer;
    }
    private PDDocument readSourceFile(String sourcePath){
        PDDocument loadedPdfFile = null;
        try {
            loadedPdfFile = PDDocument.load(new File(sourcePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedPdfFile;
    }
    private File createDestinationFolder(String destinationFolder) {
        return new File(destinationFolder);
    }
}
