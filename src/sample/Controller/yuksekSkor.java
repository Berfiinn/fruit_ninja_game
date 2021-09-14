package sample.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class yuksekSkor implements Initializable
{
    // FXML Variables
    @FXML
    private ImageView imgBackMainmenu;

    @FXML
    private Text yuksekSkorTXT;

    @FXML
    private Rectangle rectangleTop10Scores;

    @FXML
    private Text PuanlarTXT;

    @Override
    public void initialize(URL url, ResourceBundle rb)
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
                        // Swap the two elements
                        int temp = scores.get(i);
                        scores.set(i, scores.get(j));
                        scores.set(j, temp);
                    }
                }
            }

            String formatedScores = "";

            if (scores.size() <= 10)
            {
                for (int i = 0; i < scores.size(); i++)
                {
                    formatedScores += Integer.toString(scores.get(i)) + "\n";
                }
            }
            else
            {
                for (int i = 0; i < 10; i++)
                {
                    formatedScores += Integer.toString(scores.get(i)) + "\n";
                }
            }

            PuanlarTXT.setText(formatedScores);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("score:");
        }

        // Fade in/out
        FadeTransition top10Scorec = new FadeTransition(Duration.seconds(1), yuksekSkorTXT);

        top10Scorec.setFromValue(1.0);
        top10Scorec.setToValue(0.7);
        top10Scorec.setCycleCount(Timeline.INDEFINITE);
        top10Scorec.setAutoReverse(true);
        top10Scorec.play();

    }

    @FXML
    private void MenuyeDonButtonPressed(MouseEvent event)
    {
        Timeline delay = new Timeline(
                new KeyFrame(Duration.millis(20), eventDelay2 ->
                {
                    try
                    {
                        Parent root = FXMLLoader.load(this.getClass().getResource("../View/dashboard.fxml"));
                        Scene startingScene = new Scene(root);
                        Login.primaryStage.setScene(startingScene);
                    }
                    catch (IOException ex)
                    {
                        System.out.println("HighScoresScene.fxml not found..");
                    }
                })
        );

        delay.setCycleCount(1);
        delay.play();
    }
}