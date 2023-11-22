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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class OneArmedBanditController {

    @FXML
    private Button spinBtn;

    @FXML
    private Label lose_lbl;

    @FXML
    private Label win_lbl;
    @FXML
    private Button megaBet_btn;
    @FXML
    private ImageView imgLotteryLeft;

    @FXML
    private ImageView imgLotteryMid;

    @FXML
    private ImageView imgLotteryRight;
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
        drawnCodes.clear();
        win_lbl.setVisible(false);
        lose_lbl.setVisible(false);
        imgLotteryLeft.setVisible(false);
        imgLotteryMid.setVisible(false);
        imgLotteryRight.setVisible(false);

        if (!(playingCodes.size() < 3) && cash >= 50) {

            cash_lbl.setText("cash: " + cash);
            spinBtn.setText("Spin Again!");

            int maxSteps = 10;
            Timeline timeline1 = startTimeLine(imgLotteryLeft, maxSteps);
            Timeline timeline2 = startTimeLine(imgLotteryMid, maxSteps);
            Timeline timeline3 = startTimeLine(imgLotteryRight, maxSteps);

            timeline1.setOnFinished(TimeEvent -> {
                spinCode(imgLotteryLeft, true);
                timeline2.play();
            });

            timeline2.setOnFinished(TimeEvent -> {
                spinCode(imgLotteryMid, true);
                timeline3.play();
            });

            timeline3.setOnFinished(TimeEvent -> {
                spinCode(imgLotteryRight, true);
                if (isWon()) {
                    win_lbl.setVisible(true);
                    cash += 100;
                    cash_lbl.setText("cash: " + cash);
                } else {
                    lose_lbl.setVisible(true);
                    cash -= 50;
                    cash_lbl.setText("cash: " + cash);
                }
            });

            timeline1.play();
        } else goToMainMenu(event);
    }

    private boolean isWon() {
        for (Code code : drawnCodes) {
            if (!(code.getIsWinning() && !(code.getIsUsed()))) {
                return false;
            }
        }
        return true;
    }

    private Timeline startTimeLine(ImageView imageView, int maxSteps) {
        final int[] currentStep = {0};
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.15), event -> {
                    if (currentStep[0] < maxSteps) {
                        spinCode(imageView, false);
                        currentStep[0]++;
                    }
                })
        );
        timeline.setCycleCount(maxSteps);
        return timeline;
    }

    private void removeSelectedCodeFromList(String codeString) {
        ArrayList<Code> copyList = new ArrayList<>(playingCodes);
        for (Code code : playingCodes) {
            if (code.getCode().equalsIgnoreCase(codeString)) {
                copyList.remove(code);
            }
        }
        playingCodes = copyList;
    }

    private void spinCode(ImageView imageView, boolean isLast) {
        Random rand = new Random();
        int randomID = rand.nextInt(0, playingCodes.size());
        Code code = playingCodes.get(randomID);
        imageView.setVisible(true);
        if (code.getIsWinning() && !(code.getIsUsed())) {
            Image image = new Image(getClass().getResourceAsStream("/images/diamond.png"));
            imageView.setImage(image);
        } else if (code.getIsUsed()) {
            Image image = new Image(getClass().getResourceAsStream("/images/Empty.png"));
            imageView.setImage(image);
        } else {
            Image image = new Image(getClass().getResourceAsStream("/images/crashedDiamond.png"));
            imageView.setImage(image);
        }
        if (isLast) {
            removeSelectedCodeFromList(code.getCode());
            drawnCodes.add(code);
        }

    }

    @FXML
    void onMegaBetClick(ActionEvent event) throws IOException {
        MegaBetGame megaBetGame = new MegaBetGame(lottery, cash);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mega-bet-game.fxml"));
        loader.setController(megaBetGame);
        Parent root = loader.load();
        megaBetGame.setCash(cash);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Mega Bet Game");
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
