package com.example.codegeneratorfx;

import com.example.codegeneratorfx.supportClasses.Code;
import com.example.codegeneratorfx.supportClasses.Lottery;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MegaBetGame {
    Lottery lottery;


    @FXML
    private Button backBtn;

    @FXML
    private Label cash_lbl;

    @FXML
    private Label code1Won_lbl;

    @FXML
    private Label code1_lbl;

    @FXML
    private Label code2Won_lbl;

    @FXML
    private Label code2_lbl;

    @FXML
    private Label code3Won_lbl;

    @FXML
    private Label code3_lbl;

    @FXML
    private Label lose_lbl;

    @FXML
    private Button spinBtn;

    @FXML
    private Label win_lbl;

    private int cash;
    private ArrayList<Code> drawnCodes;

    public MegaBetGame(Lottery lottery, int cash) {
        this.lottery = lottery;
        this.cash = cash;
        drawnCodes = new ArrayList<>();
    }

    void setCash(int cash){
        cash_lbl.setText("cash: " + cash);
    }

    @FXML
    void onSpinBtnClick(ActionEvent event) throws IOException {
        code1_lbl.setVisible(false);
        code2_lbl.setVisible(false);
        code3_lbl.setVisible(false);
        drawnCodes.clear();
        win_lbl.setVisible(false);
        lose_lbl.setVisible(false);
        Random random = new Random();

        if (!(cash < 50)) {
            int drawnNumber = random.nextInt(0, lottery.getCodes().size());
            fillDrawnCodes(drawnNumber);
            code1_lbl.setVisible(true);
            code1_lbl.setText(drawnCodes.get(0).getCode());
            code1Won_lbl.setText(String.valueOf(drawnCodes.get(0).getIsWinning()));
            code2_lbl.setVisible(true);
            code2_lbl.setText(drawnCodes.get(1).getCode());
            code2Won_lbl.setText(String.valueOf(drawnCodes.get(1).getIsWinning()));
            code3_lbl.setVisible(true);
            code3_lbl.setText(drawnCodes.get(2).getCode());
            code3Won_lbl.setText(String.valueOf(drawnCodes.get(2).getIsWinning()));
            if (isWon()) {
                cash = cash*2;
                cash_lbl.setText("cash: " + cash);
                win_lbl.setVisible(true);
            } else {
                cash = cash/2;
                cash_lbl.setText("cash: " + cash);
                lose_lbl.setVisible(true);
            }
        } else goToMainMenu(event);
    }

    private void fillDrawnCodes(int drawnNumber) {
        if (drawnNumber != 0 && drawnNumber != lottery.getCodes().size()-1){
            drawnCodes.add(lottery.getCodes().get(drawnNumber-1));
            drawnCodes.add(lottery.getCodes().get(drawnNumber));
            drawnCodes.add(lottery.getCodes().get(drawnNumber+1));
        } else if (drawnNumber ==0) {
            drawnCodes.add(lottery.getCodes().get(lottery.getCodes().size()));
            drawnCodes.add(lottery.getCodes().get(drawnNumber));
            drawnCodes.add(lottery.getCodes().get(drawnNumber+1));
        } else {
            drawnCodes.add(lottery.getCodes().get(drawnNumber-1));
            drawnCodes.add(lottery.getCodes().get(drawnNumber));
            drawnCodes.add(lottery.getCodes().get(0));
        }
    }

    private boolean isWon() {
        for (Code code: drawnCodes){
            if (!(code.getIsWinning()) || code.getIsUsed()){
                return false;
            }
        }
        return true;
    }
    @FXML
    void goToPreviousGame(ActionEvent event) throws IOException {
        OneArmedBanditController oneArmedBanditController = new OneArmedBanditController(lottery, cash);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("one-armed-bandit-scene.fxml"));
        loader.setController(oneArmedBanditController);
        Parent root = loader.load();
        oneArmedBanditController.setCash(cash);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Play");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void goToMainMenu(ActionEvent event) throws IOException {
        CodeGeneratorFXController codeGeneratorFXController = new CodeGeneratorFXController(lottery);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-scene.fxml"));
        loader.setController(codeGeneratorFXController);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Code generator");
        stage.setScene(scene);
        stage.show();
    }
}
