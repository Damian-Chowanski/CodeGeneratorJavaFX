package com.example.codegeneratorfx;

import com.example.codegeneratorfx.supportClasses.Code;
import com.example.codegeneratorfx.supportClasses.Lottery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.CookieHandler;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AdministrationPanelScene implements Initializable {
    private final Lottery lottery;
    private final ObservableList<Code> listOfCodes = FXCollections.observableArrayList();


    @FXML
    private Button change_Btn;


    @FXML
    private TextField isWon_tf;

    @FXML
    private TextField code_tf;

    @FXML
    private TextField codeId_tf;

    @FXML
    private TextField isUsed_tf;

    @FXML
    private TableView<Code> codes_tab;

    @FXML
    private TableColumn<Code, String> codeColumn;

    @FXML
    private TableColumn<Code, Integer> idColumn;

    @FXML
    private TableColumn<Code, Boolean> isUsedColumn;

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

        codes_tab.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Code selectedCode = codes_tab.getSelectionModel().getSelectedItem();
                if (selectedCode != null) {
                    codeId_tf.setText(String.valueOf(selectedCode.getCodeID()));
                    code_tf.setText(selectedCode.getCode());
                    isUsed_tf.setText(String.valueOf(selectedCode.getIsUsed()));
                    isWon_tf.setText(String.valueOf(selectedCode.getIsWinning()));
                }
            }
        });
    }
    @FXML
    public void onChangeBtnClick(ActionEvent event){

        int currentCodeId = Integer.parseInt(codeId_tf.getText());

        for (Code code: lottery.getCodes()){
            if (code.getCodeID() == currentCodeId){
                code.setCode(code_tf.getText());
                code.setUsed(isUsed_tf.getText().equals("true"));
                code.setWinning(isWon_tf.getText().equals("true"));
                listOfCodes.clear();
                listOfCodes.addAll(lottery.getCodes());
                codes_tab.refresh();
                break;
            }
        }
    }

    @FXML
    public void onAddBtnClick(ActionEvent event){
        ArrayList<Code> actualListOfCOdes = lottery.getCodes();
        Code newCode = new Code(actualListOfCOdes.size()+1,code_tf.getText(), isUsed_tf.getText().equalsIgnoreCase("true"), isWon_tf.getText().equalsIgnoreCase("true"));
        actualListOfCOdes.add(newCode);
        lottery.setCodes(actualListOfCOdes);
        listOfCodes.clear();
        listOfCodes.addAll(lottery.getCodes());
        codes_tab.refresh();
    }

    @FXML
    public void onRemoveBtnClick(ActionEvent event){

        int currentCodeId = Integer.parseInt(codeId_tf.getText());

        ArrayList<Code> actualListOfCodes = lottery.getCodes();
        actualListOfCodes.remove(currentCodeId-1);
        for (int i = currentCodeId; i <actualListOfCodes.size()+1; i++){
            actualListOfCodes.get(i-1).setCodeID(i);
        }

        lottery.setCodes(actualListOfCodes);
        listOfCodes.clear();
        listOfCodes.addAll(lottery.getCodes());
        codes_tab.refresh();
    }
}
