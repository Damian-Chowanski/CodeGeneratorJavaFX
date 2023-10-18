package com.example.codegeneratorfx;

import com.example.codegeneratorfx.supportClasses.Code;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CodeGeneratorFXController implements Initializable {

    @FXML
    private TableView<Code> codes_tab;

    @FXML
    private TableColumn<Code, Integer> idColumn;

    @FXML
    private TableColumn<Code, String> CodeColumn;

    @FXML
    private TableColumn<Code, Boolean> isUsedColumn;

    @FXML
    private TableColumn<Code, Boolean> isWonColumn;

    @FXML
    private TextField lengthOfCodes_tv;

    @FXML
    private TextField qtyOfCodes_tv;

    @FXML
    private Button beginBtn;

    @FXML
    void onButtonClick(ActionEvent event) {

    }
    ObservableList<Code> list = FXCollections.observableArrayList();

    @FXML
    public void onGenerateBtnClick(){

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list.clear();
        idColumn.setCellValueFactory( new PropertyValueFactory<Code, Integer>("codeID"));
        CodeColumn.setCellValueFactory(new PropertyValueFactory<Code,String>("code"));
        isUsedColumn.setCellValueFactory(new PropertyValueFactory<Code,Boolean>("isUsed"));
        isWonColumn.setCellValueFactory(new PropertyValueFactory<Code,Boolean>("isWinning"));

        codes_tab.setItems(list);
    }
}
