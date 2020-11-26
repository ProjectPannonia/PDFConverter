package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import net.sourceforge.tess4j.TesseractException;
import service.getTextFromFiles.GetTextFromFiles;
import service.imagesToPdf.PdfFile;
import service.imagesToPdf.ReadSourceImages;
import service.imagesToPdf.WriteImagesIntoFile;
import service.modify.PdfModifier;
import service.pdfToImage.DataForImageGeneration;
import service.pdfToImage.PdfToImage;
import service.splitImage.ImageSplitter;

import java.io.File;

public class Controller {

    private final ObservableList<String> fileFormats = FXCollections.observableArrayList("JPG","JPEG","PNG","TIF","TIFF","GIF","BMP");
    private final ObservableList<String> targetDpi = FXCollections.observableArrayList("100", "200", "300", "400", "500", "600");
    private ObservableList<PdfFile> sourceFilesForTable = FXCollections.observableArrayList();
    private final ObservableList<String> p5FileFormats = FXCollections.observableArrayList("PDF", "JPG","JPEG","PNG","TIF","TIFF","GIF","BMP");
    private final ObservableList<String> p5DestFileFormats = FXCollections.observableArrayList("DOC", "DOCX","TXT");
    private final ObservableList<String> p5SourceLanguage = FXCollections.observableArrayList("ENG","HUN");
    // 1.
    @FXML
    Button  P1ConvertButton,
            P1ChooseDestinationFolderBtn;
    @FXML
    Label   P1OriginalPDFFilePathLabel,
            P1ChooseDestinationFolderLb;
    @FXML
    ChoiceBox   P1DestinationFormat,
                P1TargetDpi;
    @FXML
    CheckBox P1SplitImageCb;

    // 2.
    @FXML
    Button  P2ChooseMultipleFilesBtn,
            P2SplitImage,
            P2SelectConversionPath;
    @FXML
    Label   P2ChooseSourceFolderLb,
            P2ChooseDestinationPath;

    // 3.
    @FXML
    Button  P3ChooseSourcePDF,
            P3ChooseModifiedFileFolder,
            P3ModifyFile;
    @FXML
    Label   P3PathToSourcePDFTf,
            P3PathForModifiedPDFTf;

    // 4.
    @FXML
    Button  P4ChooseImagesSourceFolderBt,
            P4ChooseDestinationFolderBtn,
            P4ConvertButton,
            QuitBt;
    @FXML
    Label   P4SourceImagesPathLb,
            P4ChooseDestinationFolderLb;
    
    @FXML
    TableView<PdfFile> P4SourceImagesTable = new TableView<PdfFile>();

    @FXML
    TableColumn<Object, Object> P4SourceImageId;
    @FXML
    TableColumn<Object, Object> P4SourceImageName;
    @FXML
    TableColumn<Object, Object> P4SourceImageFormat;
    @FXML
    TableColumn<Object, Object> P4SourceImagePath;
    @FXML
    TableColumn<PdfFile,Boolean> P4SourceImageSelectCol;

    @FXML
    TextField   P4ConvertedFileName;
    @FXML
    ChoiceBox P4TargetDpi;
    @FXML
    CheckBox P4SplitImagesCb;

    // 5.

    @FXML
    Button  P5BrowseSourceFile,
            P5BrowseDestinationPathBt,
            P5ConvertButton;
    @FXML
    ChoiceBox   P5DestFileFormatCb,
                P5SourceLanguageCb,
                P5SourceFilesAmountCb;
    @FXML
    Label   P5DestinationPathLb,
            P5SourceFilePathLb;
    @FXML
    TextField P5DestinationFileNameTf;

    @FXML
    public void initialize() {
        P1DestinationFormat.setItems(fileFormats);
        P1TargetDpi.setItems(targetDpi);
        P4TargetDpi.setItems(targetDpi);
        //P5FileFormatCb.setItems(p5FileFormats);
        P5DestFileFormatCb.setItems(p5DestFileFormats);
        P4SourceImagesTable.setEditable(true);
        P5SourceLanguageCb.setItems(p5SourceLanguage);
        P4SourceImageId.setCellValueFactory( new PropertyValueFactory<>("id"));
        P4SourceImageName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        P4SourceImageFormat.setCellValueFactory(new PropertyValueFactory<>("format"));
        P4SourceImagePath.setCellValueFactory(new PropertyValueFactory<>("path"));
        //P4SourceImageSelectCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        P4SourceImagesTable.setItems(sourceFilesForTable);
    }

