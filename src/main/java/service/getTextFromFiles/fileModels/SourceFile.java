package service.getTextFromFiles.fileModels;

public class SourceFile {
    private String sourcePath;
    private String sourceLanguage;
    private boolean multipleFiles;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public boolean isMultipleFiles() {
        return multipleFiles;
    }

    public void setMultipleFiles(boolean multipleFiles) {
        this.multipleFiles = multipleFiles;
    }
    public String getFileName() {
        int slashPos = this.sourcePath.lastIndexOf("\\");
        return this.sourcePath.substring(slashPos + 1);
    }
    public String getFormat() {
        int dotPos = getFileName().indexOf(".");
        return getFileName().substring(dotPos + 1).toLowerCase();
    }
    public String getPath() {
        int dotPos = getFileName().indexOf(".");
        return getFileName().substring(0,dotPos);
    }
}
