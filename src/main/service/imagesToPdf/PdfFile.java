package main.service.imagesToPdf;

public class PdfFile {
    private int id = 0;
    private String fileName;
    private String format;
    private String path;

    public PdfFile(String fileName, String format, String path) {
        this.id = id++;
        this.fileName = fileName;
        this.format = format;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
