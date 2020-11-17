package test;

import main.service.imagesToPdf.ReadSourceImages;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test {

    @org.junit.jupiter.api.Test
    public void testGetFileFormat() {

        assertEquals("pdf",ReadSourceImages.getFileFormat("xyz.pdf"));
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

}
