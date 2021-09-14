package sample.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

import java.util.concurrent.ThreadLocalRandom;

public class Elma extends Fruit {

    public ImageView kirmiziElmaMain;
    public ImageView kirmiziElma1;
    public ImageView kirmiziElma2;

    double radiusX, radiusY, X, Y;
    public static double xCoord, yCoord;

    public Elma(AnchorPane anchorPane)
    {
        this.kirmiziElmaMain = new ImageView(getClass().getResource("../images/elmaMain.png").toExternalForm());

        this.kirmiziElmaMain.setX(100);
        this.kirmiziElmaMain.setY(100);
        this.kirmiziElmaMain.setFitWidth(82);
        this.kirmiziElmaMain.setFitHeight(89);

        anchorPane.getChildren().add(this.kirmiziElmaMain);
    }

    public Elma(String filePath, double x, double y, AnchorPane anchorPane)
    {
        this.kirmiziElmaMain = new ImageView(filePath);

        this.kirmiziElmaMain.setX(x);
        this.kirmiziElmaMain.setX(y);
        this.kirmiziElmaMain.setFitWidth(82);
        this.kirmiziElmaMain.setFitHeight(89);

        anchorPane.getChildren().add(this.kirmiziElmaMain);
    }


    @Override
    public void splash(double startX, double startY, double endX, double endY, AnchorPane pane)
    {
        double slope = (endY - startY) / (endX - startX);

        double angle = Math.toDegrees(Math.atan(slope));

        ImageView trace = new ImageView(getClass().getResource("../images/bicakAnime.gif").toExternalForm());

        trace.setRotate(45 - angle);

        trace.setVisible(false);

        trace.setLayoutX(this.kirmiziElmaMain.getLayoutX() - 27);
        trace.setLayoutY(this.kirmiziElmaMain.getLayoutY() - 27);

        pane.getChildren().add(trace);

        Timeline showSlash = new Timeline(
                new KeyFrame(Duration.ZERO, e ->
                {
                    trace.setVisible(true);
                }
                ),
                new KeyFrame(Duration.millis(300), e->
                {
                    trace.setVisible(false);
                }
                )
        );

        showSlash.setCycleCount(1);
        showSlash.play();
    }

    @Override
    public void slice(AnchorPane anchorPane){
        this.kirmiziElma1 = new ImageView(getClass().getResource("../images/elma2.png").toExternalForm());
        this.kirmiziElma2 = new ImageView(getClass().getResource("../images/elma2.png").toExternalForm());

        this.kirmiziElma1.setLayoutX(this.kirmiziElmaMain.getLayoutX() - 5);
        this.kirmiziElma2.setLayoutY(this.kirmiziElmaMain.getLayoutY() + 20);

        this.kirmiziElma2.setLayoutX(this.kirmiziElmaMain.getLayoutX() - 7);
        this.kirmiziElma2.setLayoutY(this.kirmiziElmaMain.getLayoutY() - 19);

        this.kirmiziElma1.setFitWidth(96);
        this.kirmiziElma1.setFitHeight(83);

        this.kirmiziElma2.setFitWidth(96);
        this.kirmiziElma2.setFitHeight(83);

        anchorPane.getChildren().add(this.kirmiziElma1);
        anchorPane.getChildren().add(this.kirmiziElma2);

        this.kirmiziElmaMain.setVisible(false);
        this.kirmiziElma1.setVisible(true);
        this.kirmiziElma2.setVisible(true);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e ->
        {
            Kesilme(this.kirmiziElma1, this.kirmiziElma2);
        }));

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    @Override
    public void Kesilme(ImageView kirmiziElma1, ImageView kirmiziElma2)
    {
        this.kirmiziElma1.setRotate(kirmiziElma1.getRotate() + 1);
        this.kirmiziElma2.setRotate(kirmiziElma2.getRotate() + 1);

        this.kirmiziElma1.setX(kirmiziElma1.getX() + 1);
        this.kirmiziElma1.setY(kirmiziElma1.getY() + 1);

        this.kirmiziElma2.setX(kirmiziElma2.getX() - 1);
        this.kirmiziElma2.setY(kirmiziElma2.getY() + 1);
    }

    @Override
    public void launchMeyve(AnchorPane pane)
    {
        this.radiusX = ((pane.getPrefWidth() / 2) - 100) * ThreadLocalRandom.current().nextDouble(0.3, 1.0);
        this.radiusY = (pane.getPrefHeight() - 136) * ThreadLocalRandom.current().nextDouble(0.4, 1.0);

        this.X = pane.getPrefWidth()/2 - this.radiusX; // Starting point for fruit
        this.kirmiziElmaMain.setLayoutX(this.X);

        Timeline makeAnimation = new Timeline(new KeyFrame(Duration.millis(3), makeAnimationAppear ->
        {
            if (this.X >= Elma.xCoord - this.radiusX && this.X < Elma.xCoord + this.radiusX)
            {
                this.X++;
                this.Y = Elma.yCoord - (this.radiusY * Math.sqrt(1 - ((1.0/Math.pow(this.radiusX, 2)) * Math.pow(this.X - Elma.xCoord, 2))));
            }
            else
            {
                this.X++;
                this.Y++;
            }

            // Kiwi
            this.kirmiziElmaMain.setLayoutX(this.X);
            this.kirmiziElmaMain.setLayoutY(this.Y);
            this.kirmiziElmaMain.setRotate(this.kirmiziElmaMain.getRotate() + 1);
        }));

        makeAnimation.setCycleCount(1000);
        makeAnimation.play();
    }

}
