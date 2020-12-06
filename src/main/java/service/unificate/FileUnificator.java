package service.unificate;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import service.general.MyFile;
import service.general.MyPdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUnificator {

    private String sourcePath;
    private String destPath;
    private String destFilename;
    private String destinationAbsolutePath;
    private List<File> sortedFiles;
    private PDDocument destinationDoc;


    public FileUnificator(String sourcePath, String destinationPath, String destinationFilename) {
        this.sourcePath = sourcePath;
        this.destPath = destinationPath;
        this.destFilename = destinationFilename;
    }

    public void uniteImagesIntoPdf() {
        this.destinationAbsolutePath = this.destPath + "\\" + this.destFilename + ".pdf";
        this.sortedFiles = new ArrayList<>();

        createEmptyDocument();
    }
    private void createEmptyDocument() {
        this.destinationAbsolutePath = MyPdf.createDoc(destPath,destFilename);
        addPagesToDocument();
    }
    private void addPagesToDocument() {
        int numOfFilesInFolder = new File(sourcePath).listFiles().length;
        MyPdf.addPages(destinationAbsolutePath, numOfFilesInFolder);
        loadSourceFolder();
    }

    private void loadSourceFolder() {
        File[] filesInSourceFolder = new File(this.sourcePath).listFiles();
        sortSourcesByTheirNumberName(filesInSourceFolder);
    }
    private void sortSourcesByTheirNumberName(File[] unsortedFiles) {
        String filenameWithoutFormat;

        for(int i = 0; i < unsortedFiles.length; i++) {
            for(int x = 0; x < unsortedFiles.length; x++) {
                filenameWithoutFormat = MyFile.giveFilename(unsortedFiles[x].getName());
                if(Integer.valueOf(filenameWithoutFormat) == i) {
                    this.sortedFiles.add(unsortedFiles[x]);
                    break;
                }
            }
        }
        writeToPdf();
    }
    private void writeToPdf() {
        try {
            for (int i = 0; i < sortedFiles.size(); i++) {
                this.destinationDoc = PDDocument.load(new File(this.destinationAbsolutePath));
                File file = sortedFiles.get(i);
                writeToSpecifiedPdfPage(i,file);
            }
            this.destinationDoc = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeToSpecifiedPdfPage(int wantedPage, File fileToWrite) {
        try {
            // Get the blank pd page
            PDPage page = this.destinationDoc.getPage(wantedPage);
            int width = (int) page.getMediaBox().getWidth();
            int height = (int) page.getMediaBox().getHeight();
            // Read image from disk
            PDImageXObject pdImage = PDImageXObject.createFromFile(fileToWrite.getAbsolutePath(), this.destinationDoc);
//            pdImage.setWidth(width);
//            pdImage.setHeight(height);
            PDPageContentStream contentStream = new PDPageContentStream(this.destinationDoc, page);
            contentStream.drawImage(pdImage,0,0, PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight());
            //contentStream.drawXObject(pdImage, 0, 0, pdImage.getWidth(),pdImage.getHeight());
            contentStream.close();
            this.destinationDoc.save(this.destinationAbsolutePath);
            this.destinationDoc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /* Getters
       And
       Setters
     */
    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    public String getDestFilename() {
        return destFilename;
    }

    public void setDestFilename(String destFilename) {
        this.destFilename = destFilename;
    }
}
