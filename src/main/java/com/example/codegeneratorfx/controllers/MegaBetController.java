package com.example.codegeneratorfx.controllers;

import com.example.codegeneratorfx.supportClasses.Code;
import com.example.codegeneratorfx.supportClasses.Lottery;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MegaBetController {
    Lottery lottery;


    @FXML
    private Button backBtn;

    @FXML
    private Label cash_lbl;

    @FXML
    private Label lose_lbl;

    @FXML
    private Button spinBtn;

    @FXML
    private Label win_lbl;

    @FXML
    private ImageView imgLotteryLeft;

    @FXML
    private ImageView imgLotteryMid;

    @FXML
    private ImageView imgLotteryRight;

    private int cash;
    private final ArrayList<Code> drawnCodes;

    public MegaBetController(Lottery lottery, int cash) {
        this.lottery = lottery;
        this.cash = cash;
        drawnCodes = new ArrayList<>();
    }

    void setCash(int cash){
        cash_lbl.setText("cash: " + cash);
    }

    @FXML
    void onSpinBtnClick(ActionEvent event) throws IOException {
        drawnCodes.clear();
        win_lbl.setVisible(false);
        lose_lbl.setVisible(false);
        imgLotteryLeft.setVisible(false);
        imgLotteryMid.setVisible(false);
        imgLotteryRight.setVisible(false);
        Random random = new Random();

        if (!(cash < 50)) {
            int drawnNumber = random.nextInt(0, lottery.getCodes().size());
            fillDrawnCodes(drawnNumber);
            displayImages(drawnCodes.get(0), imgLotteryLeft);
            displayImages(drawnCodes.get(1), imgLotteryMid);
            displayImages(drawnCodes.get(2), imgLotteryRight);
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

    private void displayImages(Code code, ImageView imageView) {
        imageView.setVisible(true);
        if (code.getIsWinning() && !(code.getIsUsed())){
            Image image = new Image(getClass().getResourceAsStream("/images/diamond.png"));
            imageView.setImage(image);
        } else if (code.getIsUsed()) {
            Image image = new Image(getClass().getResourceAsStream("/images/Empty.png"));
            imageView.setImage(image);
        } else {
            Image image = new Image(getClass().getResourceAsStream("/images/crashedDiamond.png"));
            imageView.setImage(image);
        }
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
        GameController gameController = new GameController(lottery, cash);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/codegeneratorfx/game.fxml"));
        loader.setController(gameController);
        Parent root = loader.load();
        gameController.setCash(cash);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Play");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void goToMainMenu(ActionEvent event) throws IOException {
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
