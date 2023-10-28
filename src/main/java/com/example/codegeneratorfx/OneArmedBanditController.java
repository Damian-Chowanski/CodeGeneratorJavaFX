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

    public OneArmedBanditController(Lottery lottery) {
        this.lottery = lottery;
        playingCodes = lottery.getCodes();
        drawnCodes = new ArrayList<>();
    }

    @FXML
    void onSpinBtnClick(ActionEvent event) throws IOException {
        code1_lbl.setVisible(false);
        code2_lbl.setVisible(false);
        code3_lbl.setVisible(false);
        win_lbl.setVisible(false);
        lose_lbl.setVisible(false);
        if (!(playingCodes.size() <3)) {

            int maxSteps = 10;
            Timeline timeline1 = startTimeLine(code1_lbl, maxSteps);
            Timeline timeline2 = startTimeLine(code2_lbl, maxSteps);
            Timeline timeline3 = startTimeLine(code3_lbl, maxSteps);

            timeline1.setOnFinished(TimeEvent -> {
                removeSelectedCodeFromList(code1_lbl);
                timeline2.play();
            });

            timeline2.setOnFinished(TimeEvent -> {
                removeSelectedCodeFromList(code2_lbl);
                timeline3.play();
            });

            timeline3.setOnFinished(TimeEvent -> {
                if (isWon()) {
                    win_lbl.setVisible(true);
                    spinBtn.setText("Spin Again!");
                } else {
                    lose_lbl.setVisible(true);
                    spinBtn.setText("Spin Again!");
                }
            });

            timeline1.play();
        }else { //Zmienić tego elsa
            goToMainMenu(event);
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

    private Timeline startTimeLine(Label label, int maxSteps) {
        final int[] currentStep = {0};
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.15), event -> {
                    if (currentStep[0] < maxSteps) {
                        spinLabel(label);
                        currentStep[0]++;
                    }
                })
        );
        timeline.setCycleCount(maxSteps);
        return timeline;
    }

    private void removeSelectedCodeFromList(Label label) {
        ArrayList<Code> copyList = new ArrayList<>(playingCodes);
        for (Code code:playingCodes){
            if(code.getCode().equals(label.getText())){
                drawnCodes.add(code);
                copyList.remove(code);
            }
        }
        playingCodes = copyList;
    }

    private void spinLabel(Label label) {
        Random rand = new Random();
        label.setVisible(true);
        label.setText(String.valueOf(playingCodes.get(rand.nextInt(0, playingCodes.size())).getCode()));
    }

    @FXML
    void onBackBtnClick(ActionEvent event) throws IOException {
        goToMainMenu(event);
    }

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
