package test;

import net.sourceforge.tess4j.TesseractException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import service.panes.pane5.unused.GetTextFromFiles;

@RunWith(JUnit4.class)
public class TestGetTextFromFiles {

    @org.junit.Test
    public void testConvertToText() throws TesseractException {
        //GetTextFromFiles.convertToText("D:\\","JPG", "D:\\");
    }
    @org.junit.Test
    public void testCreateDestinationFolder() {

    }
    @org.junit.Test
    public void testCreateDestinationFile() {
        GetTextFromFiles.createDestinationFile("D:\\PDF converter tests\\testFileFolder","testFile", "pdf", 3);
    }
}
