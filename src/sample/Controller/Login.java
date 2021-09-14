package sample.Controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class Login extends Application
{
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;


    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("../View/login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Login");

        stage.show();

        primaryStage = stage;

    }

    @FXML
    private void onLoginButtonPressed(MouseEvent event)
    {
        String url = "jdbc:postgresql://localhost/fruitNinja";
        String user = "postgres";
        String passwordDB = "salopa44";

        try {
            Connection conn2 = DriverManager.getConnection(url, user, passwordDB);
            Statement statement =  conn2.createStatement();
            String sql = "SELECT * FROM users WHERE username = '"+username.getText()+"'AND password = '"+password.getText()+"'";
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                try
                {
                    Parent root = FXMLLoader.load(this.getClass().getResource("../View/dashboard.fxml"));
                    Scene gameScene = new Scene(root);
                    Login.primaryStage.setScene(gameScene);
                }
                catch (IOException ex)
                {
                    System.out.println("dashboard.fxml bulunamadı");
                }
            }
            else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Geçersiz Giriş");
                errorAlert.setContentText("Lütfen bilgilerinizi kontol ediniz");
                errorAlert.showAndWait();
            }
        }catch (SQLException e){
            e.printStackTrace();

        }
    }

    @FXML
    private void  onKayitOlButtonPressed (MouseEvent event){
        try
        {
            Parent root = FXMLLoader.load(this.getClass().getResource("../View/kayitOl.fxml"));
            Scene gameScene = new Scene(root);
            Login.primaryStage.setScene(gameScene);
        }
        catch (IOException ex)
        {
            System.out.println("kayitOl.fxml bulunamadı");
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
