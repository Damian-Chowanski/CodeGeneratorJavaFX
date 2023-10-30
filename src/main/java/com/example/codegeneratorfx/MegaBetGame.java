package com.example.codegeneratorfx;

import com.example.codegeneratorfx.supportClasses.Code;
import com.example.codegeneratorfx.supportClasses.Lottery;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

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

    private ArrayList<Code> drawnCodes;

    public MegaBetGame(Lottery lottery) {
        this.lottery = lottery;
        drawnCodes = new ArrayList<>();
    }

    @FXML
    void goToPreviousGame(ActionEvent event) {

    }

    @FXML
    void onSpinBtnClick(ActionEvent event) {
        code1_lbl.setVisible(false);
        code2_lbl.setVisible(false);
        code3_lbl.setVisible(false);
        drawnCodes.clear();
        Random random = new Random();
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

    private boolean isWon(int drawnNumber) {
        ArrayList<Code> drawnCodes = new ArrayList<>();
        if (drawnNumber != 0 && drawnNumber != lottery.getCodes().size()-1){
            for (int i = drawnNumber-1; i <= drawnNumber+1; i++){
                if (!(lottery.getCodes().get(i).getIsWinning()) || lottery.getCodes().get(i).getIsUsed()){
                    return false;
                }
            }
        } else if (drawnNumber == 0){

        }
        return true;
    }
}
