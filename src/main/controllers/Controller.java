package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import main.service.imagesToPdf.PdfFile;
import main.service.imagesToPdf.ReadSourceImages;
import main.service.modify.PdfModifier;
import main.service.pdfToImage.PDFtoImage;
import main.service.splitImage.ImageSplitter;

import java.io.File;

public class Controller {

    private final ObservableList<String> fileFormats = FXCollections.observableArrayList("JPG","PNG","TIF");
    private final ObservableList<String> targetDpi = FXCollections.observableArrayList("100", "200", "300", "400", "500", "600");
    private ObservableList<PdfFile> sourceFilesForTable = FXCollections.observableArrayList(new PdfFile("bab.pdf","pdf","D:\\xyz"),new PdfFile("krumpli.pdf","pdf","D:\\xyz"));

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
            P4ConvertButton;
    @FXML
    Label   P4SourceImagesPathTf,
            P4ChooseDestinationFolderLb;
    private TableView<PdfFile> table = new TableView<PdfFile>();
    @FXML
    TableView P4SourceImagesTable;
//    @FXML
//    TableColumn P4SourceImageId,
//                P4SourceImageName,
//                P4SourceImageFormat,
//                P4SourceImagePath;
    @FXML
    ChoiceBox P4DestinationFormat;




    @FXML
    public void initialize() {
        P1DestinationFormat.setItems(fileFormats);
        P1TargetDpi.setItems(targetDpi);
        P4SourceImagesTable = new TableView<PdfFile>();
        P4SourceImagesTable.setEditable(true);
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn fileName = new TableColumn("File name");
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        TableColumn format = new TableColumn("Format");
        format.setCellValueFactory(new PropertyValueFactory<>("format"));
        TableColumn path = new TableColumn("Path");
        path.setCellValueFactory(new PropertyValueFactory<>("path"));

        P4SourceImagesTable.setItems(sourceFilesForTable);
        P4SourceImagesTable.getColumns().addAll(id,fileName,format,path);
        System.out.println(P4SourceImagesTable.getColumns());
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
        String destinationFormat = P1DestinationFormat.getValue().toString();
        int destinationDpi = Integer.valueOf(P1TargetDpi.getValue().toString());

        if (pathToPDFFile != null && pathToPDFFile != "" && pathToConversionDestination != null && pathToConversionDestination != "") {
            System.out.println(pathToPDFFile + ", " + pathToConversionDestination);
            PDFtoImage.convertToImages(pathToPDFFile,destinationFormat,pathToConversionDestination, destinationDpi);
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
            P1ChooseDestinationFolderLb.setText(pathToDirectory);

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
    public void p4SourceImages(ActionEvent e) {
        //P4SourceImagesTable.setEditable(true);
        //P4SourceImagesTable.getColumns().addAll(P4SourceImageId,P4SourceImageName,P4SourceImageFormat,P4SourceImagePath);
        String sourceFilesPath = null;
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            sourceFilesPath = directory.getAbsolutePath();
            System.out.println("Files folder path: " + sourceFilesPath);
            P4SourceImagesPathTf.setText(sourceFilesPath);
            sourceFilesForTable = ReadSourceImages.readSourceFiles(sourceFilesPath);
            for(PdfFile file : sourceFilesForTable) {
                System.out.println("Source files for table: " + file.getId() + ", " + file.getFileName() + ", " + file.getFormat() + ", " + file.getPath());
                //P4SourceImagesTable.getColumns().add(sourceFilesForTable);
//                P4SourceImageId.setCellFactory(new PropertyValueFactory<PdfFile,String>("id"));
//                P4SourceImageId.setCellFactory(new PropertyValueFactory<PdfFile,String>("fileName"));
//                P4SourceImageId.setCellFactory(new PropertyValueFactory<PdfFile,String>("format"));
//                P4SourceImageId.setCellFactory(new PropertyValueFactory<PdfFile,String>("path"));
                //P4SourceImagesTable.setItems(sourceFilesForTable);
            }

        }
    }
}
