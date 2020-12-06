package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import service.panes.pane5.GetTextFromFileRefR;
import service.panes.pane5.fileModels.DestinationFile;
import service.panes.pane5.fileModels.SourceFile;

@RunWith(JUnit4.class)
public class TestGetTextFromFileRefR {
    SourceFile sourceFile;
    DestinationFile destinationFile;

    @Before
    public void init() {
        sourceFile = new SourceFile();
        sourceFile.setSourceLanguage("hun");
        sourceFile.setMultipleFiles(false);
        sourceFile.setSourcePath("D:\\PDF converter tests\\convertedPdf\\conv.pdf");
        destinationFile = new DestinationFile();
        destinationFile.setDestinationFormat("pdf");
        destinationFile.setDestinationFileName("test");
        destinationFile.setDestinationPath("D:\\PDF converter tests\\conversion\\");
    }
//    @Test
//    public void testConvertToText() {
//        GetTextFromFileRefR.convertToText(sourceFile,destinationFile);
//
//    }
    @Test
    public void testReadPdf() {
        destinationFile.setDestinationPath("D:\\PDF converter tests\\conversion");
        GetTextFromFileRefR.readPdf(sourceFile, destinationFile);
    }
}