    // 1. PDF TO IMAGE
    @FXML
    public void p1ChooseSourcePDF(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files","*.pdf"));
        File f = fc.showOpenDialog(null);
        if(f != null) {
            P1OriginalPDFFilePathLabel.setText(f.getAbsolutePath());
        }
    }
    @FXML
    public void p1ChooseConversionDestinationFolder(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            String destinationFolderPath = directory.getAbsolutePath();
            P1ChooseDestinationFolderLb.setText(destinationFolderPath);
        }
    }
    @FXML
    public void p1Convert(ActionEvent e) {
        String pathToPDFFile = P1OriginalPDFFilePathLabel.getText();
        String pathToConversionDestination = P1ChooseDestinationFolderLb.getText() + "\\";
        boolean splitChecked = P1SplitImageCb.isSelected();
        int destinationDpi = Integer.valueOf(P1TargetDpi.getValue().toString());
        String destinationFormat = P1DestinationFormat.getValue().toString();

        if (pathToPDFFile != null && pathToPDFFile != "" && pathToConversionDestination != null && pathToConversionDestination != "") {
            DataForImageGeneration data = new DataForImageGeneration(pathToPDFFile, pathToConversionDestination, splitChecked, destinationDpi, destinationFormat);
            PdfToImage.convert(data);
        }
    }


    // 2. SPLIT IMAGES
    @FXML
    public void p2ChooseSourceImagesFolder(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        String pathToDirectory = null;

        if(directory != null) {
            pathToDirectory = directory.getAbsolutePath() + "\\";
            P2ChooseSourceFolderLb.setText(pathToDirectory);
        }
    }
    @FXML
    public void p2ChooseConversionDestinationFolder(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File direcotry = directoryChooser.showDialog(null);
        String pathToDirectory = null;

        if (direcotry != null) {
            pathToDirectory = direcotry.getAbsolutePath() + "\\";
            P2ChooseDestinationPath.setText(pathToDirectory);

        }
    }
    @FXML
    public void p2SplitImagesBtn(ActionEvent e) {
        String pathToFile = P2ChooseSourceFolderLb.getText();
        String conversionDestinationPath = P2ChooseDestinationPath.getText();
        if (pathToFile != null) {
            ImageSplitter.splitImage(pathToFile,conversionDestinationPath);
        }

    }

    // 3. PDF modifier
    @FXML
    private void p3ChooseSourcePDF(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files","*.pdf"));
        File file = fileChooser.showOpenDialog(null);

        if( file != null) {
            P3PathToSourcePDFTf.setText(file.getAbsolutePath());
        }
    }
    @FXML
    private void setP3ChooseModifiedFileFolder(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File direcotry = directoryChooser.showDialog(null);
        String pathToDirectory = null;

        if (direcotry != null) {
            pathToDirectory = direcotry.getAbsolutePath() + "\\";
            System.out.println(pathToDirectory);
            P3PathForModifiedPDFTf.setText(pathToDirectory);

        }
    }
    @FXML
    public void p3ModifyThisFile(ActionEvent e) {
        String pathToSourceFile = P3PathToSourcePDFTf.getText();
        String modifiedFileDestinationPath = P3PathForModifiedPDFTf.getText();
        System.out.println("From p3ModifyThisFIle: " + pathToSourceFile + ", " + modifiedFileDestinationPath);
        PdfModifier.modifyPdf(pathToSourceFile,modifiedFileDestinationPath);
    }

    // 4. Images to PDF
    @FXML
    public void p4BrowseFiles(ActionEvent e) {
        String sourceFilesPath = null;
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            sourceFilesPath = directory.getAbsolutePath();
            P4SourceImagesPathLb.setText(sourceFilesPath);
            ObservableList<PdfFile>  files = ReadSourceImages.readSourceFiles(sourceFilesPath);
            sourceFilesForTable.addAll(files);
        }
    }
    @FXML
    public void p4ChooseConversionDestination(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            String destinationFolderPath = directory.getAbsolutePath();
            P4ChooseDestinationFolderLb.setText(destinationFolderPath);
        }
    }
    @FXML
    public void p4Convert(ActionEvent e) {
        String imagesPath = P4SourceImagesPathLb.getText();
        String destinationFileName = P4ConvertedFileName.getText();
        String destinationPath = P4ChooseDestinationFolderLb.getText();
        boolean splitImages = P4SplitImagesCb.isSelected();

        WriteImagesIntoFile.uniteFilesIntoPdf(imagesPath,destinationFileName,destinationPath, splitImages);
    }

    // 5.
    @FXML
    public void p5BrowseFiles(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("*","*"));
        File file = fc.showOpenDialog(null);
        String sourceFilePath = file.getAbsolutePath();

        if(file != null && sourceFilePath != null) {
            P5SourceFilePathLb.setText(sourceFilePath);
        }
    }
    @FXML
    public void p5BrowseDestFolder(ActionEvent e) {
        //String destinationFormat = P5DestFileFormatCb.getValue().toString();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        if(directory != null) {
            P5DestinationPathLb.setText(directory.getAbsolutePath());
        }
    }
    @FXML
    public void p5Convert(ActionEvent e) {
        String sourceFilePath = P5SourceFilePathLb.getText();
        String destinationFolderPath = P5DestinationPathLb.getText();
        String destinationFormat = P5DestFileFormatCb.getValue().toString();
        String destinationFileName = P5DestinationFileNameTf.getText();
        try {
            GetTextFromFiles.convertToText(destinationFolderPath, destinationFormat, sourceFilePath,destinationFileName);
        } catch (TesseractException tesseractException) {
            tesseractException.printStackTrace();
        }
    }
    @FXML
    public void quit(ActionEvent e) {
        Platform.exit();
    }
}