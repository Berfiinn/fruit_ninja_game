package sample.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class Game implements Initializable {

    Random bomba;
    BlackBomb blackBomb;

    Timeline karpuzShow, muzShow, portakalShow, ananasShow,
            kiviShow, kirmiziElmaShow, bombaShow;


    boolean oyunStart = true, scoreKaydet = false;
    Timeline countDown;
    double startX, startY, endX, endY;

    Karpuz karpuz;
    Muz muz;
    Portakal portakal;
    Ananas ananas;
    Kivi kivi;
    Elma elma;

    public static Text scoreText;
    public static int yuksekSkor;

    @FXML
    private ImageView arkaplanResmi;

    @FXML
    private Text oyunSuresi;

    @FXML
    private Text slicedFruit;

    @FXML
    private Text oyuncuSkore;

    @FXML
    private AnchorPane anchorPane;



    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        sayacBasla(50);

        Karpuz.xCoord = anchorPane.getPrefWidth() / 2;
        Karpuz.ycCoord = anchorPane.getPrefHeight();

        Muz.xCoord = anchorPane.getPrefWidth() / 2;
        Muz.yCoord = anchorPane.getPrefHeight();

        Portakal.xCoord = anchorPane.getPrefWidth() / 2;
        Portakal.yCoord = anchorPane.getPrefHeight();

        Ananas.xCoord = anchorPane.getPrefWidth() / 2;
        Ananas.yCoord = anchorPane.getPrefHeight();

        Kivi.xCoord = anchorPane.getPrefWidth() / 2;
        Kivi.yCoord = anchorPane.getPrefHeight();

        Elma.xCoord = anchorPane.getPrefWidth() / 2;
        Elma.yCoord = anchorPane.getPrefHeight();

        BlackBomb.xCoord = anchorPane.getPrefWidth() / 2;
        BlackBomb.yCoord = anchorPane.getPrefHeight();

        oyunuBaslat();

        scoreText = this.oyuncuSkore;

        Login.primaryStage.setOnCloseRequest(e ->
                {
                    try
                    {
                        if (!scoreKaydet)
                        {

                            saveScore();
                        }
                    }
                    catch (IOException ex)
                    {
                        System.out.println(ex);
                    }
                }
        );
    }



    public void pauseResumeGame()
    {
        if (oyunStart)
        {
            meyveleriDurdur();
        }
        else
        {
           meyveleriBaslat();
        }
    }

    public void meyveleriDurdur (){
        oyunStart = false;
        pauseCountDown();
        karpuzShow.pause();
        muzShow.pause();
        portakalShow.pause();
        ananasShow.pause();
        kiviShow.pause();
        kirmiziElmaShow.pause();
        bombaShow.pause();
    }

    public void meyveleriBaslat (){
        oyunStart = true;
        resumeCountDown();
        karpuzShow.play();
        muzShow.play();
        portakalShow.play();
        ananasShow.play();
        kiviShow.play();
        kirmiziElmaShow.play();
        bombaShow.play();
    }

    public void sayacBasla(int time)
    {
        oyunSuresi.setText(Integer.toString(time));

        countDown = new Timeline(new KeyFrame(Duration.seconds(1), this::CountHandle, new javafx.animation.KeyValue[]{}));

        countDown.setCycleCount(time + 1);
        countDown.play();
    }

    public void pauseCountDown()
    {
        countDown.pause();
    }

    public void resumeCountDown()
    {
        countDown.play();
    }

    public int getCountDownTime()
    {
        return Integer.parseInt(oyunSuresi.getText());
    }

    public void oyunuBaslat()
    {
        arkaplanResmi.setOnDragDetected(dragDetected -> arkaplanResmi.startFullDrag());

        karpuzManager();
        muzManager();
        portakalManager();
        ananasManager();
        kiviManager();
        kirmiziElmaManager();
        bombaManager();
    }

    public void karpuzManager()
    {
        karpuzShow = new Timeline(new KeyFrame(Duration.millis(2500), e ->
        {
            karpuz = new Karpuz(anchorPane);
            karpuz.KarpuzImage.setVisible(false);
            karpuz.KarpuzImage.setFitWidth(100);
            karpuz.KarpuzImage.setFitHeight(100);

            karpuz.KarpuzImage.setOnMouseDragEntered(dragEntered ->
            {
                startX = dragEntered.getX();
                startY = dragEntered.getY();
            });

            karpuz.KarpuzImage.setOnMouseDragExited(dragExit ->
            {
                endX = dragExit.getX();
                endY = dragExit.getY();

                karpuz.slice(anchorPane);

                karpuz.splash(startX, startY, endX, endY, anchorPane);

                KarpuzIncreaseScore();

                kesilenMeyve();
            });

            karpuz.launchMeyve(anchorPane);

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible ->
            {
                karpuz.KarpuzImage.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        karpuzShow.setCycleCount(Timeline.INDEFINITE);
        karpuzShow.play();
    }

    public void muzManager()
    {
        muzShow = new Timeline(new KeyFrame(Duration.millis(2600), e ->
        {
            muz = new Muz(anchorPane);
            muz.muzMain.setVisible(false);

            muz.muzMain.setFitWidth(100);
            muz.muzMain.setFitHeight(150);

            muz.muzMain.setOnMouseDragEntered(dragEntered ->
            {
                startX = dragEntered.getX();
                startY = dragEntered.getY();
            });

            muz.muzMain.setOnMouseDragExited(dragExit ->
            {
                endX = dragExit.getX();
                endY = dragExit.getY();

                muz.slice(anchorPane);

                muz.splash(startX, startY, endX, endY, anchorPane);

                increaseScore();

                kesilenMeyve();
            });

            muz.launchMeyve(anchorPane);

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible ->
            {
                muz.muzMain.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        muzShow.setCycleCount(Timeline.INDEFINITE);
        muzShow.play();
    }

    public void portakalManager()
    {
        portakalShow = new Timeline(new KeyFrame(Duration.millis(2700), e ->
        {
            portakal = new Portakal(anchorPane);
            portakal.portakalMain.setVisible(false);

            portakal.portakalMain.setFitWidth(100);
            portakal.portakalMain.setFitHeight(100);

            portakal.portakalMain.setOnMouseDragEntered(dragEntered ->
            {
                startX = dragEntered.getX();
                startY = dragEntered.getY();
            });

            portakal.portakalMain.setOnMouseDragExited(dragExit ->
            {
                endX = dragExit.getX();
                endY = dragExit.getY();

                portakal.slice(anchorPane);

                portakal.splash(startX, startY, endX, endY, anchorPane);

                increaseScore();

                kesilenMeyve();
            });

            portakal.launchMeyve(anchorPane);

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible ->
            {
                portakal.portakalMain.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        portakalShow.setCycleCount(Timeline.INDEFINITE);
        portakalShow.play();
    }

    public void ananasManager()
    {
        ananasShow = new Timeline(new KeyFrame(Duration.millis(2800), e ->
        {
            ananas = new Ananas(anchorPane);
            ananas.ananasImage.setVisible(false);

            ananas.ananasImage.setFitWidth(150);
            ananas.ananasImage.setFitHeight(200);

            ananas.ananasImage.setOnMouseDragEntered(dragEntered ->
            {
                startX = dragEntered.getX();
                startY = dragEntered.getY();
            });

            ananas.ananasImage.setOnMouseDragExited(dragExit ->
            {
                endX = dragExit.getX();
                endY = dragExit.getY();

                ananas.slice(anchorPane);

                ananas.splash(startX, startY, endX, endY, anchorPane);

                AnanasIncreaseScore();

                kesilenMeyve();
            });

            ananas.launchMeyve(anchorPane);

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible ->
            {
                ananas.ananasImage.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        ananasShow.setCycleCount(Timeline.INDEFINITE);
        ananasShow.play();
    }

    public void kiviManager()
    {
        kiviShow = new Timeline(new KeyFrame(Duration.seconds(3), e ->
        {
            kivi = new Kivi(anchorPane);
            kivi.KiviMain.setVisible(false);

            kivi.KiviMain.setOnMouseDragEntered(dragEntered ->
            {
                startX = dragEntered.getX();
                startY = dragEntered.getY();
            });

            kivi.KiviMain.setOnMouseDragExited(dragExit ->
            {
                endX = dragExit.getX();
                endY = dragExit.getY();

                kivi.slice(anchorPane);

                kivi.splash(startX, startY, endX, endY, anchorPane);

                increaseScore();

                kesilenMeyve();
            });

            kivi.launchMeyve(anchorPane);

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible ->
            {
                kivi.KiviMain.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        kiviShow.setCycleCount(Timeline.INDEFINITE);
        kiviShow.play();
    }

    public void kirmiziElmaManager()
    {
        kirmiziElmaShow = new Timeline(new KeyFrame(Duration.seconds(4), e ->
        {
            elma = new Elma(anchorPane);
            elma.kirmiziElmaMain.setVisible(false);

            elma.kirmiziElmaMain.setOnMouseDragEntered(dragEntered ->
            {
                startX = dragEntered.getX();
                startY = dragEntered.getY();
            });

            elma.kirmiziElmaMain.setOnMouseDragExited(dragExit ->
            {
                endX = dragExit.getX();
                endY = dragExit.getY();

                elma.slice(anchorPane);

                elma.splash(startX, startY, endX, endY, anchorPane);

                kirmiziElmaIncreaseScore();

                kesilenMeyve();
            });

            elma.launchMeyve(anchorPane);

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible ->
            {
                elma.kirmiziElmaMain.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        kirmiziElmaShow.setCycleCount(Timeline.INDEFINITE);
        kirmiziElmaShow.play();
    }

    public void bombaManager()
    {
        bomba = new Random();

        bombaShow = new Timeline(new KeyFrame(Duration.seconds(2),this::bombaHandle));

        bombaShow.setCycleCount(Timeline.INDEFINITE);
        bombaShow.play();
    }

    public void increaseScore()
    {
        int hisCurrentScore = Integer.parseInt(oyuncuSkore.getText());
        oyuncuSkore.setText(Integer.toString(hisCurrentScore + 10));
    }
    public void kirmiziElmaIncreaseScore()
    {
        int hisCurrentScore = Integer.parseInt(oyuncuSkore.getText());
        oyuncuSkore.setText(Integer.toString(hisCurrentScore + 20));
    }
    public void AnanasIncreaseScore()
    {
        int hisCurrentScore = Integer.parseInt(oyuncuSkore.getText());
        oyuncuSkore.setText(Integer.toString(hisCurrentScore + 40));
    }
    public void KarpuzIncreaseScore()
    {
        int hisCurrentScore = Integer.parseInt(oyuncuSkore.getText());
        oyuncuSkore.setText(Integer.toString(hisCurrentScore + 30));
    }

    public void decreaseScore()
    {
        int scoreDurumu = Integer.parseInt(oyuncuSkore.getText());

        if (scoreDurumu >= 80)
        {
            oyuncuSkore.setText(Integer.toString(scoreDurumu - 80));
        }
        else if (scoreDurumu < 80 && scoreDurumu >= 0)
        {
            oyuncuSkore.setText(Integer.toString(0));
        }
    }

    public void kesilenMeyve()
    {
        int hisCuttedFruit = Integer.parseInt(slicedFruit.getText());
        slicedFruit.setText(Integer.toString(hisCuttedFruit + 1));
    }

    public static void saveScore() throws IOException
    {
        File scoreFile = new File("C:\\Scores.txt");

        FileWriter scoreFileWriter = new FileWriter(scoreFile, true);

        if (Integer.parseInt(scoreText.getText()) == 0)
            return;

        scoreFileWriter.write(" " + scoreText.getText());
        scoreFileWriter.close();
    }

    public static int getHighestScore()
    {
        try
        {
            File scoresFile = new File("C:\\Scores.txt");
            Scanner scanScoreFile = new Scanner(scoresFile);

            ArrayList<Integer> scores = new ArrayList();

            while(scanScoreFile.hasNext())
            {
                scores.add(scanScoreFile.nextInt());
            }

            for (int i = 0; i < scores.size(); i++)
            {
                for (int j = i + 1; j < scores.size(); j++)
                {
                    if (scores.get(i) < scores.get(j))
                    {
                        int temp = scores.get(i);
                        scores.set(i, scores.get(j));
                        scores.set(j, temp);
                    }
                }
            }

            if (scores.size() == 0)
            {
                return 0;
            }
            else
            {
                return scores.get(0);
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Scores.txt not found..");
        }

        return -1;
    }

    private void handle(MouseDragEvent dragEntered) {
        decreaseScore();

        blackBomb.bombaMain.setVisible(false);

        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("../View/oyunBitir.fxml"));
            Scene gameScene = new Scene(root);
            Login.primaryStage.setScene(gameScene);
        } catch (IOException ex) {
            System.out.println("StartingScene.fxml not found..");
        }

        FadeTransition ft = new FadeTransition(Duration.millis(300), anchorPane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.play();
    }

    private void bombaHandle(ActionEvent e) {
        if (!bomba.nextBoolean()) {
            return;
        }
        blackBomb = new BlackBomb(anchorPane);
        blackBomb.bombaMain.setVisible(false);

        blackBomb.bombaMain.setFitWidth(160);
        blackBomb.bombaMain.setFitHeight(140);

        blackBomb.bombaMain.setOnMouseDragEntered(this::handle);

        blackBomb.appearBomb(anchorPane);

        Timeline delay;
        delay = new Timeline(new KeyFrame(Duration.millis(5), this::visibleBomb));

        delay.setCycleCount(1);
        delay.play();
    }

    private void visibleBomb(ActionEvent delayVisible) {
        blackBomb.bombaMain.setVisible(true);
    }

    private void CountHandle(ActionEvent e) {
        int countDownTime = getCountDownTime();

        if (countDownTime <= 10) {
            oyunSuresi.setFill(Color.RED);
            FadeTransition fadeCountDown = new FadeTransition(Duration.millis(300), oyunSuresi);
            fadeCountDown.setCycleCount(10);
            fadeCountDown.setFromValue(0.5);
            fadeCountDown.setToValue(1);
            fadeCountDown.play();

            if (countDownTime >= 1) {
                oyunSuresi.setText(Integer.toString(countDownTime - 1));
            }

            if (getCountDownTime() <= 8) {


                if (countDownTime <= 0) {
                    Timeline delay = new Timeline(
                            new KeyFrame(Duration.millis(10), eventDelay2 ->
                            {
                                try {
                                    AnchorPane root = FXMLLoader.load(this.getClass().getResource("../View/oyunBitir.fxml"));
                                    anchorPane.getChildren().add(root);
                                    root.setLayoutX(280);
                                    root.setLayoutY(220);

                                    karpuzShow.stop();
                                    muzShow.stop();
                                    portakalShow.stop();
                                    ananasShow.stop();
                                    kiviShow.stop();
                                    kirmiziElmaShow.stop();
                                    bombaShow.stop();
                                } catch (IOException ex) {
                                    System.out.println("OyunBitir bulunamadı");
                                }
                            })
                    );

                    delay.play();

                    try {
                        yuksekSkor = getHighestScore();

                        saveScore();

                        scoreKaydet = true;
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            }
        } else {
            oyunSuresi.setText(Integer.toString(countDownTime - 1));
        }
    }
}
