package main.service.pdfToImage;

public class DataForImageGeneration {
    private String sourceFilePath;
    private String destinationPath;
    private boolean split;
    private int targetDpi;
    private String targetFormat;

    public DataForImageGeneration(String sourceFilePath, String destinationPath, boolean split, int targetDpi, String targetFormat) {
        this.sourceFilePath = sourceFilePath;
        this.destinationPath = destinationPath;
        this.split = split;
        this.targetDpi = targetDpi;
        this.targetFormat = targetFormat;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public int getTargetDpi() {
        return targetDpi;
    }

    public void setTargetDpi(int targetDpi) {
        this.targetDpi = targetDpi;
    }

    public String getTargetFormat() {
        return targetFormat;
    }

    public void setTargetFormat(String targetFormat) {
        this.targetFormat = targetFormat;
    }
}
