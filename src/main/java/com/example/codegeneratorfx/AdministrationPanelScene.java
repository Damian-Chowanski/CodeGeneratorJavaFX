package com.example.codegeneratorfx;

import com.example.codegeneratorfx.supportClasses.Code;
import com.example.codegeneratorfx.supportClasses.Lottery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministrationPanelScene implements Initializable {
    private Lottery lottery;
    private ObservableList<Code> listOfCodes = FXCollections.observableArrayList();


    @FXML
    private Button Change_Btn;

    @FXML
    private TextField IsWon_tf;

    @FXML
    private TableColumn<Code, String> codeColumn;

    @FXML
    private TextField code_tf;

    @FXML
    private TableView<Code> codes_tab;

    @FXML
    private TableColumn<Code, Integer> idColumn;

    @FXML
    private TableColumn<Code, Boolean> isUsedColumn;

    @FXML
    private TextField isUsed_tf;

    @FXML
    private TableColumn<Code, Boolean> isWonColumn;

    public AdministrationPanelScene(Lottery lottery){
        this.lottery = lottery;
        listOfCodes.addAll(lottery.getCodes());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Code, Integer>("codeID"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<Code,String>("code"));
        isUsedColumn.setCellValueFactory(new PropertyValueFactory<Code,Boolean>("isUsed"));
        isWonColumn.setCellValueFactory(new PropertyValueFactory<Code,Boolean>("isWinning"));

        codes_tab.setItems(listOfCodes);
    }

    void rowClicked(MouseEvent event){
        Code clickedCode = codes_tab.getSelectionModel().getSelectedItem();
    }
}
