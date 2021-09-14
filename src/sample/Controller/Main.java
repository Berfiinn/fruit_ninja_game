package sample.Controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable
{
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            Parent root = FXMLLoader.load(this.getClass().getResource("../View/oyunBitir.fxml"));
            Scene gameScene = new Scene(root);
            Login.primaryStage.setScene(gameScene);
        }
        catch (IOException ex)
        {
            System.out.println("OYUNbİTİR BULUNAMADI");
        }
    }

}
