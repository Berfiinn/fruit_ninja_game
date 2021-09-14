package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class KayıtOl implements Initializable
{
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    @FXML
    private void kaydetBtnPassed(MouseEvent event)
    {
        String url = "jdbc:postgresql://localhost/fruitNinja";
        String user = "postgres";
        String passwordDB = "salopa44";

        try {
            Connection conn2 = DriverManager.getConnection(url, user, passwordDB);
            Statement statement =  conn2.createStatement();
            String sql ="INSERT INTO users(username,password) VALUES ('"+username.getText()+"','"+password.getText()+"')";
             statement.executeUpdate(sql);

                try
                {
                    Parent root = FXMLLoader.load(this.getClass().getResource("../View/login.fxml"));
                    Scene gameScene = new Scene(root);
                    Login.primaryStage.setScene(gameScene);
                }
                catch (IOException ex)
                {
                    System.out.println("login.fxml bulunamadı");
                }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
