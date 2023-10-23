package com.example.codegeneratorfx;

import com.example.codegeneratorfx.supportClasses.Code;
import com.example.codegeneratorfx.supportClasses.Lottery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CodeGeneratorFXController implements Initializable {

    Lottery lottery;
    @FXML
    private TableView<Code> codes_tab;

    @FXML
    private TableColumn<Code, Integer> idColumn;

    @FXML
    private TableColumn<Code, String> codeColumn;

    @FXML
    private TableColumn<Code, Boolean> isUsedColumn;

    @FXML
    private TableColumn<Code, Boolean> isWonColumn;

    @FXML
    private TextField lengthOfCodes_tv;

    @FXML
    private TextField qtyOfCodes_tv;

    public CodeGeneratorFXController(Lottery lottery) {
        this.lottery = lottery;
    }

    ObservableList<Code> listOfCodes = FXCollections.observableArrayList();


    @FXML
    public void onButtonClick(ActionEvent event) throws IOException {
        AdministrationPanelScene administrationPanelScene = new AdministrationPanelScene(lottery);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("administration-panel.fxml"));
        loader.setController(administrationPanelScene);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Administration Panel");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onGenerateBtnClick(ActionEvent event) {
        int qtyOfCodes = Integer.parseInt(qtyOfCodes_tv.getText());
        int lengthOfCodes = Integer.parseInt(lengthOfCodes_tv.getText());
        lottery.setCodes(new ArrayList<Code>());
        lottery.generateCodes(qtyOfCodes, lengthOfCodes);
        listOfCodes.clear();
        listOfCodes.addAll(lottery.getCodes());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Codes generated");
        alert.setHeaderText(null);
        alert.setContentText("Twoje kody zostały wygenerowane");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listOfCodes.addAll(lottery.getCodes());
        idColumn.setCellValueFactory(new PropertyValueFactory<Code, Integer>("codeID"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<Code, String>("code"));
        isUsedColumn.setCellValueFactory(new PropertyValueFactory<Code, Boolean>("isUsed"));
        isWonColumn.setCellValueFactory(new PropertyValueFactory<Code, Boolean>("isWinning"));

        codes_tab.setItems(listOfCodes);
    }
}
