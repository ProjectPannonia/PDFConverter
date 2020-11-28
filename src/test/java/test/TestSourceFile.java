package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import service.getTextFromFiles.fileModels.SourceFile;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TestSourceFile {
    SourceFile singleFile;

    @Before
    public void initialize() {

        singleFile = new SourceFile();
        singleFile.setSourcePath("D:\\PDF converter tests\\testFileFolder\\testFile.pdf");
        singleFile.setMultipleFiles(false);
        singleFile.setSourceLanguage("hun");
    }
    @Test
    public void testGetFileName() {
        System.out.println(singleFile.getFileName());
        assertEquals("testFile.pdf",singleFile.getFileName());
    }
    @Test
    public void testGetFileFormat() {
        System.out.println(singleFile.getFormat());
        assertEquals("pdf",singleFile.getFormat());
    }
    @Test
    public void testGetPath() {
        System.out.println(singleFile.getPath());
        assertEquals("testFile", singleFile.getPath());
    }
    @After
    public void setToNull() {
        singleFile = null;
    }
}
