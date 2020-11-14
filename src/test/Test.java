package test;

import main.service.imagesToPdf.ReadSourceImages;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test {

    @org.junit.jupiter.api.Test
    public void testGetFileFormat() {

        assertEquals("pdf",ReadSourceImages.getFileFormat("xyz.pdf"));
    }

}
