package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdatedController implements Initializable {

    @FXML
    private AnchorPane pnMain;

    @FXML
    private VBox vbMenuBackground;

    @FXML
    private Pane pnLogoBackground;

    /*** Main-menu buttons ***/
    @FXML
    private Button btPdfToImage, btImageSplitter, btMergeIntoPDF, btReadTextFromImages, btExit;

    /*** Content panes ***/
    @FXML
    private GridPane gridpanePdfToImage, gridpaneImageSplitter, gridpaneMergeImagesIntoPDF, gridpaneReadTextFromImages;



    /*** P1 -> PDF to images ***/
    /** buttons **/
    @FXML
    private Button btP1ChoosePDFPath, btP1ChooseDestinationFolder, btP1Convert;
    /** text fields **/
    @FXML
    private TextField tfP1PDFPath, tfP1DestinationPath;
    /** choice boxes **/
    @FXML
    private ChoiceBox<?> cbP1DestinationFormat, cbP1DestinationDPI;
    /** checkbox **/
    @FXML
    private CheckBox cbP1SplitImage;

    /*** P2 -> Image splitter ***/

    /*** P3 -> Merge images into pdf ***/

    /*** P4 -> Read text from images ***/

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void handleClicks(ActionEvent event) {
        if(event.getSource() == btPdfToImage) {
            gridpanePdfToImage.toFront();
        } else if(event.getSource() == btImageSplitter) {
            gridpaneImageSplitter.toFront();
        } else if(event.getSource() == btMergeIntoPDF) {
            gridpaneMergeImagesIntoPDF.toFront();
        } else if(event.getSource() == btReadTextFromImages) {
            gridpaneReadTextFromImages.toFront();
        } else if(event.getSource() == btExit) {
            Platform.exit();
        }
    }

}
