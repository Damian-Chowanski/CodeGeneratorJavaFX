package com.example.codegeneratorfx;

import com.example.codegeneratorfx.supportClasses.Code;
import com.example.codegeneratorfx.supportClasses.Lottery;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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

public class OneArmedBanditController {

    @FXML
    private Label code1_lbl;

    @FXML
    private Label code2_lbl;

    @FXML
    private Label code3_lbl;

    @FXML
    private Label code1Won_lbl;
    @FXML
    private Label code2Won_lbl;
    @FXML
    private Label code3Won_lbl;

    @FXML
    private Button spinBtn;

    @FXML
    private Label lose_lbl;

    @FXML
    private Label win_lbl;
    @FXML
    private Label cash_lbl;

    private final Lottery lottery;
    ArrayList<Code> playingCodes;
    ArrayList<Code> drawnCodes;
    int cash;

    public OneArmedBanditController(Lottery lottery, int cash) {
        cash_lbl = new Label();
        this.cash = cash;
        this.lottery = lottery;
        playingCodes = lottery.getCodes();
        drawnCodes = new ArrayList<>();
    }

    public void setCash(int cash) {
        this.cash_lbl.setText("cash: " + cash);
    }

    @FXML
    void onSpinBtnClick(ActionEvent event) throws IOException {
        code1_lbl.setVisible(false);
        code2_lbl.setVisible(false);
        code3_lbl.setVisible(false);
        win_lbl.setVisible(false);
        lose_lbl.setVisible(false);
        drawnCodes.clear();

        if (!(playingCodes.size() <3) && cash >=50) {
            cash -= 50;
            cash_lbl.setText("cash: " + cash);
            spinBtn.setText("Spin Again!");

            int maxSteps = 10;
            Timeline timeline1 = startTimeLine(code1_lbl,code1Won_lbl, maxSteps);
            Timeline timeline2 = startTimeLine(code2_lbl,code2Won_lbl, maxSteps);
            Timeline timeline3 = startTimeLine(code3_lbl,code3Won_lbl, maxSteps);

            timeline1.setOnFinished(TimeEvent -> {
                addCodeToDrawn(code1_lbl);
                removeSelectedCodeFromList(code1_lbl);
                timeline2.play();
            });

            timeline2.setOnFinished(TimeEvent -> {
                addCodeToDrawn(code2_lbl);
                removeSelectedCodeFromList(code2_lbl);
                timeline3.play();
            });

            timeline3.setOnFinished(TimeEvent -> {
                addCodeToDrawn(code3_lbl);
                System.out.println(drawnCodes.size());
                if (isWon()) {
                    win_lbl.setVisible(true);
                    cash += 100;
                    cash_lbl.setText("cash: " + cash);
                } else {
                    lose_lbl.setVisible(true);
                    cash -= 50;
                    cash_lbl.setText("cash: " + cash);
                }
                removeSelectedCodeFromList(code3_lbl);
            });

            timeline1.play();
        }else goToMainMenu(event);
    }

    private void addCodeToDrawn(Label label) {
        for (Code code: playingCodes){
            if(code.getCode().equalsIgnoreCase(label.getText())){
                drawnCodes.add(code);
            }
        }
    }

    private boolean isWon() {
        for (Code code: drawnCodes){
            if (!(code.getIsWinning())){
                return false;
            }
        }
        return true;
    }

    private Timeline startTimeLine(Label label, Label labelWon, int maxSteps) {
        final int[] currentStep = {0};
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.15), event -> {
                    if (currentStep[0] < maxSteps) {
                        spinLabel(label, labelWon);
                        currentStep[0]++;
                    }
                })
        );
        timeline.setCycleCount(maxSteps);
        return timeline;
    }

    private void removeSelectedCodeFromList(Label label) {
        ArrayList<Code> copyList = new ArrayList<>(playingCodes);
        for (Code code: playingCodes){
            if(code.getCode().equalsIgnoreCase(label.getText())){
                copyList.remove(code);
            }
        }
        playingCodes = copyList;
    }

    private void spinLabel(Label label, Label labelWon) {
        Random rand = new Random();
        int randomID = rand.nextInt(0, playingCodes.size());
        label.setVisible(true);
        label.setText(String.valueOf(playingCodes.get(randomID).getCode()));
        labelWon.setText(String.valueOf(playingCodes.get(randomID).getIsWinning()));
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
