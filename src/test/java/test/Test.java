package test;


import service.panes.pane4.ReadSourceImages;
import service.panes.pane4.WriteImagesIntoFile;
import service.panes.pane1.PdfToImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test {

    @org.junit.jupiter.api.Test
    public void testGetFileFormat() {

        assertEquals("pdf", ReadSourceImages.getFileFormat("xyz.pdf"));
    }
    @org.junit.jupiter.api.Test
    public void testGetFileName() {
        assertEquals("xyz",ReadSourceImages.getFileName("xyz.pdf"));
    }
    @org.junit.jupiter.api.Test
    public void getFileNamesFromFolder() {
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("D:\\PDF converter tests\\images\\pdf-99.PNG");
        expectedResult.add("D:\\PDF converter tests\\images\\pdf-100.PNG");
        expectedResult.add("D:\\PDF converter tests\\images\\pdf-101.PNG");
        expectedResult.add("D:\\PDF converter tests\\images\\pdf-102.PNG");
        expectedResult.add("D:\\PDF converter tests\\images\\pdf-103.PNG");
        expectedResult.add("D:\\PDF converter tests\\images\\pdf-104.PNG");
       // assertEquals(expectedResult.size(), CreatePdfFile.getFileNamesFromFolder("D:\\PDF converter tests\\images\\").size());

    }
    @org.junit.jupiter.api.Test
    public void testCheckFileNameOperativeness() {
        assertEquals("default", WriteImagesIntoFile.checkFileNameOperativeness("bab.xyz"));
        assertEquals("myFile",WriteImagesIntoFile.checkFileNameOperativeness("myFile"));
        assertEquals("default",WriteImagesIntoFile.checkFileNameOperativeness("b*b"));
        assertEquals("default", WriteImagesIntoFile.checkFileNameOperativeness("au*o"));
        assertEquals("default", WriteImagesIntoFile.checkFileNameOperativeness("a.u*o"));
    }
    @org.junit.jupiter.api.Test
    public void testReadImagesFromFolder() throws IOException {
        //SM_TASKalfa_2552ci_3252ci_4052ci_5052ci_6052ci_Rev4.pdf
        //ReadImagesFromFolder.uniteImagesIntoPDF("D:\\PDF converter tests\\images");
        //ReadImagesFromFolder.uniteImagesIntoPDF("C:\\testfiles\\PDFConverter test files\\images");
    }
    @org.junit.jupiter.api.Test
    public void testGetTempFolderPath() {
        System.out.println(PdfToImage.getTempFolderPath("D:\\PDF converter tests\\images"));
        assertEquals("D:\\PDF converter tests\\temp\\", PdfToImage.getTempFolderPath("D:\\PDF converter tests\\images"));
    }
}
