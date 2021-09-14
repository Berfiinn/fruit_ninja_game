package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class liderBordu implements Initializable {

    private ObservableList<ObservableList> data;
    private TableView table = new TableView();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void buildData() {
        String url = "jdbc:postgresql://localhost/fruitNinja";
        String user = "postgres";
        String passwordDB = "salopa44";

        data = FXCollections.observableArrayList();
        try {

            try {
                Connection conn2 = DriverManager.getConnection(url, user, passwordDB);
                Statement statement = conn2.createStatement();
                String sql = "SELECT *\n" +
                        "FROM users\n" +
                        "ORDER BY userid DESC;";
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    String username = resultSet.getString("username");


                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        row.add(resultSet.getString(i));
                    }

                    data.add(row);

                    System.out.println("Data " + data);

                }


            } catch (SQLException e) {
                e.printStackTrace();
            }


            table.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

        TableColumn userid = new TableColumn("userid");
        userid.setMinWidth(200);

        TableColumn score = new TableColumn("score");
        score.setMinWidth(100);


        table.getColumns().addAll(userid, score);
        System.out.println("Table Value::" + table);

    }
}