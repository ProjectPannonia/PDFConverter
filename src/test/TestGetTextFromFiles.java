package test;

import main.service.getTextFromFiles.GetTextFromFiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGetTextFromFiles {
    @Test
    public void testGetFileFormat() {
        assertEquals("png", GetTextFromFiles.getFileFormat("D:\\image\\image.png"));
        assertEquals("jpg", GetTextFromFiles.getFileFormat("D:\\image\\image.jpg"));
        assertEquals("jpeg", GetTextFromFiles.getFileFormat("D:\\image\\image.jpeg"));
    }
}
