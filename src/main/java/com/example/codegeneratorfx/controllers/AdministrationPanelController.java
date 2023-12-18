package com.example.codegeneratorfx.controllers;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdministrationPanelController implements Initializable {

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

    private final Lottery lottery;
    private final ObservableList<Code> listOfCodes = FXCollections.observableArrayList();


    public AdministrationPanelController(Lottery lottery) {
        this.lottery = lottery;
        listOfCodes.addAll(lottery.getCodes());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Code, Integer>("codeID"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<Code, String>("code"));
        isUsedColumn.setCellValueFactory(new PropertyValueFactory<Code, Boolean>("isUsed"));
        isWonColumn.setCellValueFactory(new PropertyValueFactory<Code, Boolean>("isWinning"));

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
    public void onChangeBtnClick(ActionEvent event) {
        int codeLength = listOfCodes.get(0).getCode().length();
        String tempCode = code_tf.getText();
        if (tempCode.length() == codeLength) {
            boolean tempIsUsed = isUsed_tf.getText().equals("true");
            boolean tempIsWon = isWon_tf.getText().equals("true");

            int currentCodeId = Integer.parseInt(codeId_tf.getText());

            for (Code code : lottery.getCodes()) {
                if (code.getCodeID() == currentCodeId) {
                    code.setCode(tempCode);
                    code.setUsed(tempIsUsed);
                    code.setWinning(tempIsWon);
                    listOfCodes.clear();
                    listOfCodes.addAll(lottery.getCodes());
                    codes_tab.refresh();
                    break;
                }
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Zmiana kodu");
            alert.setHeaderText(null);
            alert.setContentText("Wybrany przez Ciebie kod został zmieniony\n" +
                    "kod: " + tempCode + "\n" +
                    "Czy użyty: " + tempIsUsed + "\n" +
                    "Czy zwycieski: " + tempIsWon + "\n");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepoprawna długość kodu");
            alert.setHeaderText(null);
            alert.setContentText("Długość kodu jest nie prawidlowa. Kod powinien zawierać " + codeLength + "znaki\\ów");
            alert.showAndWait();
        }
    }

    @FXML
    public void onAddBtnClick(ActionEvent event) {
        int codeLength = listOfCodes.get(0).getCode().length();
        ArrayList<Code> actualListOfCOdes = lottery.getCodes();
        String tempCode = code_tf.getText();
        if (containsAllowedCharacters(tempCode)) {
            if (tempCode.length() == codeLength) {

                boolean tempIsUsed = isUsed_tf.getText().equalsIgnoreCase("true");
                boolean tempIsWon = isWon_tf.getText().equalsIgnoreCase("true");

                Code newCode = new Code(actualListOfCOdes.size() + 1, tempCode, tempIsUsed, tempIsWon);
                actualListOfCOdes.add(newCode);
                lottery.setCodes(actualListOfCOdes);
                listOfCodes.clear();
                listOfCodes.addAll(lottery.getCodes());
                codes_tab.refresh();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Dodano nowy Kod");
                alert.setHeaderText(null);
                alert.setContentText("Wybrany przez Ciebie kod został dodany\n" +
                        "kod: " + tempCode + "\n" +
                        "Czy użyty: " + tempIsUsed + "\n" +
                        "Czy zwycieski: " + tempIsWon + "\n");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepoprawna długość kodu");
                alert.setHeaderText(null);
                alert.setContentText("Długość kodu jest nie prawidlowa. Kod powinien zawierać " + codeLength + "znaki\\ów");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nieprawidłowy kod");
            alert.setHeaderText(null);
            alert.setContentText("Kod Powinien składać się tylko z cyfr i liter");
            alert.showAndWait();
        }
    }

    private boolean containsAllowedCharacters(String tempCode) {
        char[] temp = tempCode.toCharArray();
        /*
        RandomInRanges rir = new RandomInRanges(48, 57);
        rir.addRange(65, 90);
        rir.addRange(97, 122);
         */
        for (char mark: temp){
            if (!((mark > 47 && mark <58) || (mark > 64 && mark < 91) || (mark > 96 && mark < 123))){
                return false;
            }
        }
        return true;
    }

    @FXML
    public void onRemoveBtnClick(ActionEvent event) {

        int currentCodeId = Integer.parseInt(codeId_tf.getText());

        ArrayList<Code> actualListOfCodes = lottery.getCodes();
        actualListOfCodes.remove(currentCodeId - 1);
        for (int i = currentCodeId; i < actualListOfCodes.size() + 1; i++) {
            actualListOfCodes.get(i - 1).setCodeID(i);
        }

        lottery.setCodes(actualListOfCodes);
        listOfCodes.clear();
        listOfCodes.addAll(lottery.getCodes());
        codes_tab.refresh();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dodano nowy Kod");
        alert.setHeaderText(null);
        alert.setContentText("Wybrany przez Ciebie kod został usuniety");
        alert.showAndWait();
    }

    public void onBackBtnClick(ActionEvent event) throws IOException {
        MainController mainController = new MainController(lottery);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/codegeneratorfx/start-scene.fxml"));
        loader.setController(mainController);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Code generator");
        stage.setScene(scene);
        stage.show();
    }
}
