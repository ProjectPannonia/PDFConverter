package main.service.imagesToPdf;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PdfFile {
    private SimpleIntegerProperty id;
    private SimpleStringProperty fileName;
    private SimpleStringProperty format;
    private SimpleStringProperty path;

    public PdfFile(Integer id, String fileName, String format, String path) {
        this.id = new SimpleIntegerProperty(id);
        this.fileName = new SimpleStringProperty(fileName);
        this.format = new SimpleStringProperty(format);
        this.path = new SimpleStringProperty(path);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFileName() {
        return fileName.get();
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public String getFormat() {
        return format.get();
    }

    public SimpleStringProperty formatProperty() {
        return format;
    }

    public void setFormat(String format) {
        this.format.set(format);
    }

    public String getPath() {
        return path.get();
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }
}
