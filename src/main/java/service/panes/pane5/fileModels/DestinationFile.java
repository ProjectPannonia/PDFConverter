package service.panes.pane5.fileModels;

public class DestinationFile {
    private String destinationPath;
    private String destinationFormat;
    private String destinationFileName;

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public String getDestinationFormat() {
        return destinationFormat;
    }

    public void setDestinationFormat(String destinationFormat) {
        this.destinationFormat = destinationFormat;
    }

    public String getDestinationFileName() {
        return destinationFileName;
    }

    public void setDestinationFileName(String destinationFileName) {
        this.destinationFileName = destinationFileName;
    }
    public String getPathNameAndFormat() {
        return this.destinationPath + "\\" + this.destinationFileName + "." + this.destinationFormat;
    }
}
