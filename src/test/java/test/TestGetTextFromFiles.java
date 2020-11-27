package test;

import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;
import service.getTextFromFiles.GetTextFromFiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestGetTextFromFiles {
    @org.junit.jupiter.api.Test
    public void testGetFileFormat() {
        assertEquals("png", GetTextFromFiles.getFileFormat("D:\\image\\image.png"));
        assertEquals("jpg", GetTextFromFiles.getFileFormat("D:\\image\\image.jpg"));
        assertEquals("jpeg", GetTextFromFiles.getFileFormat("D:\\image\\image.jpeg"));
    }
    @Test
    public void testConvertToText() throws TesseractException {
        //GetTextFromFiles.convertToText("D:\\","JPG", "D:\\");
    }
    @Test
    public void testCreateDestinationFile() {
        GetTextFromFiles.createDestinationFile("D:\\PDF converter tests\\testFileFolder","testFile", "pdf");
    }
    @Test
    public void testCutPath() {
        // Wanted -> D:\PDF converter tests
        GetTextFromFiles.cutPath("D:\\PDF converter tests\\testFileFolder");
        //assertEquals("D:\\PDF converter tests", GetTextFromFiles.cutPath("D:\\PDF converter tests\\testFileFolder"));
    }
}
