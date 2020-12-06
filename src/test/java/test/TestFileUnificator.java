package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import service.unificate.FileUnificator;

@RunWith(JUnit4.class)
public class TestFileUnificator {
    FileUnificator testUnificator;

    @Before
    public void init() {
        testUnificator = new FileUnificator("D:\\PDF converter tests\\images\\Tiszta kód\\1\\","D:\\PDF converter tests\\convertedPdf","test");
    }
    @Test
    public void testUniteImagesIntoPdf() {
        testUnificator.uniteImagesIntoPdf();
    }
    @After
    public void toNull() {
        testUnificator = null;
    }
}
