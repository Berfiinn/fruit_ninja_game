package sample.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class OyunBitir implements Initializable
{
    @FXML
    private Text oyuncuScore;


    

    @FXML
    private Text YuksekSkor;


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        oyuncuScore.setText(Game.scoreText.getText());

        int oyuncuSkore = Integer.parseInt(Game.scoreText.getText());
        int yuksekSkor = Game.yuksekSkor;

        if (oyuncuSkore > yuksekSkor)
        {
            YuksekSkor.setText("Tebrikler yüksek skora ulaştınız: " + oyuncuSkore);
            YuksekSkor.setLayoutX(33);
            YuksekSkor.setLayoutY(79);

        }

        else
        {
            YuksekSkor.setText("Oyun Bitti- En yüksek skor: " + yuksekSkor);
            YuksekSkor.setLayoutX(170);
            YuksekSkor.setLayoutY(80); }
    }

    @FXML
    private void menuyeDonButtonPressed(MouseEvent event)
    {
            try
            {
                Parent root = FXMLLoader.load(this.getClass().getResource("../View/dashboard.fxml"));
                Scene gameScene = new Scene(root);
                Login.primaryStage.setScene(gameScene);
            }
            catch (IOException ex)
            {
                System.out.println("StartingScene.fxml not found..");
            }
    }
}