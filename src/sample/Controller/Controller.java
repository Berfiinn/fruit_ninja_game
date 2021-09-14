package sample.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Controller implements Initializable
{
    double startX, startY, endX, endY;

    @FXML
    private Text newGameButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView dashboardArkaPlan;

    @FXML
    private ImageView Logo;

    @FXML
    private Rectangle card,card2;


    private ObservableList<ObservableList> data;
    private ListView listView = new ListView();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    @FXML
    private void onNewGameButton(MouseEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(this.getClass().getResource("../View/oyunScene2.fxml"));
            Scene gameScene = new Scene(root);
            Login.primaryStage.setScene(gameScene);
        }
        catch (IOException ex)
        {
            System.out.println("dashboard.fxml bulunamadı");
        }
    }

    private void builData() {
        String url = "jdbc:postgresql://localhost/fruitNinja";
        String user = "postgres";
        String passwordDB = "salopa44";

        data = FXCollections.observableArrayList();


            try {
                Connection conn2 = DriverManager.getConnection(url, user, passwordDB);
                Statement statement = conn2.createStatement();
                String sql = "SELECT *\n" +
                        "FROM users\n" +
                        "ORDER BY userid DESC;";
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    String current = resultSet.getString("username");
                    ObservableList<String> list = FXCollections.observableArrayList(current);
                    listView.getItems().addAll(list);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    @FXML
    private void leaderBoardButtonPressed(MouseEvent event){

        listView = new ListView<>();
        builData();

        Scene scene = new Scene(listView);

        Login.primaryStage.setScene(scene);
        Login.primaryStage.show();
    }

    @FXML
    private void yuksekSkorButtonPressed(MouseEvent event)
    {
        yukseSkor();
    }

    public void yukseSkor()
    {
        Timeline delay = new Timeline(
                new KeyFrame(Duration.millis(20), eventDelay ->
                {
                    try
                    {
                        Parent root = FXMLLoader.load(getClass().getResource("../View/yuksekSkor.fxml"));
                        Scene highScoreScene = new Scene(root);
                        Login.primaryStage.setScene(highScoreScene);
                    }
                    catch (IOException ex)
                    {
                        System.out.println("yuksekSkor.fxml not found..");
                    }
                })
        );

        delay.setCycleCount(1);
        delay.play();
    }
}